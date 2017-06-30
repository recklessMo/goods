package com.recklessmo.web.webmodel.page;

/**
 * Created by hpf on 7/23/16.
 */
public class StudentPage extends Page{

    private long gradeId;
    private long classId;
    private int gender;
    private long examId;

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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }


}
