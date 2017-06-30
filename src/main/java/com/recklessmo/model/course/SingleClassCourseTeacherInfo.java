package com.recklessmo.model.course;

import java.util.List;

/**
 * Created by hpf on 1/20/17.
 */
public class SingleClassCourseTeacherInfo {

    private long groupId;
    private long gradeId;
    private String groupName;
    private String gradeName;
    private List<CourseTeacher> courseTeacherList;


    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getGradeId() {
        return gradeId;
    }

    public void setGradeId(long gradeId) {
        this.gradeId = gradeId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public List<CourseTeacher> getCourseTeacherList() {
        return courseTeacherList;
    }

    public void setCourseTeacherList(List<CourseTeacher> courseTeacherList) {
        this.courseTeacherList = courseTeacherList;
    }

    public static void main(String[] args){

    }
}
