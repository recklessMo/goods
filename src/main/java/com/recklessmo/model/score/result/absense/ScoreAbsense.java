package com.recklessmo.model.score.result.absense;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 4/15/17.
 */
public class ScoreAbsense {

    private String courseName;
    private int totalCount;
    private List<String> nameList = new LinkedList<>();
    private List<String> sidList = new LinkedList<>();

    public List<String> getSidList() {
        return sidList;
    }

    public void setSidList(List<String> sidList) {
        this.sidList = sidList;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }
}
