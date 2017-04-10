package com.recklessmo.model.score.result.self;

import java.util.List;

/**
 * Created by hpf on 4/10/17.
 */
public class ScoreSelfInner {

    public String studentName;
    public List<Double> scoreList;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public List<Double> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Double> scoreList) {
        this.scoreList = scoreList;
    }
}
