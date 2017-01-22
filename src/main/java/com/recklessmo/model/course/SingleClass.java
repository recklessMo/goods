package com.recklessmo.model.course;

import com.recklessmo.model.setting.Course;
import com.recklessmo.model.setting.CourseClass;
import com.recklessmo.model.setting.Group;

import java.util.List;

/**
 * Created by hpf on 1/20/17.
 */
public class SingleClass {

    private long groupId;
    private long gradeId;
    private String groupName;
    private String gradeName;
    private List<CourseClass> courseClassList;


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

    public List<CourseClass> getCourseClassList() {
        return courseClassList;
    }

    public void setCourseClassList(List<CourseClass> courseClassList) {
        this.courseClassList = courseClassList;
    }
}
