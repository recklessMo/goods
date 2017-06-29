package com.recklessmo.service.score;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.asm.Label;
import com.recklessmo.model.common.AvgPair;
import com.recklessmo.model.common.Pair;
import com.recklessmo.model.dynamicTable.*;
import com.recklessmo.model.score.CourseScore;
import com.recklessmo.model.score.Score;
import com.recklessmo.model.score.ScoreTemplate;
import com.recklessmo.model.score.inner.CourseTotalSetting;
import com.recklessmo.model.score.inner.RankPointInner;
import com.recklessmo.model.score.inner.RankPointPair;
import com.recklessmo.model.score.inner.ScorePointInner;
import com.recklessmo.model.score.result.absense.ScoreAbsense;
import com.recklessmo.model.score.result.gap.CourseGap;
import com.recklessmo.model.score.result.gap.GapInner;
import com.recklessmo.model.score.result.gap.ScoreGap;
import com.recklessmo.model.score.result.rank.CourseRank;
import com.recklessmo.model.score.result.rank.RankGap;
import com.recklessmo.model.score.result.rank.RankInner;
import com.recklessmo.model.score.result.rankchange.CourseRankChange;
import com.recklessmo.model.score.result.rankchange.RankChange;
import com.recklessmo.model.score.result.rankpoint.RankPoint;
import com.recklessmo.model.score.result.scorepoint.ScorePoint;
import com.recklessmo.model.score.result.self.CourseSelf;
import com.recklessmo.model.score.result.self.ScoreSelf;
import com.recklessmo.model.score.result.self.ScoreSelfInner;
import com.recklessmo.model.score.result.total.ClassTotal;
import com.recklessmo.model.score.result.total.CourseTotal;
import com.recklessmo.model.score.result.total.TotalInner;
import com.recklessmo.model.setting.Course;
import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.student.StudentInfo;
import com.recklessmo.service.setting.CourseSettingService;
import com.recklessmo.service.student.StudentService;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.Rank;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
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


    /**
     *
     * 分析成绩单
     *
     * @param data
     * @return
     */
    public Object analyseScore(List<Score> data){
        DynamicTable result = new DynamicTable();
        Map<String, String> nameMap = new HashMap<>();
        //data
        List<Map<String, Object>> dataList = new LinkedList<>();
        List<String> labelList = new LinkedList<>();
        for(int pos = 0; pos < data.size(); pos++){
            Score item = data.get(pos);
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("type", item.getClassType());
            dataMap.put("level", item.getClassLevel());
            dataMap.put("classname", item.getClassName());
            dataMap.put("classid", item.getClassId());
            dataMap.put("sid", item.getSid());
            dataMap.put("name", item.getName());
            if(pos == 0) {
                nameMap.put("type", "类型");//文科理科全科
                nameMap.put("level", "类别");//重点 普通 平行
                nameMap.put("classname", "名称");
                nameMap.put("sid", "学号");
                nameMap.put("name", "姓名");
                labelList.add("type");
                labelList.add("level");
                labelList.add("classname");
                labelList.add("name");
                labelList.add("sid");
            }
            CourseScore totalCourseScore = item.getCourseScoreList().get(item.getCourseScoreList().size() - 1);
            for (int i = 0; i < item.getCourseScoreList().size(); i++) {
                CourseScore courseScore = item.getCourseScoreList().get(i);
                String temp = "course" + i;
                dataMap.put(temp, courseScore.getScore());
                dataMap.put(temp + "gradeRank", courseScore.getRank());
                dataMap.put(temp + "classRank", courseScore.getClassRank());
                if(pos == 0) {
                    nameMap.put(temp, courseScore.getCourseName());
                    nameMap.put(temp + "gradeRank", "年级排名");
                    nameMap.put(temp + "classRank", "班级排名");
                    labelList.add(temp);
                    labelList.add(temp + "gradeRank");
                    labelList.add(temp + "classRank");
                }
            }
            dataList.add(dataMap);
        }
        result.setDataList(dataList);

        //label
        List<TableColumn> tableColumnList = new LinkedList<>();
        labelList.stream().forEach(label -> {
            TableColumn tableColumn = new TableColumn();
            tableColumn.setField(label);
            tableColumn.setTitle(nameMap.get(label));
            tableColumn.setSortable(label);
            JSONObject obj = new JSONObject();
            obj.put(label, "text");
            tableColumn.setFilter(obj);
            tableColumnList.add(tableColumn);
        });
        result.setLabelList(tableColumnList);
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
                if (score.getClassType().equals("文科班")) {
                    ClassTotal wenke = result.getOrDefault(-1L, new ClassTotal(-1L, "文科班"));
                    result.put(-1L, wenke);
                    singleClass(score, wenke, scoreTemplate);
                }
                //理科，理科-2
                if (score.getClassType().equals("理科班")) {
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
                    if (newScore.getClassType().equals("文科班")) {
                        singleCourse("文科班", -1L, courseScore, courseTotal, scoreTemplate);
                    }
                    if (newScore.getClassType().equals("理科班")) {
                        singleCourse("理科班", -2L, courseScore, courseTotal, scoreTemplate);
                    }
                });
            });
            result.values().stream().forEach(item -> item.getClassTotalList().stream().forEach(totalInner -> processAfterTotalInner(totalInner)));
            List<CourseTotal> values = new LinkedList<>(result.values());
            Collections.sort(values, (a, b) -> {
                return a.getCourseId() >= b.getCourseId() ? (a.getCourseId() == b.getCourseId() ? 0 : 1) : -1;
            });
            values.stream().forEach(item -> {
                TotalInner allInner = item.getClassTotalList().get(0);
                item.getClassTotalList().stream().forEach(single -> {
                    single.setAvgGap(single.getAvg() - allInner.getAvg());
                });
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

    private void singleCourse(String className, long classId, CourseScore courseScore, CourseTotal courseTotal, ScoreTemplate scoreTemplate) {
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
            return null;
        }
        Map<String, CourseGap> gapMap = new HashMap<>();
        Map<String, List<ScoreGap>> scoreGapMap = getScoreGapMap(scoreTemplate);
        scoreList.stream().forEach(score -> {
            score.getCourseScoreList().stream().forEach(courseScore -> {
                CourseGap gap = gapMap.getOrDefault(courseScore.getCourseName(), new CourseGap(courseScore.getCourseId(), courseScore.getCourseName(), scoreGapMap.getOrDefault(courseScore.getCourseName(), new LinkedList<>())));
                gapMap.put(courseScore.getCourseName(), gap);
                singleGap(gap, score.getClassId(), score.getClassName(), courseScore);
                singleGap(gap, -3L, "全年级", courseScore);
            });
        });
        List<CourseGap> values = new LinkedList<>(gapMap.values());
        Collections.sort(values, (a, b) -> {
            return a.getCourseId() >= b.getCourseId() ? (a.getCourseId() == b.getCourseId() ? 0 : 1) : -1;
        });
        return values;
    }

    private void singleGap(CourseGap gap, long classId, String className, CourseScore courseScore) {
        GapInner gapInner;
        Optional<GapInner> temp = gap.getGapInnerList().stream().filter(m -> m.getCid() == classId).findAny();
        if (temp.isPresent()) {
            gapInner = temp.get();
        } else {
            gapInner = new GapInner(gap.getGapList().size());
            gapInner.setCid(classId);
            gapInner.setClassName(className);
            gap.getGapInnerList().add(gapInner);
            Collections.sort(gap.getGapInnerList(), (o1, o2) -> o1.getClassName().compareTo(o2.getClassName()));
        }
        for (int i = 0; i < gap.getGapList().size(); i++) {
            ScoreGap scoreGap = gap.getGapList().get(i);
            if (courseScore.getScore() >= scoreGap.getStart() && courseScore.getScore() <= scoreGap.getEnd()) {
                gapInner.getCountList().set(i, gapInner.getCountList().get(i) + 1);
            }
        }
    }

    private Map<String, List<ScoreGap>> getScoreGapMap(ScoreTemplate scoreTemplate) {
        Map<String, List<ScoreGap>> result = new HashMap<>();
        Map<String, String> scoreGapMap = scoreTemplate.getCourseGapSettingMap();
        scoreGapMap.entrySet().stream().forEach(item -> {
            String str = item.getValue();
            String[] scoreGapArray = str.trim().split("\\s+");
            for (String single : scoreGapArray) {
                String[] temp = single.trim().split("-");
                if (temp.length == 2) {
                    double start = Double.parseDouble(temp[0]);
                    double end = Double.parseDouble(temp[1]);
                    ScoreGap scoreGap = new ScoreGap(start, end);
                    List<ScoreGap> singleValue = result.getOrDefault(item.getKey(), new LinkedList<>());
                    result.put(item.getKey(), singleValue);
                    singleValue.add(new ScoreGap(start, end));
                }
            }
        });
        return result;
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
            return null;
        }
        Map<String, List<RankGap>> courseRankGapMap = getRankGapList(scoreTemplate);
        Map<String, CourseRank> rankMap = new HashMap<>();
        scoreList.stream().forEach(score -> {
            double total = 0;
            for (CourseScore courseScore : score.getCourseScoreList()) {
                CourseRank rank = rankMap.getOrDefault(courseScore.getCourseName(), new CourseRank(courseScore.getCourseId(), courseScore.getCourseName(), courseRankGapMap.getOrDefault(courseScore.getCourseName(), new LinkedList<>())));
                rankMap.put(courseScore.getCourseName(), rank);
                singleRank(rank, score, courseScore);
                total += courseScore.getScore();
            }
        });
        //排序
        List<CourseRank> values = new LinkedList<>(rankMap.values());
        Collections.sort(values, (a, b) -> {
            return a.getCourseId() >= b.getCourseId() ? (a.getCourseId() == b.getCourseId() ? 0 : 1) : -1;
        });
        return values;
    }

    private void singleRank(CourseRank rank, Score score, CourseScore courseScore) {
        RankInner rankInner;
        Optional<RankInner> temp = rank.getGapInnerList().stream().filter(m -> m.getCid() == score.getClassId()).findAny();
        if (temp.isPresent()) {
            rankInner = temp.get();
        } else {
            rankInner = new RankInner(rank.getGapList().size());
            rankInner.setCid(score.getClassId());
            rankInner.setCname(score.getClassName());
            rank.getGapInnerList().add(rankInner);
            Collections.sort(rank.getGapInnerList(), (o1, o2) -> o1.getCname().compareTo(o2.getCname()));
        }

        for (int i = 0; i < rank.getGapList().size(); i++) {
            RankGap rankGap = rank.getGapList().get(i);
            if (courseScore.getRank() >= rankGap.getStart() && courseScore.getRank() <= rankGap.getEnd()) {
                rankInner.getCountList().set(i, rankInner.getCountList().get(i) + 1);
            }
        }
    }

    private Map<String, List<RankGap>> getRankGapList(ScoreTemplate scoreTemplate) {
        Map<String, List<RankGap>> result = new HashMap<>();
        Map<String, String> rankGapMap = scoreTemplate.getCourseRankSettingMap();
        rankGapMap.entrySet().stream().forEach(item -> {
            String str = item.getValue();
            String[] scoreGapArray = str.trim().split("\\s+");
            for (String single : scoreGapArray) {
                String[] temp = single.trim().split("-");
                if (temp.length == 2) {
                    double start = Double.parseDouble(temp[0]);
                    double end = Double.parseDouble(temp[1]);
                    ScoreGap scoreGap = new ScoreGap(start, end);
                    List<RankGap> singleValue = result.getOrDefault(item.getKey(), new LinkedList<>());
                    result.put(item.getKey(), singleValue);
                    singleValue.add(new RankGap((int) start, (int) end));
                }
            }
        });
        return result;
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
                        courseRankChange.setRankGapNum(courseScore.getRank() - tempCourseScore.getRank());
                        courseRankChangeList.add(courseRankChange);
                    }
                });
                rankChange.setCourseRankChangeList(courseRankChangeList);
                rankChangeList.add(rankChange);
            }
        });

        rankChangeList.sort((a, b) -> {
            CourseRankChange achange = a.getCourseRankChangeList().get(a.getCourseRankChangeList().size() - 1);
            CourseRankChange bchange = b.getCourseRankChangeList().get(b.getCourseRankChangeList().size() - 1);
            return achange.getRankGapNum() <= bchange.getRankGapNum() ? (achange.getRankGapNum() == bchange.getRankGapNum() ? 0 : 1) : -1;
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
            if (i == 0) {
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
                if (i == 0) {
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
                    titleMap.put(scoreGapStr, "分数差");
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
     * 分析缺考信息
     *
     * @param scoreList
     * @return
     */
    public Object analyseAbsense(long orgId, List<Score> scoreList) {
        List<ScoreAbsense> scoreAbsenseList = new LinkedList<>();
        scoreList.stream().forEach(score -> {
            List<CourseScore> courseScoreList = score.getCourseScoreList();
            courseScoreList.stream().forEach(courseScore -> {
                if (courseScore.getFlag() == 1) {
                    Optional<ScoreAbsense> scoreAbsenseOptional = scoreAbsenseList.stream().filter(o -> o.getCourseName().equals(courseScore.getCourseName())).findAny();
                    if (scoreAbsenseOptional.isPresent()) {
                        scoreAbsenseOptional.get().getSidList().add(score.getSid());
                    } else {
                        ScoreAbsense scoreAbsense = new ScoreAbsense();
                        scoreAbsense.setCourseName(courseScore.getCourseName());
                        scoreAbsense.getSidList().add(score.getSid());
                        scoreAbsense.getNameList().add(score.getName());
                        scoreAbsenseList.add(scoreAbsense);
                    }
                }
            });
        });
        scoreAbsenseList.stream().forEach(scoreAbsense -> {
            scoreAbsense.setTotalCount(scoreAbsense.getNameList().size());
        });
        return scoreAbsenseList;
    }


    /**
     * 分析个人成绩趋势
     *
     * @param showType
     * @param examType
     * @param scoreList
     * @return
     */
    public Object analyseTrend(List<String> examTypeList, int showType, List<Score> tempScoreList) {
        //过滤出需要处理的scorelist
        Set<String> examTypeSet = new HashSet<>(examTypeList);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Score> scoreList = tempScoreList.stream().filter(o -> examTypeSet.contains(o.getExamType())).collect(Collectors.toList());
        scoreList.sort((a, b) -> a.getExamTime().compareTo(b.getExamTime()));
        List<Pair> courseList = new LinkedList<>();
        scoreList.stream().forEach(score -> {
            score.getCourseScoreList().stream().forEach(courseScore -> {
                Optional<Pair> pairOptional = courseList.stream().filter(o -> o.getId() == courseScore.getCourseId()).findAny();
                if(!pairOptional.isPresent()){
                    courseList.add(new Pair(courseScore.getCourseId(), courseScore.getCourseName()));
                }
            });
        });
        courseList.sort((a, b) -> {
            return a.getId() >= b.getId() ? ( a.getId() == b.getId() ? 0 : 1) : -1;
        });
        //开始进行分析
        if (showType == 1) {
            DynamicTable dynamicTable = new DynamicTable();
            List<String> labelList = new LinkedList<>();
            Map<String, String> nameMap = new HashMap<>();
            labelList.add("name");
            labelList.add("sid");
            labelList.add("gradeName");
            labelList.add("className");
            labelList.add("examName");
            labelList.add("examTime");
            nameMap.put("name", "姓名");
            nameMap.put("sid", "学号");
            nameMap.put("gradeName", "年级");
            nameMap.put("className", "班级");
            nameMap.put("examName", "考试名称");
            nameMap.put("examTime", "考试时间");
            courseList.stream().forEach(pair -> {
                String scoreKey = "score" + pair.getId();
                String rankKey = "rank" + pair.getId();
                String classRankKey = "classRank" + pair.getId();
                labelList.add(scoreKey);
                labelList.add(rankKey);
                labelList.add(classRankKey);
                nameMap.put(scoreKey, pair.getValue() + "分数");
                nameMap.put(rankKey, "年级排名");
                nameMap.put(classRankKey, "班内排名");
            });
            List<Map<String, Object>> dataList = new LinkedList<>();
            for (int i = 0; i < scoreList.size(); i++) {
                Score score = scoreList.get(i);
                Map<String, Object> temp = new HashMap<>();
                temp.put("sid", score.getSid());
                temp.put("name", score.getName());
                temp.put("gradeName", score.getGradeName());
                temp.put("className", score.getClassName());
                temp.put("examName", score.getExamName());
                temp.put("examTime",sdf.format(score.getExamTime()));
                courseList.stream().forEach(pair -> {
                    String scoreKey = "score" + pair.getId();
                    String rankKey = "rank" + pair.getId();
                    String classRankKey = "classRank" + pair.getId();
                    Optional<CourseScore> courseScoreOptional = score.getCourseScoreList().stream().filter(o -> o.getCourseId() == pair.getId()).findAny();
                    if(courseScoreOptional.isPresent()){
                        CourseScore res = courseScoreOptional.get();
                        temp.put(scoreKey, res.getScore());
                        temp.put(rankKey, res.getRank());
                        temp.put(classRankKey, res.getClassRank());
                    }
                });
                dataList.add(temp);
            }
            //label
            List<TableColumn> tableColumnList = new LinkedList<>();
            labelList.stream().forEach(label -> {
                TableColumn tableColumn = new TableColumn();
                tableColumn.setField(label);
                tableColumn.setTitle(nameMap.get(label));
                tableColumn.setSortable(label);
                JSONObject obj = new JSONObject();
                obj.put(label, "text");
                tableColumn.setFilter(obj);
                tableColumnList.add(tableColumn);
            });
            dynamicTable.setLabelList(tableColumnList);
            dynamicTable.setDataList(dataList);
            return dynamicTable;
        } else {
            //折线图
            DynamicChart dynamicChart = new DynamicChart();
            List<String> typeList = courseList.stream().map(o->o.getValue()).collect(Collectors.toList());
            List<String> xList = scoreList.stream().map(o->o.getExamName()).collect(Collectors.toList());
            List<List<Double>> resultList = new LinkedList<>();
            courseList.stream().forEach(type -> {
                List<Double> tempList = new LinkedList<Double>();
                scoreList.stream().forEach(score -> {
                    List<CourseScore> courseScoreList = score.getCourseScoreList();
                    Optional<CourseScore> courseScoreOptional = courseScoreList.stream().filter(o -> o.getCourseId() == type.getId()).findAny();
                    if(showType == 2) {
                        tempList.add(courseScoreOptional.isPresent() ? courseScoreOptional.get().getScore() : 0d);
                    }else if(showType == 3){
                        tempList.add(courseScoreOptional.isPresent() ? courseScoreOptional.get().getRank() : 0d);
                    }else if(showType == 4){
                        tempList.add(courseScoreOptional.isPresent() ? courseScoreOptional.get().getClassRank() : 0d);
                    }
                });
                resultList.add(tempList);
            });
            dynamicChart.setTypeList(typeList);
            dynamicChart.setxList(xList);
            dynamicChart.setDataList(resultList);
            return dynamicChart;
        }
    }


    /**
     * 分析个人多场考试对比
     *
     * @param showType
     * @param examType
     * @param scoreList
     * @return
     */
    public Object analyseContrast(List<String> examTypeList, int showType, List<Score> tempScoreList) {
        //过滤出需要处理的scorelist
        Set<String> examTypeSet = new HashSet<>(examTypeList);
        List<Score> scoreList = tempScoreList.stream().filter(o -> examTypeSet.contains(o.getExamType())).collect(Collectors.toList());
        //获取科目并集
        List<Pair> courseList = new LinkedList<>();
        scoreList.stream().forEach(score -> {
            score.getCourseScoreList().stream().forEach(courseScore -> {
                Optional<Pair> pairOptional = courseList.stream().filter(o -> o.getId() == courseScore.getCourseId()).findAny();
                if(!pairOptional.isPresent()){
                    courseList.add(new Pair(courseScore.getCourseId(), courseScore.getCourseName()));
                }
            });
        });
        courseList.sort((a, b) -> {
            return a.getId() >= b.getId() ? ( a.getId() == b.getId() ? 0 : 1) : -1;
        });
        //每个人的考试成绩列表
        Map<String, List<Score>> resultMap = scoreList.stream().collect(Collectors.groupingBy(o->{
            return o.getSid()+ "-" + o.getName();
        }));
        //开始进行图表的展示
        if(showType == 2) {
            RadarChart radarChart = new RadarChart();
            List<String> nameList = new LinkedList<>(resultMap.keySet());
            List<RadarChartDimen> typeList = new LinkedList<>();
            courseList.stream().forEach(course -> {
                if (course.getId() != 0) {
                    RadarChartDimen radarChartDimen = new RadarChartDimen();
                    radarChartDimen.setName(course.getValue());
                    radarChartDimen.setMax(100);
                    radarChartDimen.setId(course.getId());
                    typeList.add(radarChartDimen);
                }
            });
            List<RadarChartInner> dataList = new LinkedList<>();
            nameList.stream().forEach(name -> {
                List<Score> singleScoreList = resultMap.get(name);
                RadarChartInner radarChartInner = new RadarChartInner();
                radarChartInner.setName(name);
                List<Double> valueList = new LinkedList<Double>();
                typeList.stream().forEach(type -> {
                    AvgPair temp = new AvgPair();
                    singleScoreList.stream().forEach(singleScore -> {
                        singleScore.getCourseScoreList().stream().forEach(courseScore -> {
                            if (courseScore.getCourseId() == type.getId()) {
                                temp.setTotal(temp.getTotal() + courseScore.getScore());
                                temp.setCount(temp.getCount() + 1);
                            }
                        });
                    });
                    //统计完毕
                    valueList.add(temp.getCount() == 0 ? 0 : (((int)(temp.getTotal() / temp.getCount() * 100)) / 100.0));
                });
                radarChartInner.setValue(valueList);
                dataList.add(radarChartInner);
            });
            radarChart.setNameList(nameList);
            radarChart.setTypeList(typeList);
            radarChart.setDataList(dataList);
            return radarChart;
        }else{
            DynamicTable dynamicTable = new DynamicTable();
            List<String> labelList = new LinkedList<>();
            Map<String, String> nameMap = new HashMap<>();
            List<Map<String, Object>> dataList = new LinkedList<>();
            labelList.add("sid");
            labelList.add("name");
            nameMap.put("sid", "学号");
            nameMap.put("name", "姓名");
            courseList.stream().forEach(course -> {
                labelList.add("course" + course.getId());
                nameMap.put("course" + course.getId(), course.getValue());
            });
            resultMap.entrySet().forEach(entry -> {
                Map<String, Object> temp = new HashMap<String, Object>();
                String sidName = entry.getKey();
                List<Score> singleScoreList = entry.getValue();
                String[] array = sidName.split("-");
                temp.put("sid", array[0]);
                temp.put("name", array[1]);
                List<Score> valueList = entry.getValue();
                courseList.stream().forEach(course -> {
                    AvgPair avgPair = new AvgPair();
                    singleScoreList.stream().forEach(singleScore -> {
                        singleScore.getCourseScoreList().stream().forEach(courseScore -> {
                            if (courseScore.getCourseId() == course.getId()) {
                                avgPair.setTotal(avgPair.getTotal() + courseScore.getScore());
                                avgPair.setCount(avgPair.getCount() + 1);
                            }
                        });
                    });
                    temp.put("course" + course.getId(), avgPair.getCount() == 0 ? 0 : (((int)(avgPair.getTotal() / avgPair.getCount() * 100)) / 100.0));
                });
                dataList.add(temp);
            });

            //处理labelList
            List<TableColumn> tableColumnList = new LinkedList<>();
            labelList.stream().forEach(label -> {
                TableColumn tableColumn = new TableColumn();
                tableColumn.setField(label);
                tableColumn.setTitle(nameMap.get(label));
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
    }


    /**
     * 分析分数点阵图
     * @param orgId
     * @param scoreList
     * @return
     */
    public Object analyseScorePoint(long orgId, List<Score> scoreList){
        Map<Long, ScorePoint> scorePointMap = new HashMap<>();
        scoreList.stream().forEach(score -> {
            score.getCourseScoreList().stream().forEach(courseScore -> {
                ScorePoint scorePoint = scorePointMap.getOrDefault(courseScore.getCourseId(), new ScorePoint(courseScore.getCourseId(), courseScore.getCourseName()));
                scorePointMap.put(courseScore.getCourseId(), scorePoint);
                //处理本班的
                singleScorePointInner(score.getClassId(), score.getClassName(), score, courseScore, scorePoint);
                //再处理全年级的
                singleScorePointInner(-3L, "全年级", score, courseScore, scorePoint);
                //如果是文科班
                if(score.getClassType().equals("文科班")){
                    singleScorePointInner(-1L, "文科班", score, courseScore, scorePoint);
                }
                //如果是理科班
                if(score.getClassType().equals("理科班")){
                    singleScorePointInner(-2L, "理科班", score, courseScore, scorePoint);
                }
            });
        });
        Collection<ScorePoint> scorePointCollection = scorePointMap.values();
        scorePointCollection.stream().forEach(scorePoint -> {
            scorePoint.getScorePointInnerList().stream().forEach(scorePointInner -> {
//                Map<Double, Integer> scorePointPairMap = scorePointInner.getScorePointPairMap();
//                List<Double> keyList = new LinkedList<>(scorePointPairMap.keySet());
//                Double max = Double.MIN_VALUE;
//                Double min = Double.MAX_VALUE;
//                for(Double temp : keyList){
//                    if(temp > max){
//                        max = temp;
//                    }
//                    if(temp < min){
//                        min = temp;
//                    }
//                }
//                double start = min;
//                while(start <= max){
//                    scorePointPairMap.put(start , scorePointPairMap.getOrDefault(start, 0));
//                    start += 0.5;
//                }
                scorePointInner.setScorePointPairList(new LinkedList<>(scorePointInner.getScorePointPairMap().entrySet()));
                scorePointInner.setScorePointPairMap(null);
            });
        });
        return scorePointMap.values();
    }

    private void singleScorePointInner(long classId, String className, Score score, CourseScore courseScore, ScorePoint scorePoint){
        System.out.println(score.getSid() + ", " + courseScore.getCourseName());
        Optional<ScorePointInner> scorePointInnerOptional = scorePoint.getScorePointInnerList().stream().filter(o -> o.getClassId() == classId).findAny();
        ScorePointInner scorePointInner = null;
        if(scorePointInnerOptional.isPresent()){
            scorePointInner = scorePointInnerOptional.get();
        }else{
            scorePointInner = new ScorePointInner();
            scorePointInner.setClassId(classId);
            scorePointInner.setClassName(className);
            scorePoint.getScorePointInnerList().add(scorePointInner);
            scorePoint.getScorePointInnerList().sort((a, b) -> {
                return a.getClassId() >= b.getClassId() ? (a.getClassId() == b.getClassId() ? 0 : 1) : -1;
            });
        }
        //处理具体的逻辑
        Integer cnt = scorePointInner.getScorePointPairMap().getOrDefault(courseScore.getScore(), new Integer(0));
        scorePointInner.getScorePointPairMap().put(courseScore.getScore(), cnt + 1);
    }

    /**
     * 分析名次点阵图
     * @param orgId
     * @param scoreList
     * @return
     */
    public Object analyseRankPoint(long orgId, List<Score> scoreList){
        Map<Long, RankPoint> rankPointMap = new HashMap<>();
        scoreList.stream().forEach(score -> {
            score.getCourseScoreList().stream().forEach(courseScore -> {
                RankPoint rankPoint = rankPointMap.getOrDefault(courseScore.getCourseId(), new RankPoint(courseScore.getCourseId(), courseScore.getCourseName()));
                rankPointMap.put(courseScore.getCourseId(), rankPoint);
                //处理本班的
                singleRankPointInner(score.getClassId(), score.getClassName(), score, courseScore, rankPoint);
//                //再处理全年级的
//                singleRankPointInner(-3L, "全年级", score, courseScore, rankPoint);
//                //如果是文科班
//                if(score.getClassType().equals("文科班")){
//                    singleRankPointInner(-1L, "文科班", score, courseScore, rankPoint);
//                }
//                //如果是理科班
//                if(score.getClassType().equals("理科班")){
//                    singleRankPointInner(-2L, "理科班", score, courseScore, rankPoint);
//                }
            });
        });
        Collection<RankPoint> rankPointCollection = rankPointMap.values();
        rankPointCollection.stream().forEach(rankPoint -> {
            rankPoint.getRankPointInnerList().stream().forEach(rankPointInner -> {
                rankPointInner.getRankPointPairList().sort((a,b)->(a.getRank() >= b.getRank() ? (a.getRank() == b.getRank() ? 0 : 1) : -1));
            });
        });
        return rankPointCollection;
    }


    private void singleRankPointInner(long classId, String className, Score score, CourseScore courseScore, RankPoint rankPoint){
        Optional<RankPointInner> rankPointInnerOptional = rankPoint.getRankPointInnerList().stream().filter(o -> o.getClassId() == classId).findAny();
        RankPointInner rankPointInner = null;
        if(rankPointInnerOptional.isPresent()){
            rankPointInner = rankPointInnerOptional.get();
        }else{
            rankPointInner = new RankPointInner();
            rankPointInner.setClassId(classId);
            rankPointInner.setClassName(className);
            rankPoint.getRankPointInnerList().add(rankPointInner);
            rankPoint.getRankPointInnerList().sort((a, b) -> {
                return a.getClassId() >= b.getClassId() ? (a.getClassId() == b.getClassId() ? 0 : 1) : -1;
            });
        }
        //处理具体的逻辑
        RankPointPair rankPointPair = new RankPointPair(courseScore.getRank(), courseScore.getScore(), score.getSid(), score.getName());
        rankPointInner.getRankPointPairList().add(rankPointPair);
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
