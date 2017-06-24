package com.recklessmo.model.student;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.support.odps.udf.JSONArrayAdd;

import java.util.Date;
import java.util.List;

/**
 * Created by hpf on 7/23/16.
 * 插入学生信息的model
 */
public class StudentInfo {

    private long id;
    private long orgId;
    private String sid;
    private String name="";
    private String parentName="";
    private String phone="";
    private String scn="";
    private int gender;
    private Date birth;
    private String birthStr;
    private String homeTown = "";
    private String people = "";
    private String birthTown = "";
    private String address = "";
    private String qq = "";
    private String wechat = "";
//    //暂时先不以结构化来表示,似乎json串就够了
//    private String educations;
//    private String relations;
    private long gradeId;
    private String gradeName = "";
    private String gradeOtherName = "";
    private long classId;
    private String className = "";
    private String job = "";
    private String wechatId="";

    public String getGradeOtherName() {
        return gradeOtherName;
    }

    public void setGradeOtherName(String gradeOtherName) {
        this.gradeOtherName = gradeOtherName;
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

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public long getGradeId() {
        return gradeId;
    }

    public void setGradeId(long gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getScn() {
        return scn;
    }

    public void setScn(String scn) {
        this.scn = scn;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getBirthStr() {
        return birthStr;
    }

    public void setBirthStr(String birthStr) {
        this.birthStr = birthStr;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getBirthTown() {
        return birthTown;
    }

    public void setBirthTown(String birthTown) {
        this.birthTown = birthTown;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

//    public String getEducations() {
//        return educations;
//    }
//
//    public void setEducations(String educations) {
//        this.educations = educations;
//    }
//
//    public String getRelations() {
//        return relations;
//    }
//
//    public void setRelations(String relations) {
//        this.relations = relations;
//    }


//    /**
//     * JSON FIELD 会根据name字段来找到set方法,然后根据set方法的参数去读取对应的string字段来进行解析?
//     * @return
//     */
//
//    @JSONField(name="educationList")
//    public JSONArray getEducationList() {
//        return JSON.parseArray(educations);
//    }
//
//    @JSONField(name="educationList")
//    public void setEducationList(JSONArray educations) {
//        this.educations = JSON.toJSONString(educations);
//    }
//
//    @JSONField(name="relationList")
//    public JSONArray getRelationList() {
//        return JSON.parseArray(educations);
//    }
//
//    @JSONField(name="relationList")
//    public void setRelationList(JSONArray relations) {
//        this.relations = JSON.toJSONString(relations);
//    }

}
