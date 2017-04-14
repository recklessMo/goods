package com.recklessmo.model.score.result.rankchange;

import com.recklessmo.model.score.result.rank.CourseRank;

import java.util.List;

/**
 * Created by hpf on 4/14/17.
 */
public class RankChange {

    private String sid;
    private String name;
    private String gradeName;
    private String className;
    private List<CourseRankChange> courseRankChangeList;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<CourseRankChange> getCourseRankChangeList() {
        return courseRankChangeList;
    }

    public void setCourseRankChangeList(List<CourseRankChange> courseRankChangeList) {
        this.courseRankChangeList = courseRankChangeList;
    }
}
