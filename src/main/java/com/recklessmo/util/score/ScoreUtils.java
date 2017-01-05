package com.recklessmo.util.score;

import com.recklessmo.model.score.CourseScore;
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
    public static CourseScore changeScoreToCourseScore(List<Score> scoreList){

        List<String> courseList = new LinkedList<>();
        courseList.add("语文");
        courseList.add("数学");
        courseList.add("英语");
        courseList.add("政治");
        courseList.add("历史");
        courseList.add("地理");
        courseList.add("物理");
        courseList.add("化学");
        courseList.add("生物");

        CourseScore courseScore = new CourseScore();
        courseScore.setCourseList(courseList);

        List<List<Double>> scores = new LinkedList<>();

        scores.add(new LinkedList<>());
        scores.add(new LinkedList<>());
        scores.add(new LinkedList<>());
        scores.add(new LinkedList<>());
        scores.add(new LinkedList<>());
        scores.add(new LinkedList<>());
        scores.add(new LinkedList<>());
        scores.add(new LinkedList<>());
        scores.add(new LinkedList<>());



        for(Score score : scoreList){
            scores.get(0).add(score.getChinese());
            scores.get(1).add(score.getMath());
            scores.get(2).add(score.getEnglish());
            scores.get(3).add(score.getPolotics());
            scores.get(4).add(score.getHistory());
            scores.get(5).add(score.getGeo());
            scores.get(6).add(score.getPhysics());
            scores.get(7).add(score.getChemistry());
            scores.get(8).add(score.getBiology());
        }

        courseScore.setScoreList(scores);
        return courseScore;
    }


}
