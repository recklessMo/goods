package com.recklessmo.model.score.result.rank;


import com.recklessmo.model.setting.Course;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 1/8/17.
 */
public class CourseRank {

    //名字
    private long courseId;
    private String courseName;

    private List<RankGap> gapList = new LinkedList<>();
    private List<RankInner> gapInnerList = new LinkedList<>();

    public CourseRank(){

    }

    public CourseRank(long courseId, String courseName, List<RankGap> rankGapList){
        this.courseId = courseId;
        this.courseName = courseName;
        this.gapList = rankGapList;
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

    public List<RankGap> getGapList() {
        return gapList;
    }

    public void setGapList(List<RankGap> gapList) {
        this.gapList = gapList;
    }

    public List<RankInner> getGapInnerList() {
        return gapInnerList;
    }

    public void setGapInnerList(List<RankInner> gapInnerList) {
        this.gapInnerList = gapInnerList;
    }
}
