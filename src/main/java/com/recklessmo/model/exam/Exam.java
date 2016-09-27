package com.recklessmo.model.exam;

/**
 * Created by hpf on 8/29/16.
 */
public class Exam {

    public static final int EXAM_INIT = 0;
    public static final int EXAM_ANALYZED = 1;
    public static final int EXAM_RE_ANALYZED = 2;

    private long examId;
    private String examName;
    private long gradeId;
    //代表考试的分析状态
    private int status;


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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
