package com.recklessmo.model.score.result.gap;


import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 1/8/17.
 */
public class CourseGap {

    //名字
    private long courseId;
    private String name;

    private List<ScoreGap> gapList;
    private List<GapInner> gapInnerList;

    public CourseGap(){

    }

    public CourseGap(long courseId, String name, List<ScoreGap> gapList){
        this.courseId = courseId;
        this.name = name;
        this.gapList = gapList;
        this.gapInnerList = new LinkedList<>();
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ScoreGap> getGapList() {
        return gapList;
    }

    public void setGapList(List<ScoreGap> gapList) {
        this.gapList = gapList;
    }

    public List<GapInner> getGapInnerList() {
        return gapInnerList;
    }

    public void setGapInnerList(List<GapInner> gapInnerList) {
        this.gapInnerList = gapInnerList;
    }
}
