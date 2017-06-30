package com.recklessmo.model.wechat.page;

/**
 * Created by hpf on 3/26/17.
 */
public class WechatIndexModel {

    private String orgName;

    private int totalGradeCount;
    private int totalClassCount;
    private String principal;
    private String created;

    private String gradeName;
    private int gradeClassCount;
    private String gradeCharger;
    private String gradePhone;

    private String className;
    private int classTotalCount;
    private String classCharger;
    private String classPhone;

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getClassTotalCount() {
        return classTotalCount;
    }

    public void setClassTotalCount(int classTotalCount) {
        this.classTotalCount = classTotalCount;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public int getTotalGradeCount() {
        return totalGradeCount;
    }

    public void setTotalGradeCount(int totalGradeCount) {
        this.totalGradeCount = totalGradeCount;
    }

    public int getTotalClassCount() {
        return totalClassCount;
    }

    public void setTotalClassCount(int totalClassCount) {
        this.totalClassCount = totalClassCount;
    }


    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public int getGradeClassCount() {
        return gradeClassCount;
    }

    public void setGradeClassCount(int gradeClassCount) {
        this.gradeClassCount = gradeClassCount;
    }

    public String getGradeCharger() {
        return gradeCharger;
    }

    public void setGradeCharger(String gradeCharger) {
        this.gradeCharger = gradeCharger;
    }

    public String getGradePhone() {
        return gradePhone;
    }

    public void setGradePhone(String gradePhone) {
        this.gradePhone = gradePhone;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassCharger() {
        return classCharger;
    }

    public void setClassCharger(String classCharger) {
        this.classCharger = classCharger;
    }

    public String getClassPhone() {
        return classPhone;
    }

    public void setClassPhone(String classPhone) {
        this.classPhone = classPhone;
    }
}
