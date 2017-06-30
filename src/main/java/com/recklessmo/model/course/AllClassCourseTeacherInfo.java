package com.recklessmo.model.course;

import com.recklessmo.model.setting.Course;

import java.util.List;

/**
 * Created by hpf on 1/20/17.
 */
public class AllClassCourseTeacherInfo {

    private List<Course> courseList;
    private List<SingleClassCourseTeacherInfo> singleClassCourseTeacherInfoList;

    public List<SingleClassCourseTeacherInfo> getSingleClassCourseTeacherInfoList() {
        return singleClassCourseTeacherInfoList;
    }

    public void setSingleClassCourseTeacherInfoList(List<SingleClassCourseTeacherInfo> singleClassCourseTeacherInfoList) {
        this.singleClassCourseTeacherInfoList = singleClassCourseTeacherInfoList;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }
}
