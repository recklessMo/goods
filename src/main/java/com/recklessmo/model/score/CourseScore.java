package com.recklessmo.model.score;

import com.recklessmo.model.setting.Course;

import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
public class CourseScore {

    private long courseId;
    private String courseName;
    private double score;
    //班级排名
    private int classRank;
    //年级排名
    private int rank;
    //标记是否缺考, 1代表缺考
    private int flag;

    public CourseScore(){

    }

    public CourseScore(String courseName,long courseId, double score, int rank){
        this.courseId = courseId;
        this.courseName = courseName;
        this.score = score;
        this.rank = rank;
    }

    public int getClassRank() {
        return classRank;
    }

    public void setClassRank(int classRank) {
        this.classRank = classRank;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}