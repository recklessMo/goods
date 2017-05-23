package com.recklessmo.model.message;

import java.util.Date;

/**
 *
 * 通知消息
 *
 * Created by hpf on 2/10/17.
 */
public class InformMessage {

    public static int STATUS_INIT = 0;
    public static int STATUS_SEND = 1;

    private long id;
    private long orgId;
    private long gradeId;
    private String gradeName;
    private String gradeOtherName;
    private long classId;
    private String className;
    private String type;//通知类型
    private String name;//通知名称
    private String text;//具体内容
    private Date created;
    private long opId;
    private String opName;
    private int deleted;
    private int totalCount;
    private int status;//发送状态， 是否已经进行推送了

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

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
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

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}
