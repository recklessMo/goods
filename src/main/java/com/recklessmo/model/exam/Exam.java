package com.recklessmo.model.exam;

import java.util.Date;

/**
 * Created by hpf on 8/29/16.
 */
public class Exam {

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
