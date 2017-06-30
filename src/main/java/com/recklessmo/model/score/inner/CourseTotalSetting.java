package com.recklessmo.model.score.inner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hpf on 10/9/16.
 */
public class CourseTotalSetting {

    private String courseName;
    private double full;
    private double best;
    private double good;
    private double qualified;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getFull() {
        return full;
    }

    public void setFull(double full) {
        this.full = full;
    }

    public double getBest() {
        return best;
    }

    public void setBest(double best) {
        this.best = best;
    }

    public double getGood() {
        return good;
    }

    public void setGood(double good) {
        this.good = good;
    }

    public double getQualified() {
        return qualified;
    }

    public void setQualified(double qualified) {
        this.qualified = qualified;
    }
}