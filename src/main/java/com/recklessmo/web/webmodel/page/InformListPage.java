package com.recklessmo.web.webmodel.page;

/**
 * Created by hpf on 10/28/16.
 */
public class InformListPage extends Page{

    private long gradeId;
    private long classId;
    private long opId;

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

    public long getOpId() {
        return opId;
    }

    public void setOpId(long opId) {
        this.opId = opId;
    }
}
