package com.recklessmo.model.score.result;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 1/8/17.
 */
public class CourseTotal {

    private String courseName;
    private List<TotalInner> classTotalList = new LinkedList<>();

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<TotalInner> getClassTotalList() {
        return classTotalList;
    }

    public void setClassTotalList(List<TotalInner> classTotalList) {
        this.classTotalList = classTotalList;
    }
}
