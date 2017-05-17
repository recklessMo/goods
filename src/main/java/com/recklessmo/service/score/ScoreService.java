package com.recklessmo.service.score;

import com.recklessmo.dao.score.ScoreDAO;
import com.recklessmo.model.exam.Exam;
import com.recklessmo.model.score.CourseScore;
import com.recklessmo.model.score.Score;
import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.setting.Group;
import com.recklessmo.model.student.StudentInfo;
import com.recklessmo.service.exam.ExamService;
import com.recklessmo.service.setting.ClassLevelSettingService;
import com.recklessmo.service.setting.GradeSettingService;
import com.recklessmo.service.student.StudentService;
import com.recklessmo.web.webmodel.page.ScoreListPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by hpf on 9/29/16.
 */
@Service
public class ScoreService {

    @Resource
    private ScoreDAO scoreDAO;
    @Resource
    private GradeSettingService gradeSettingService;
    @Resource
    private StudentService studentService;
    @Resource
    private ExamService examService;
    @Resource
    private ClassLevelSettingService classLevelSettingService;

    /**
     *
     * 插入成绩数据
     *
     * @param scoreList
     */
    public void insertScoreList(List<Score> scoreList){
        if(scoreList != null && scoreList.size() != 0) {
            computeRank(scoreList);
            scoreDAO.insertList(scoreList);
        }
    }

    /**
     *
     * 移除成绩数据
     *
     * @param scoreList
     */
    public void removeScoreList(long orgId, long examId){
        scoreDAO.removeList(orgId, examId);
    }


    /**
     * 获取成绩列表(分页)
     *
     * @param page
     * @return
     */
    public List<Score> loadScoreList(ScoreListPage page){
        List<Score> scoreList = scoreDAO.getList(page);
        composeGradeInfoAndExamInfo(page.getOrgId(), scoreList);
        sortByTotal(scoreList);
        return scoreList;
    }


    /**
     *
     * 根据examid获取成绩列表
     *
     * @param examId
     * @return
     */
    public List<Score> loadScoreByExamId(long orgId, long examId){
        List<Score> scoreList = scoreDAO.getScoreListByExamId(examId);
        composeGradeInfoAndExamInfo(orgId, scoreList);
        sortByTotal(scoreList);
        return scoreList;
    }


    /**
     *
     * 根据examid和sid获取单份成绩
     *
     * @param examId
     * @param sid
     * @return
     */
    public Score getScoreByExamIdAndSid(long examId, String sid){
        return scoreDAO.getScoreByExamIdAndSid(examId, sid);
    }

    /**
     *
     * 根据examid和sidList获取一系列的成绩
     *
     * @param examId
     * @param sid
     * @return
     */
    public List<Score> getScoreByExamIdAndSidList(long examId, List<String> sidList){
        return scoreDAO.getScoreByExamIdAndSidList(examId, sidList);
    }


    /**
     * 根据sid获取scoreList
     * @param orgId
     * @param sid
     * @return
     */
    public List<Score> getScoreListBySid(long orgId, String sid){
        List<Score> scoreList =  scoreDAO.getScoreListBySid(orgId, sid);
        composeGradeInfoAndExamInfo(orgId, scoreList);
        return scoreList;
    }

    /**
     * 根据sid获取scoreList
     * @param orgId
     * @param sid
     * @return
     */
    public List<Score> getScoreListBySidList(long orgId, List<String> sidList){
        List<Score> scoreList =  scoreDAO.getScoreListBySidList(orgId, sidList);
        composeGradeInfoAndExamInfo(orgId, scoreList);
        return scoreList;
    }


