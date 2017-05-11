package com.recklessmo.service.score;

import com.alibaba.fastjson.JSONObject;
import com.recklessmo.model.dynamicTable.DynamicTable;
import com.recklessmo.model.dynamicTable.TableColumn;
import com.recklessmo.model.score.CourseScore;
import com.recklessmo.model.score.Score;
import com.recklessmo.model.score.ScoreTemplate;
import com.recklessmo.model.score.inner.CourseTotalSetting;
import com.recklessmo.model.score.result.absense.ScoreAbsense;
import com.recklessmo.model.score.result.gap.CourseGap;
import com.recklessmo.model.score.result.gap.GapInner;
import com.recklessmo.model.score.result.gap.ScoreGap;
import com.recklessmo.model.score.result.rank.CourseRank;
import com.recklessmo.model.score.result.rank.RankGap;
import com.recklessmo.model.score.result.rank.RankInner;
import com.recklessmo.model.score.result.rankchange.CourseRankChange;
import com.recklessmo.model.score.result.rankchange.RankChange;
import com.recklessmo.model.score.result.self.CourseSelf;
import com.recklessmo.model.score.result.self.ScoreSelf;
import com.recklessmo.model.score.result.self.ScoreSelfInner;
import com.recklessmo.model.score.result.total.ClassTotal;
import com.recklessmo.model.score.result.total.CourseTotal;
import com.recklessmo.model.score.result.total.TotalInner;
import com.recklessmo.model.setting.Course;
import com.recklessmo.model.setting.Group;
import com.recklessmo.model.student.StudentInfo;
import com.recklessmo.service.setting.CourseSettingService;
import com.recklessmo.service.student.StudentService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class ScoreAnalyseService {

    @Resource
    private ScoreTemplateService scoreTemplateService;

    @Resource
    private StudentService studentService;

    @Resource
    private CourseSettingService courseSettingService;


    private Map<String, Long> getCourseNameToIdMap(){
        List<Course> courseList = courseSettingService.getStandardCourseList();
        Map<String, Long> result = courseList.stream().collect(Collectors.toMap(Course::getCourseName, course -> course.getCourseId()));
        return result;
    }

    /**
     * 进行整体分析.
     * <p>
     * cond:
     * 1. 适应不同的维度
     * 2. 根据学科的进行自适应, 有对应的学科就显示对应的分析结果,无则不显示
     * 3. todo看需不需要返回图形的特定结果(or 我们可以在前端进行数据结构的转换)
     * <p>
     * type为1代表班级维度
     * type为2代表学科维度
     */
    public Object analyseTotal(List<Score> scoreList, int type, long templateId) {
        //根据templateId获取模板参数
        ScoreTemplate scoreTemplate = scoreTemplateService.get(templateId);
        if (scoreTemplate == null) {
            return null;
        }
        //班级维度
        if (type == 1) {
            Map<Long, ClassTotal> result = new HashMap<>();
            scoreList.stream().forEach(score -> {
                //加入对应的分开班级
                ClassTotal classTotal = result.getOrDefault(score.getClassId(), new ClassTotal(score.getClassId(), score.getClassName()));
                result.put(score.getClassId(), classTotal);
                //单独分析一个班，每个班有自己的id
                singleClass(score, classTotal, scoreTemplate);
                //文科，文科-1，
                if(score.getClassType().equals("文科班")){
                    ClassTotal wenke = result.getOrDefault(-1L, new ClassTotal(-1L, "文科班"));
                    result.put(-1L, wenke);
                    singleClass(score, wenke, scoreTemplate);
                }
                //理科，理科-2
                if(score.getClassType().equals("理科班")){
                    ClassTotal like = result.getOrDefault(-2L, new ClassTotal(-2L, "理科班"));
                    result.put(-2l, like);
                    singleClass(score, like, scoreTemplate);
                }
                //分析全年级，-3班代表全年级
                ClassTotal all = result.getOrDefault(-3L, new ClassTotal(-3L, "全年级"));
                result.put(-3L, all);
                singleClass(score, all, scoreTemplate);
            });
            result.values().stream().forEach(item -> item.getCourseTotalList().stream().forEach(totalInner -> processAfterTotalInner(totalInner)));
            List<ClassTotal> values = new LinkedList<>(result.values());
            Collections.sort(values, (a, b) -> {
                return a.getClassId() >= b.getClassId() ? (a.getClassId() == b.getClassId() ? 0 : 1) : -1;
            });
            return values;
        } else if (type == 2) {
            Map<String, CourseTotal> result = new HashMap<>();
            scoreList.stream().forEach(newScore -> {
                newScore.getCourseScoreList().stream().forEach(courseScore -> {
                    CourseTotal courseTotal = result.getOrDefault(courseScore.getCourseName(), new CourseTotal(courseScore.getCourseName(), courseScore.getCourseId()));
                    result.put(courseScore.getCourseName(), courseTotal);
                    //先找一下本班的
                    singleCourse(newScore.getClassName(), newScore.getClassId(), courseScore, courseTotal, scoreTemplate);
                    //然后全年级的
                    singleCourse("全年级", -3L, courseScore, courseTotal, scoreTemplate);
                    //然后文理分科
                    if(newScore.getClassType().equals("文科班")){
                        singleCourse("文科班", -1L, courseScore, courseTotal, scoreTemplate);
                    }
                    if(newScore.getClassType().equals("理科班")){
                        singleCourse("理科班", -2L, courseScore, courseTotal, scoreTemplate);
                    }
                });
            });
            result.values().stream().forEach(item -> item.getClassTotalList().stream().forEach(totalInner -> processAfterTotalInner(totalInner)));
            List<CourseTotal> values = new LinkedList<>(result.values());
            Map<String, Long> courseMap = getCourseNameToIdMap();
            values.stream().forEach(value -> {
                value.setCourseId(courseMap.getOrDefault(value.getCourseName(), 0L));
            });
            Collections.sort(values, (a, b) -> {
                return a.getCourseId() >= b.getCourseId() ? (a.getCourseId() == b.getCourseId() ? 0 : 1) : -1;
            });
            return values;
        }
        return null;
    }

    private void singleClass(Score score, ClassTotal classTotal, ScoreTemplate scoreTemplate) {
        score.getCourseScoreList().stream().forEach(courseScore -> {
            TotalInner totalInner;
            Optional<TotalInner> data = classTotal.getCourseTotalList().stream().filter(m -> m.getName().equals(courseScore.getCourseName())).findAny();
            if (data.isPresent()) {
                totalInner = data.get();
            } else {
                totalInner = new TotalInner(courseScore.getCourseName(), 0);
                classTotal.getCourseTotalList().add(totalInner);
            }
            totalInner(courseScore, totalInner, scoreTemplate);
        });
    }

    private void singleCourse(String className, long classId, CourseScore courseScore, CourseTotal courseTotal, ScoreTemplate scoreTemplate){
        TotalInner selfInner;
        Optional<TotalInner> selfInnerOptional = courseTotal.getClassTotalList().stream().filter(m -> m.getName().equals(className)).findAny();
        if (selfInnerOptional.isPresent()) {
            selfInner = selfInnerOptional.get();
        } else {
            selfInner = new TotalInner(className, classId);
            courseTotal.getClassTotalList().add(selfInner);
            Collections.sort(courseTotal.getClassTotalList(), (o1, o2) -> o1.getId() > o2.getId() ? 1 : o1.getId() == o2.getId() ? 0 : -1);
        }
        totalInner(courseScore, selfInner, scoreTemplate);
    }

    //内部数据
    private void totalInner(CourseScore courseScore, TotalInner totalInner, ScoreTemplate scoreTemplate) {
        //获取对应的分析设置
        CourseTotalSetting courseTotalSetting = scoreTemplate.getCourseTotalSettingMap().get(courseScore.getCourseName());
        //及格率,优秀率
        if (courseScore.getScore() >= courseTotalSetting.getQualified()) {
            totalInner.setQualified(totalInner.getQualified() + 1);
        }
        if (courseScore.getScore() >= courseTotalSetting.getGood()) {
            totalInner.setGood(totalInner.getGood() + 1);
        }
        if (courseScore.getScore() >= courseTotalSetting.getBest()) {
            totalInner.setBest(totalInner.getBest() + 1);
        }
        if (courseScore.getScore() >= courseTotalSetting.getFull()) {
            totalInner.setFull(totalInner.getFull() + 1);
        }
        //总人数
        totalInner.setTotalCount(totalInner.getTotalCount() + 1);
        //总的分数
        totalInner.setSum(totalInner.getSum() + courseScore.getScore());
        //最高分
        if (courseScore.getScore() > totalInner.getMax()) {
            totalInner.setMax(courseScore.getScore());
        }
        //最低分
        if (courseScore.getScore() < totalInner.getMin()) {
            totalInner.setMin(courseScore.getScore());
        }
        //添加score
        totalInner.getScoreList().add(courseScore.getScore());
    }

    //标准差
    private void processAfterTotalInner(TotalInner totalInner) {
        totalInner.setAvg(totalInner.getSum() / totalInner.getTotalCount());
        double sum = 0;
        for (Double score : totalInner.getScoreList()) {
            sum += (score - totalInner.getAvg()) * (score - totalInner.getAvg());
        }
        totalInner.setStdDev(Math.sqrt(sum / totalInner.getTotalCount()));
    }


    /**
     * 分数线分析, 分数线就基于学科来做
     * 基于班级的话不太好做, 有可能一个班级里面多个学科的分数段设置不同
     * <p>
     * 这样界面不好看!
     * <p>
     * 基于学科来做, 然后学科的每个班级下面会包括各个班级, 以及整个文科班理科班和全年级
     * <p>
     * 以及重点班, 普通班等不同的班级类型
     *
     * @param newScores
     * @param templateId
     * @return
     */
    public Collection<CourseGap> analyseGap(List<Score> scoreList, long templateId) {
        //根据templateId获取模板参数
        ScoreTemplate scoreTemplate = scoreTemplateService.get(templateId);
        if (scoreTemplate == null) {
//            return null;
        }
        Map<String, CourseGap> gapMap = new HashMap<>();
        scoreList.stream().forEach(score -> {
            score.getCourseScoreList().stream().forEach(courseScore -> {
                CourseGap gap = gapMap.getOrDefault(courseScore.getCourseName(), new CourseGap(courseScore.getCourseName(), getScoreGapList(scoreTemplate, courseScore.getCourseName())));
                gapMap.put(courseScore.getCourseName(), gap);
                GapInner gapInner;
                Optional<GapInner> temp = gap.getGapInnerList().stream().filter(m -> m.getCid() == score.getClassId()).findAny();
                if (temp.isPresent()) {
                    gapInner = temp.get();
                } else {
                    gapInner = new GapInner(gap.getGapList().size());
                    gapInner.setCid(score.getClassId());
                    gapInner.setClassName(score.getClassName());
                    gap.getGapInnerList().add(gapInner);
                    Collections.sort(gap.getGapInnerList(), (o1, o2) -> o1.getClassName().compareTo(o2.getClassName()));
                }
                for (int i = 0; i < gap.getGapList().size(); i++) {
                    ScoreGap scoreGap = gap.getGapList().get(i);
                    if (courseScore.getScore() >= scoreGap.getStart() && courseScore.getScore() <= scoreGap.getEnd()) {
                        gapInner.getCountList().set(i, gapInner.getCountList().get(i) + 1);
                    }
                }
            });
        });
        return gapMap.values();
    }

    private List<ScoreGap> getScoreGapList(ScoreTemplate scoreTemplate, String courseName) {
        List<ScoreGap> scoreGaps = new LinkedList<>();
        scoreGaps.add(new ScoreGap(1d, 20d));
        scoreGaps.add(new ScoreGap(21d, 40d));
        scoreGaps.add(new ScoreGap(41d, 50d));
        scoreGaps.add(new ScoreGap(51d, 70d));
        scoreGaps.add(new ScoreGap(71d, 100d));
        return scoreGaps;
    }

    /**
     * 名次分析
     *
     * @param newScores
     * @param templateId
     * @return
     */
    public Object analyseRank(List<Score> scoreList, long templateId) {
        //根据templateId获取模板参数
        ScoreTemplate scoreTemplate = scoreTemplateService.get(templateId);
        if (scoreTemplate == null) {
//            return null;
        }
        Map<String, CourseRank> rankMap = new HashMap<>();
        scoreList.stream().forEach(score -> {
            for (CourseScore courseScore : score.getCourseScoreList()) {
                CourseRank rank = rankMap.get(courseScore.getCourseName());
                if (rank == null) {
                    rank = new CourseRank();
                    rank.setName(courseScore.getCourseName());
                    rank.setGapList(getRankGapList());
                    rankMap.put(courseScore.getCourseName(), rank);
                }
                RankInner rankInner;
                Optional<RankInner> temp = rank.getGapInnerList().stream().filter(m -> m.getCid() == score.getClassId()).findAny();
                if (temp.isPresent()) {
                    rankInner = temp.get();
                } else {
                    rankInner = new RankInner(rank.getGapList().size());
                    rankInner.setCid(score.getClassId());
                    rankInner.setCname(score.getClassName());
                    rank.getGapInnerList().add(rankInner);
                }

                for (int i = 0; i < rank.getGapList().size(); i++) {
                    RankGap rankGap = rank.getGapList().get(i);
                    if (courseScore.getScore() >= rankGap.getStart() && courseScore.getScore() <= rankGap.getEnd()) {
                        rankInner.getCountList().set(i, rankInner.getCountList().get(i) + 1);
                    }
                }
            }
        });
        return rankMap.values();
    }

    private List<RankGap> getRankGapList() {
        List<RankGap> gapList = new LinkedList<>();
        gapList.add(new RankGap(1, 10));
        gapList.add(new RankGap(11, 20));
        gapList.add(new RankGap(21, 30));
        gapList.add(new RankGap(31, 40));
        gapList.add(new RankGap(41, 50));
        gapList.add(new RankGap(51, 60));
        gapList.add(new RankGap(61, 70));
        gapList.add(new RankGap(71, 80));
        gapList.add(new RankGap(81, 90));
        gapList.add(new RankGap(91, 100));
        gapList.add(new RankGap(101, 100000));
        return gapList;
    }

    /**
     * 分析均分
     *
     * @param newScores
     * @param templateId
     * @return
     */
    public Object analyseAvg(List<Score> scoreList, long templateId) {
        Map<String, CourseRank> rankMap = new HashMap<>();
        return rankMap.values();
    }

    /**
     * 分析个人综合
     *
     * @param newScores
     * @param templateId
     * @return
     */
    public Object analyseSelf(long orgId, List<Score> scoreList, long templateId) {
        //根据templateId获取模板参数
        ScoreTemplate scoreTemplate = scoreTemplateService.get(templateId);
        if (scoreTemplate == null) {
            return null;
        }
        if (scoreList.size() > 0) {
            ScoreSelf scoreSelf = new ScoreSelf();
            Score first = scoreList.get(0);
            List<CourseSelf> courseSelfList = new LinkedList<>();
            first.getCourseScoreList().stream().forEach(item -> {
                CourseSelf courseSelf = new CourseSelf();
                courseSelf.setName(item.getCourseName());
                courseSelf.setMax(scoreTemplate.getCourseTotalSettingMap().get(item.getCourseName()).getFull());
                courseSelfList.add(courseSelf);
            });

            List<String> sidList = scoreList.stream().map(o -> o.getSid()).collect(Collectors.toList());
            List<StudentInfo> studentInfoList = studentService.getStudentInfoBySidList(orgId, sidList);
            Map<String, StudentInfo> studentInfoMap = studentInfoList.stream().collect(Collectors.toMap(StudentInfo::getSid, Function.identity()));
            List<ScoreSelfInner> scoreSelfInnerList = new LinkedList<>();
            scoreList.stream().forEach(score -> {
                ScoreSelfInner scoreSelfInner = new ScoreSelfInner();
                scoreSelfInner.setName(studentInfoMap.get(score.getSid()).getName());
                Map<String, Double> courseMap = score.getCourseScoreList().stream().collect(Collectors.toMap(CourseScore::getCourseName, o -> o.getScore()));
                List<Double> resultScoreList = new LinkedList<>();
                courseSelfList.stream().forEach(courseSelf -> {
                    resultScoreList.add(courseMap.get(courseSelf.getName()));
                });
                scoreSelfInner.setValue(resultScoreList);
                scoreSelfInnerList.add(scoreSelfInner);
            });
            scoreSelf.setCourseInfoList(courseSelfList);
            scoreSelf.setScoreSelfInnerList(scoreSelfInnerList);
            scoreSelf.setNameList(studentInfoList.stream().map(o -> o.getName()).collect(Collectors.toList()));
            return scoreSelf;
        }
        return null;
    }


    /**
     * 分析两场考试的进退步
     * <p>
     * 显示问题, 暂时不支持大于2个的进行分析
     * <p>
     * 展示列
     * <p>
     * 年级 班级 学号 姓名 (语文1, 语文2, 数学1, 数学2)
     *
     * @param examIdList
     * @return
     */
    public Object analyseRankChange(List<Score> first, List<Score> second) {
        List<RankChange> rankChangeList = new LinkedList<>();
        Map<String, Score> secondMap = second.stream().collect(Collectors.toMap(Score::getSid, Function.identity()));
        first.stream().forEach(firstItem -> {
            Score secondItem = secondMap.get(firstItem.getSid());
            if (secondItem != null) {
                RankChange rankChange = new RankChange();
                rankChange.setSid(firstItem.getSid());
                rankChange.setName(firstItem.getName());
                rankChange.setClassName(firstItem.getClassName());
                rankChange.setGradeName(firstItem.getGradeName());
                List<CourseScore> firstList = firstItem.getCourseScoreList();
                List<CourseScore> secondList = secondItem.getCourseScoreList();
                //获取交集
                List<CourseRankChange> courseRankChangeList = new LinkedList<>();
                firstList.stream().forEach(courseScore -> {
                    Optional<CourseScore> temp = secondList.stream().filter(o -> o.getCourseName().equals(courseScore.getCourseName())).findAny();
                    if (temp.isPresent()) {
                        CourseRankChange courseRankChange = new CourseRankChange();
                        courseRankChange.setCourseName(courseScore.getCourseName());
                        courseRankChange.setFirstScore(courseScore.getScore());
                        courseRankChange.setFirstRank(courseScore.getRank());
                        CourseScore tempCourseScore = temp.get();
                        courseRankChange.setSecondScore(tempCourseScore.getScore());
                        courseRankChange.setSecondRank(tempCourseScore.getRank());
                        courseRankChange.setScoreChange(tempCourseScore.getScore() - courseScore.getScore());
                        courseRankChange.setRankGapNum(tempCourseScore.getRank() - courseScore.getRank());
                        courseRankChangeList.add(courseRankChange);
                    }
                });
                rankChange.setCourseRankChangeList(courseRankChangeList);
                rankChangeList.add(rankChange);
            }
        });

        DynamicTable dynamicTable = new DynamicTable();

        List<String> labelList = new LinkedList<>();
        Map<String, String> titleMap = new HashMap<>();

        List<Map<String, Object>> dataList = new LinkedList<>();

        for (int i = 0; i < rankChangeList.size(); i++) {
            RankChange rankChange = rankChangeList.get(i);
            Map<String, Object> singleData = new HashMap<String, Object>();
            singleData.put("sid", rankChange.getSid());
            singleData.put("gradeName", rankChange.getGradeName());
            singleData.put("className", rankChange.getClassName());
            singleData.put("name", rankChange.getName());
            if(i == 0){
                labelList.add("sid");
                labelList.add("gradeName");
                labelList.add("className");
                labelList.add("name");

                titleMap.put("sid", "学号");
                titleMap.put("gradeName", "年级");
                titleMap.put("className", "班级");
                titleMap.put("name", "姓名");
            }
            for (int j = 0; j < rankChange.getCourseRankChangeList().size(); j++) {
                CourseRankChange courseRankChange = rankChange.getCourseRankChangeList().get(j);

                String firstScoreStr = "firstScore" + j;
                String firstRankStr = "firstRank" + j;
                String secondScoreStr = "secondScore" + j;
                String secondRankStr = "secondRank" + j;
                String scoreGapStr = "scoreGap" + j;
                String rankGapStr = "rankGap" + j;

                singleData.put(firstScoreStr, courseRankChange.getFirstScore());
                singleData.put(firstRankStr, courseRankChange.getFirstRank());
                singleData.put(secondScoreStr, courseRankChange.getSecondScore());
                singleData.put(secondRankStr, courseRankChange.getSecondRank());
                singleData.put(scoreGapStr, courseRankChange.getScoreChange());
                singleData.put(rankGapStr, courseRankChange.getRankGapNum());
                if(i == 0){
                    labelList.add(firstScoreStr);
                    labelList.add(firstRankStr);
                    labelList.add(secondScoreStr);
                    labelList.add(secondRankStr);
                    labelList.add(scoreGapStr);
                    labelList.add(rankGapStr);
                    titleMap.put(firstScoreStr, courseRankChange.getCourseName() + "1");
                    titleMap.put(firstRankStr, "排名1");
                    titleMap.put(secondScoreStr, courseRankChange.getCourseName() + "2");
                    titleMap.put(secondRankStr, "排名2");
                    titleMap.put(scoreGapStr,"分数差");
                    titleMap.put(rankGapStr, "排名差");
                }
            }
            dataList.add(singleData);
        }
        //处理labelList
        List<TableColumn> tableColumnList = new LinkedList<>();
        labelList.stream().forEach(label -> {
            TableColumn tableColumn = new TableColumn();
            tableColumn.setField(label);
            tableColumn.setTitle(titleMap.get(label));
            tableColumn.setSortable(label);
            JSONObject obj = new JSONObject();
            obj.put(label, "text");
            tableColumn.setFilter(obj);
            tableColumnList.add(tableColumn);
        });
        dynamicTable.setDataList(dataList);
        dynamicTable.setLabelList(tableColumnList);
        return dynamicTable;
    }

    /**
     *
     * 分析缺考信息
     *
     * @param scoreList
     * @return
     */
    public Object analyseAbsense(long orgId, List<Score> scoreList){
        List<ScoreAbsense> scoreAbsenseList = new LinkedList<>();
        scoreList.stream().forEach(score -> {
            List<CourseScore> courseScoreList = score.getCourseScoreList();
            courseScoreList.stream().forEach(courseScore -> {
                if(courseScore.getFlag() == 1){
                    Optional<ScoreAbsense> scoreAbsenseOptional = scoreAbsenseList.stream().filter(o->o.getCourseName().equals(courseScore.getCourseName())).findAny();
                    if(scoreAbsenseOptional.isPresent()){
                        scoreAbsenseOptional.get().getSidList().add(score.getSid());
                    }else{
                        ScoreAbsense scoreAbsense = new ScoreAbsense();
                        scoreAbsense.setCourseName(courseScore.getCourseName());
                        scoreAbsense.getSidList().add(score.getSid());
                        scoreAbsenseList.add(scoreAbsense);
                    }
                }
            });
        });
        Set<String> sidSet = new HashSet<>();
        scoreAbsenseList.stream().forEach(scoreAbsense -> sidSet.addAll(scoreAbsense.getSidList()));
        List<StudentInfo> studentBaseInfoList = studentService.getStudentInfoBySidList(orgId, new LinkedList<>(sidSet));
        Map<String, StudentInfo> nameMap = studentBaseInfoList.stream().collect(Collectors.toMap(StudentInfo::getSid, Function.identity()));
        scoreAbsenseList.stream().forEach(scoreAbsense -> {
            scoreAbsense.getSidList().stream().forEach(sid -> {
                scoreAbsense.getNameList().add(nameMap.get(sid).getName());
            });
            scoreAbsense.setTotalCount(scoreAbsense.getNameList().size());
        });
        return scoreAbsenseList;
    }


    public static void main(String[] args) {
        Integer a = new Integer(1000);
        int b = 1000;
        Integer c = new Integer(10);
        Integer d = new Integer(10);
        System.out.println(a == b);
        System.out.println(c == d);
    }

}
