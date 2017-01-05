package com.recklessmo.model.score;

import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
public class CourseScore {

    private List<String> courseList;
    private List<List<Double>> scoreList;


    public List<String> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<String> courseList) {
        this.courseList = courseList;
    }

    public List<List<Double>> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<List<Double>> scoreList) {
        this.scoreList = scoreList;
    }
}
