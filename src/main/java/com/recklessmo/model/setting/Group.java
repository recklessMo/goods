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

    private long classId;
    private long gradeId;
    private String className;
    private String charger;
    private String phone;
    private String detail;

    private Map<String, CourseTeacher> courseClassMap = new LinkedHashMap<>();
    private Map<String, ScheduleCourse> scheduleClassMap = new LinkedHashMap<>();

    public String getCourseClass() {
        return JSON.toJSONString(courseClassMap);
    }

    public void setCourseClass(String str) {
        this.courseClassMap = JSON.parseObject(str, new TypeReference<Map<String, CourseTeacher>>(){});
    }

    public String getScheduleClass() {
        return JSON.toJSONString(scheduleClassMap);
    }

    public void setScheduleClass(String str) {
        this.scheduleClassMap = JSON.parseObject(str, new TypeReference<Map<String, ScheduleCourse>>(){});
    }

    public Map<String, ScheduleCourse> getScheduleClassMap() {
        return scheduleClassMap;
    }

    public void setScheduleClassMap(Map<String, ScheduleCourse> scheduleClassMap) {
        this.scheduleClassMap = scheduleClassMap;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Map<String, CourseTeacher> getCourseClassMap() {
        return courseClassMap;
    }

    public void setCourseClassMap(Map<String, CourseTeacher> courseClassMap) {
        this.courseClassMap = courseClassMap;
    }
}
