package com.recklessmo.model.score;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.recklessmo.model.setting.Course;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
public class Score {

    //联合主键
    private long orgId;
    private String sid;
    private long examId;
    private String examName;
    private String examType;
    private Date examTime;
    private Date created;
    //json存储每次考试对应的成绩详情
    private String detail;
    //对应的内部数据结构
    private String name;
    private long gradeId;
    private String gradeName;
    private long classId;
    private String className;
    //班级类型
    private String classType;
    //班级类别
    private String classLevel;
    private List<CourseScore> courseScoreList = new LinkedList<>();

    public String getDetail() {
        return JSON.toJSONString(courseScoreList);
    }

    public void setDetail(String detail) {
        this.detail = detail;
        if(StringUtils.isNotEmpty(detail)) {
            courseScoreList = JSON.parseObject(detail, new TypeReference<List<CourseScore>>(){});
        }
    }

    public Date getExamTime() {
        return examTime;
    }

    public void setExamTime(Date examTime) {
        this.examTime = examTime;
    }

    public Date getCreated() {
        return created;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public long getGradeId() {
        return gradeId;
    }

    public void setGradeId(long gradeId) {
        this.gradeId = gradeId;
    }

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public List<CourseScore> getCourseScoreList() {
        return courseScoreList;
    }

    public void setCourseScoreList(List<CourseScore> courseScoreList) {
        this.courseScoreList = courseScoreList;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }
}
