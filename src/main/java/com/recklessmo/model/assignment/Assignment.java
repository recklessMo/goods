package com.recklessmo.model.assignment;

import com.recklessmo.model.wechat.page.GradePair;

import java.util.Date;
import java.util.List;

/**
 * Created by hpf on 18/05/2017.
 */
public class Assignment {

    private long id;
    private long orgId;
    private long opId;
    private String opName;
    private String name;
    private String courseName;
    private long courseId;
    private long gradeId;
    private String gradeName;
    private String gradeOtherName;
    private long classId;
    private String className;
    private String content;
    private Date created;//创建时间
    private Date submit;//截止时间

    private int totalCount;
    private int doneCount;

    //用于微信端的一个字段
    private boolean status;

    public int getDoneCount() {
        return doneCount;
    }

    public void setDoneCount(int doneCount) {
        this.doneCount = doneCount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getGradeOtherName() {
        return gradeOtherName;
    }

    public void setGradeOtherName(String gradeOtherName) {
        this.gradeOtherName = gradeOtherName;
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

    public Date getSubmit() {
        return submit;
    }

    public void setSubmit(Date submit) {
        this.submit = submit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public long getOpId() {
        return opId;
    }

    public void setOpId(long opId) {
        this.opId = opId;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
