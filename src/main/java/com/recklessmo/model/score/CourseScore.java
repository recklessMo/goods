package com.recklessmo.model.score;

import com.recklessmo.model.setting.Course;

import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
public class CourseScore {

    private String name;
    private double score;

    public CourseScore(){

    }

    public CourseScore(String name, double score){
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}