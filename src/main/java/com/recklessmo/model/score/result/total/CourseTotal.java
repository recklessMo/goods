package com.recklessmo.model.score.result.total;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 1/8/17.
 */
public class CourseTotal {

    private long courseId;
    private String courseName;
    private List<TotalInner> classTotalList = new LinkedList<>();

    public CourseTotal(){

    }

    public CourseTotal(String courseName, long courseId){
        this.courseName = courseName;
        this.courseId = courseId;
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

    public List<TotalInner> getClassTotalList() {
        return classTotalList;
    }

    public void setClassTotalList(List<TotalInner> classTotalList) {
        this.classTotalList = classTotalList;
    }
}
