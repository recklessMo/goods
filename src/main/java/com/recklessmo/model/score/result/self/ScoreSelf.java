package com.recklessmo.model.score.result.self;

import java.util.List;

/**
 * Created by hpf on 4/10/17.
 */
public class ScoreSelf {

    private List<CourseSelf> courseInfoList;
    private List<String> nameList;
    private List<ScoreSelfInner> scoreSelfInnerList;

    public List<CourseSelf> getCourseInfoList() {
        return courseInfoList;
    }

    public void setCourseInfoList(List<CourseSelf> courseInfoList) {
        this.courseInfoList = courseInfoList;
    }

    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public List<ScoreSelfInner> getScoreSelfInnerList() {
        return scoreSelfInnerList;
    }

    public void setScoreSelfInnerList(List<ScoreSelfInner> scoreSelfInnerList) {
        this.scoreSelfInnerList = scoreSelfInnerList;
    }
}
