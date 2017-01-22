package com.recklessmo.model.course;

import com.recklessmo.model.setting.Course;

import java.util.List;

/**
 * Created by hpf on 1/20/17.
 */
public class AllClass {

    private List<Course> courseList;
    private List<SingleClass> singleClassList;

    public List<SingleClass> getSingleClassList() {
        return singleClassList;
    }

    public void setSingleClassList(List<SingleClass> singleClassList) {
        this.singleClassList = singleClassList;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }
}
