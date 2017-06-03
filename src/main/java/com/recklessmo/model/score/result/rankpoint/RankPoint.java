package com.recklessmo.model.score.result.rankpoint;

import com.recklessmo.model.score.inner.RankPointInner;
import com.recklessmo.model.score.inner.ScorePointInner;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 29/05/2017.
 */
public class RankPoint {

    private long courseId;
    private String courseName;
    private List<RankPointInner> rankPointInnerList = new LinkedList<>();

    public RankPoint(){

    }

    public RankPoint(long courseId, String courseName){
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

    public List<RankPointInner> getRankPointInnerList() {
        return rankPointInnerList;
    }

    public void setRankPointInnerList(List<RankPointInner> rankPointInnerList) {
        this.rankPointInnerList = rankPointInnerList;
    }
}
