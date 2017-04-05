package com.recklessmo.model.exam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
public class Exam {

    public static int EXAM_UN_UPLOADED = 0;
    public static int EXAM_UPLOADED = 1;

    //机构id
    private long orgId;
    //考试id
    private long examId;
    //考试名称
    private String examName;
    //所属年级
    private long gradeId;
    //所属班级
    //为0代表所有班级
    private long classId;
    //考试类型, 小考,周考,月考,期中考,期末考
    private int type;
    //代表考试成绩的上传状态
    private int uploadStatus;
    //考试时间
    private Date examTime;
    //学科列表
    private List<Long> courseList = new LinkedList<>();
    private String courseStr;

    public String getCourseStr() {
        return JSON.toJSONString(courseList);
    }

    public void setCourseStr(String courseStr) {
        this.courseStr = courseStr;
        if (StringUtils.isNotEmpty(courseStr)) {
            this.courseList = JSON.parseObject(courseStr, new TypeReference<List<Long>>() {});
        }
    }

    public List<Long> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Long> courseList) {
        this.courseList = courseList;
    }

    public Date getExamTime() {
        return examTime;
    }

    public void setExamTime(Date examTime) {
        this.examTime = examTime;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(int uploadStatus) {
        this.uploadStatus = uploadStatus;
    }
}
