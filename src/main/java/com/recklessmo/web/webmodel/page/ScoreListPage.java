package com.recklessmo.web.webmodel.page;

/**
 * Created by hpf on 10/28/16.
 */
public class ScoreListPage extends Page{
    private long examId;
    //查询那个sid
    private long sid;
    //查询哪个班级
    private long classId;

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }
}
