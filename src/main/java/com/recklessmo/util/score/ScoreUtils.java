package com.recklessmo.util.score;

import com.recklessmo.model.score.CourseScore;
import com.recklessmo.model.score.NewScore;
import com.recklessmo.model.score.Score;
import org.apache.poi.ss.formula.functions.Rank;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by hpf on 9/30/16.
 */
public class ScoreUtils {


    /**
     *
     * 暂时用一下
     *
     * @return
     */
    public static List<NewScore> changeScoreToNewScore(List<Score> scoreList){
        List<NewScore> scores = new LinkedList<>();
        for(int i = 0; i < scoreList.size(); i++){
            Score score = scoreList.get(i);
            NewScore temp = new NewScore();
            temp.setExamId(score.getExamId());
            temp.setClassId(score.getCid());
            temp.setClassName(score.getCname());
            temp.setSid(score.getSid());
            temp.getCourseScoreList().add(new CourseScore("语文", score.getChinese(), i + 1));
            temp.getCourseScoreList().add(new CourseScore("数学", score.getMath(), i + 1));
            temp.getCourseScoreList().add(new CourseScore("英语", score.getEnglish(), i + 1));
            temp.getCourseScoreList().add(new CourseScore("政治", score.getPolotics(), i + 1));
            temp.getCourseScoreList().add(new CourseScore("历史", score.getHistory(), i + 1));
            temp.getCourseScoreList().add(new CourseScore("地理", score.getGeo(), i + 1));
            temp.getCourseScoreList().add(new CourseScore("物理", score.getPhysics(), i + 1));
            temp.getCourseScoreList().add(new CourseScore("化学", score.getChemistry(), i + 1));
            temp.getCourseScoreList().add(new CourseScore("生物", score.getBiology(), i + 1));
            temp.getCourseScoreList().add(new CourseScore("总分", score.getChinese() + score.getMath() + score.getEnglish() + score.getPolotics() + score.getHistory() + score.getGeo() + score.getPhysics() + score.getChemistry() + score.getBiology(), i + 1));
            scores.add(temp);
        }
        return scores;
    }


}
