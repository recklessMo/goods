package com.recklessmo.web.webmodel.page;

/**
 * Created by hpf on 10/28/16.
 */
public class ExamListPage extends Page{

    private String examType;
    private long gradeId;
    private long classId;

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
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