    /**
     *
     * 计算年级信息
     *
     * @param scoreList
     */
    private void composeGradeInfoAndExamInfo(long orgId, List<Score> scoreList){
        if(scoreList.size() == 0 ){
            return;
        }
        List<Grade>  gradeList = gradeSettingService.listAllGrade(orgId);
        Map<Long, Grade> gradeMap = new HashMap<>();
        Map<Long, Group> groupMap = new HashMap<>();
        gradeList.stream().forEach(grade -> {
            gradeMap.put(grade.getGradeId(), grade);
            grade.getClassList().stream().forEach(group -> {
                groupMap.put(group.getClassId(), group);
            });
        });

        Set<Long> examIdSet = scoreList.stream().map(o -> o.getExamId()).collect(Collectors.toSet());
        List<Long> examIdList = new LinkedList<>(examIdSet);
        List<Exam> examList = examService.getExamByIdList(orgId, examIdList);
        Map<Long, Exam> examMap = examList.stream().collect(Collectors.toMap(Exam::getExamId, Function.identity()));

        List<String> sidList = scoreList.stream().map(o -> o.getSid()).collect(Collectors.toList());
        List<StudentInfo> studentBaseInfoList = studentService.getStudentInfoBySidList(orgId, sidList);
        Map<String, StudentInfo> stuMap = studentBaseInfoList.stream().collect(Collectors.toMap(StudentInfo::getSid, Function.identity()));

        scoreList.stream().forEach(score -> {
            score.setGradeName(gradeMap.get(score.getGradeId()).getGradeName());
            Group group = groupMap.get(score.getClassId());
            score.setClassName(group.getClassName());
            score.setClassLevel(group.getClassLevel());
            score.setClassType(group.getClassType());
            score.setName(stuMap.get(score.getSid()).getName());
            score.setExamTime(examMap.get(score.getExamId()).getExamTime());
            score.setExamName(examMap.get(score.getExamId()).getExamName());
            score.setExamType(examMap.get(score.getExamId()).getExamType());
        });
    }

    /**
     * 按照总分排序
     * @param scoreList
     */
    private void sortByTotal(List<Score> scoreList){
        //进行排序
        scoreList.sort((a, b) -> {
            CourseScore acs = a.getCourseScoreList().get(a.getCourseScoreList().size() - 1);
            CourseScore bcs = b.getCourseScoreList().get(b.getCourseScoreList().size() - 1);
            return Double.compare(bcs.getScore(), acs.getScore());
        });
    }


    /**
     * 计算排名
     *
     * @param scoreList
     */
    private void computeRank(List<Score> scoreList){
        Map<String, Map<Long, List<CourseScore>>> resultMap = new HashMap<>();
        //整理数据
        scoreList.stream().forEach(score -> {
            List<CourseScore> courseScoreList = score.getCourseScoreList();
            double total = score.getCourseScoreList().stream().mapToDouble(o->o.getScore()).sum();
            CourseScore totalScore = new CourseScore();
            totalScore.setCourseId(0);
            totalScore.setScore(total);
            totalScore.setCourseName("总分");
            courseScoreList.add(totalScore);
            courseScoreList.stream().forEach(courseScore -> {
                Map<Long, List<CourseScore>> valueListMap = resultMap.getOrDefault(courseScore.getCourseName(), new HashMap<>());
                resultMap.put(courseScore.getCourseName(), valueListMap);
                List<CourseScore> classCourseList = valueListMap.getOrDefault(score.getClassId(), new LinkedList<>());
                valueListMap.put(score.getClassId(), classCourseList);
                classCourseList.add(courseScore);
            });
        });

        //计算名次
        resultMap.values().stream().forEach(courseScoreMap -> {
            //计算总名次
            List<CourseScore> totalList = new LinkedList<>();
            courseScoreMap.values().stream().forEach(courseScores -> {
                totalList.addAll(courseScores);
                //计算单科名次
                courseScores.sort((o1, o2) -> {return o1.getScore() >= o2.getScore() ? (o1.getScore() == o2.getScore() ? 0 : -1) : 1;});
                for(int j = 0; j < courseScores.size(); j++){
                    courseScores.get(j).setClassRank(j + 1);
                    if(j > 0 && Double.compare(courseScores.get(j).getScore(), courseScores.get(j - 1).getScore()) == 0){
                        courseScores.get(j).setClassRank(courseScores.get(j - 1).getClassRank());
                    }
                }
            });
            //计算总分名次
            totalList.sort((o1, o2) -> {return o1.getScore() >= o2.getScore() ? (o1.getScore() == o2.getScore() ? 0 : -1) : 1;});
            for(int i = 0; i < totalList.size(); i++){
                totalList.get(i).setRank(i + 1);
                if(i > 0 && Double.compare(totalList.get(i).getScore(), totalList.get(i - 1).getScore()) == 0){
                    totalList.get(i).setRank(totalList.get(i - 1).getRank());
                }
            }
        });
    }

}
