package com.recklessmo.model.score;

import com.recklessmo.model.setting.Course;

import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
public class CourseScore {

    private String courseName;
    private double score;
    private int rank;
    //标记是否缺考, 1代表缺考
    private int flag;

    public CourseScore(){

    }

    public CourseScore(String courseName, double score, int rank){
        this.courseName = courseName;
        this.score = score;
        this.rank = rank;
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