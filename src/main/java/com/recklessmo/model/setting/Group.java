package com.recklessmo.model.setting;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.recklessmo.model.course.CourseTeacher;
import com.recklessmo.model.course.ScheduleCourse;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by hpf on 8/17/16.
 */
public class Group {

    private long orgId;
    private long classId;
    private long gradeId;
    private String className;
    private String classType;
    private String classLevel;
    private String charger;
    private String phone = "";

    private Map<String, CourseTeacher> courseTeacherMap = new LinkedHashMap<>();
    private Map<String, ScheduleCourse> scheduleCourseMap = new LinkedHashMap<>();

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(String classLevel) {
        this.classLevel = classLevel;
    }

    public String getCourseTeacher() {
        return JSON.toJSONString(courseTeacherMap);
    }

    public void setCourseTeacher(String str) {
        this.courseTeacherMap = JSON.parseObject(str, new TypeReference<Map<String, CourseTeacher>>(){});
    }

    public String getScheduleCourse() {
        return JSON.toJSONString(scheduleCourseMap);
    }

    public void setScheduleCourse(String str) {
        this.scheduleCourseMap = JSON.parseObject(str, new TypeReference<Map<String, ScheduleCourse>>(){});
    }

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public long getGradeId() {
        return gradeId;
    }

    public void setGradeId(long gradeId) {
        this.gradeId = gradeId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCharger() {
        return charger;
    }

    public void setCharger(String charger) {
        this.charger = charger;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Map<String, CourseTeacher> getCourseTeacherMap() {
        return courseTeacherMap;
    }

    public void setCourseTeacherMap(Map<String, CourseTeacher> courseTeacherMap) {
        this.courseTeacherMap = courseTeacherMap;
    }

    public Map<String, ScheduleCourse> getScheduleCourseMap() {
        return scheduleCourseMap;
    }

    public void setScheduleCourseMap(Map<String, ScheduleCourse> scheduleCourseMap) {
        this.scheduleCourseMap = scheduleCourseMap;
    }
}
