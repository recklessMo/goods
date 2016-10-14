package com.recklessmo.model.score;

/**
 * Created by hpf on 9/29/16.
 */
public class OverAllResult {

    private String result;
    private long examId;
    private long gradeId;
    private long classId;


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
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
}
