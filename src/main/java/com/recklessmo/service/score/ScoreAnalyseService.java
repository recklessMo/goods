package com.recklessmo.service.score;

import com.recklessmo.model.score.CourseScore;
import com.recklessmo.model.score.NewScore;
import com.recklessmo.model.score.result.ClassTotal;
import com.recklessmo.model.score.result.TotalInner;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 */
@Service
public class ScoreAnalyseService {


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
    public Object analyseTotal(List<NewScore> scoreList, int type, long templateId) {
        //根据templateId获取模板参数
        //开始分析
        if (type == 1) {
            Map<Long, ClassTotal> result = new HashMap<>();
            for (NewScore newScore : scoreList) {
                for (CourseScore courseScore : newScore.getCourseScoreList()) {
                    ClassTotal classTotal = result.get(newScore.getCid());
                    if (classTotal == null) {
                        classTotal = new ClassTotal();
                        classTotal.setCid(newScore.getCid());
                        result.put(newScore.getCid(), classTotal);
                    }
                    TotalInner totalInner;
                    Optional<TotalInner> data = classTotal.getCourseTotalList().stream().filter(m->m.getName().equals(courseScore.getName())).findAny();
                    if(data.isPresent()){
                        totalInner = data.get();
                    }else{
                        totalInner = new TotalInner();
                        totalInner.setName(courseScore.getName());
                        classTotal.getCourseTotalList().add(totalInner);
                    }
                    if (courseScore.getScore() >= 60) {
                        totalInner.setQualified(totalInner.getQualified() + 1);
                    }
                    if (courseScore.getScore() >= 80) {
                        totalInner.setGood(totalInner.getGood() + 1);
                    }
                    if (courseScore.getScore() >= 90) {
                        totalInner.setBest(totalInner.getBest() + 1);
                    }
                    if (courseScore.getScore() >= 100) {
                        totalInner.setFull(totalInner.getFull() + 1);
                    }
                    //总人数
                    totalInner.setTotalCount(totalInner.getTotalCount() + 1);
                    //最高分
                    if (courseScore.getScore() > totalInner.getMax()) {
                        totalInner.setMax(courseScore.getScore());
                    }
                    //最低分
                    if (courseScore.getScore() < totalInner.getMin()) {
                        totalInner.setMin(courseScore.getScore());
                    }
                }
            }
            return result.values();
        } else if (type == 2) {

        }
        return null;
    }


}
