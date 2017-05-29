package com.recklessmo.model.score.result.scorepoint;

import com.recklessmo.model.score.inner.ScorePointInner;
import com.recklessmo.util.CheckUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 29/05/2017.
 */
public class ScorePoint {

    private long courseId;
    private String courseName;
    private List<ScorePointInner> scorePointInnerList = new LinkedList<>();

    public ScorePoint(){

    }

    public ScorePoint(long courseId, String courseName){
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<ScorePointInner> getScorePointInnerList() {
        return scorePointInnerList;
    }

    public void setScorePointInnerList(List<ScorePointInner> scorePointInnerList) {
        this.scorePointInnerList = scorePointInnerList;
    }
}
