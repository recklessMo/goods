package com.recklessmo.service.score;

import com.recklessmo.dao.score.ScoreDAO;
import com.recklessmo.model.exam.Exam;
import com.recklessmo.model.score.CourseScore;
import com.recklessmo.model.score.Score;
import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.setting.Group;
import com.recklessmo.model.student.StudentBaseInfo;
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
        computeRank(scoreList);
        if(scoreList != null && scoreList.size() != 0) {
            scoreDAO.insertList(scoreList);
        }
    }


    /**
     * 获取成绩列表(分页)
     *
     * @param page
     * @return
     */
    public List<Score> loadScoreList(ScoreListPage page){
        List<Score> scoreList = scoreDAO.getList(page);
        composeGradeInfo(page.getOrgId(), scoreList);
        computeRank(scoreList);
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
        composeGradeInfo(orgId, scoreList);
        computeRank(scoreList);
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
        composeGradeInfo(orgId, scoreList);
        return scoreList;
    }


    /**
     *
     * 计算年级信息
     *
     * @param scoreList
     */
    private void composeGradeInfo(long orgId, List<Score> scoreList){
        List<Grade>  gradeList = gradeSettingService.listAllGrade();
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
        List<StudentBaseInfo> studentBaseInfoList = studentService.getStudentBaseInfoByIdList(orgId, sidList);
        Map<String, StudentBaseInfo> stuMap = studentBaseInfoList.stream().collect(Collectors.toMap(StudentBaseInfo::getSid, Function.identity()));

        scoreList.stream().forEach(score -> {
            score.setGradeName(gradeMap.get(score.getGradeId()).getGradeName());
            Group group = groupMap.get(score.getClassId());
            score.setClassName(group.getClassName());
            score.setClassLevel(group.getClassLevel());
            score.setClassType(group.getClassType());
            score.setName(stuMap.get(score.getSid()).getName());
            score.setExamName(examMap.get(score.getExamId()).getExamName());
            score.setCreated(examMap.get(score.getExamId()).getExamTime());
            score.setExamType(examMap.get(score.getExamId()).getExamType());
        });
    }


    /**
     * 计算排名
     *
     * @param scoreList
     */
    private void computeRank(List<Score> scoreList){
        Map<String, List<CourseScore>> resultMap = new HashMap<>();
        scoreList.stream().forEach(score -> {
            List<CourseScore> courseScoreList = score.getCourseScoreList();
            courseScoreList.stream().forEach(courseScore -> {
                List<CourseScore> valueList = resultMap.getOrDefault(courseScore.getCourseName(), new LinkedList<>());
                valueList.add(courseScore);
                resultMap.put(courseScore.getCourseName(), valueList);
            });
        });

        resultMap.values().stream().forEach(courseScores -> {
            courseScores.sort((o1, o2) -> {return o1.getScore() >= o2.getScore() ? (o1.getScore() == o2.getScore() ? 0 : -1) : 1;});
            for(int i = 0; i < courseScores.size(); i++){
                courseScores.get(i).setRank(i + 1);
            }
        });
    }

}
