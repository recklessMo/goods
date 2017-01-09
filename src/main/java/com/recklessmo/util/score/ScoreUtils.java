package com.recklessmo.util.score;

import com.recklessmo.model.score.CourseScore;
import com.recklessmo.model.score.NewScore;
import com.recklessmo.model.score.Score;

import java.util.LinkedList;
import java.util.List;

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
            temp.setCid(score.getCid());
            temp.setCname(score.getCname());
            temp.setSid(score.getSid());
            temp.add(new CourseScore("语文", score.getChinese(), score.getCname()));
            temp.add(new CourseScore("数学", score.getMath(), score.getCname()));
            temp.add(new CourseScore("英语", score.getEnglish(), score.getCname()));
            temp.add(new CourseScore("政治", score.getPolotics(), score.getCname()));
            temp.add(new CourseScore("历史", score.getHistory(), score.getCname()));
            temp.add(new CourseScore("地理", score.getGeo(), score.getCname()));
            temp.add(new CourseScore("物理", score.getPhysics(), score.getCname()));
            temp.add(new CourseScore("化学", score.getChemistry(), score.getCname()));
            temp.add(new CourseScore("生物", score.getBiology(), score.getCname()));
            temp.add(new CourseScore("总分", score.getChinese() + score.getMath() + score.getEnglish() + score.getPolotics() + score.getHistory() + score.getGeo() + score.getPhysics() + score.getChemistry() + score.getBiology(), score.getCname()));
            scores.add(temp);
        }
        return scores;
    }


}
