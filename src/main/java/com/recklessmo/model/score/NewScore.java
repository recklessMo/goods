package com.recklessmo.model.score;

import com.recklessmo.model.setting.Course;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
public class NewScore {

    private long sid;
    private long examId;
    //通过sid获取对应的cid,set进去
    private long cid;
    private String cname;
    private List<CourseScore> courseScoreList = new LinkedList<>();



    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void add(CourseScore courseScore){
        courseScoreList.add(courseScore);
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public List<CourseScore> getCourseScoreList() {
        return courseScoreList;
    }

    public void setCourseScoreList(List<CourseScore> courseScoreList) {
        this.courseScoreList = courseScoreList;
    }
}
