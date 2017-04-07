package com.recklessmo.service.score;

import com.alibaba.fastjson.JSON;
import com.recklessmo.dao.score.ScoreDAO;
import com.recklessmo.model.score.Score;
import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.setting.Group;
import com.recklessmo.service.setting.CourseSettingService;
import com.recklessmo.service.setting.GradeSettingService;
import com.recklessmo.web.webmodel.page.ScoreListPage;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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


    /**
     *
     * 插入成绩数据
     *
     * @param scoreList
     */
    public void insertScoreList(List<Score> scoreList){
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
        return scoreList;
    }


    /**
     *
     * 根据examid获取成绩
     *
     * @param examId
     * @return
     */
    public List<Score> loadScoreByExamId(long examId){
        List<Score> scoreList = scoreDAO.getScoreListByExamId(examId);
        return scoreList;
    }

    private void compose(List<Score> scoreList){
        List<Grade>  gradeList = gradeSettingService.listAllGrade();
        Map<Long, String> gradeMap = new HashMap<>();
        Map<Long, String> groupMap = new HashMap<>();
        gradeList.stream().forEach(grade -> {
            gradeMap.put(grade.getGradeId(), grade.getGradeName());
            grade.getClassList().stream().forEach(group -> {
                groupMap.put(group.getClassId(), group.getClassName());
            });
        });
        scoreList.stream().forEach(score -> {
            score.setGradeName(gradeMap.getOrDefault(score.getGradeId(), ""));
            score.setClassName(gradeMap.getOrDefault(score.getClassId(), ""));
        });
    }



}
