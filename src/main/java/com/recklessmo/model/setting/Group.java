package com.recklessmo.model.setting;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
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

    private Map<String, CourseClass> courseClassMap = new LinkedHashMap<>();
    private String courseClass;

    public String getCourseClass() {
        return JSON.toJSONString(courseClassMap);
    }

    public void setCourseClass(String courseClass) {
        this.courseClassMap = JSON.parseObject(courseClass, new TypeReference<Map<String, CourseClass>>(){});
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

    public Map<String, CourseClass> getCourseClassMap() {
        return courseClassMap;
    }

    public void setCourseClassMap(Map<String, CourseClass> courseClassMap) {
        this.courseClassMap = courseClassMap;
    }
}
