package com.recklessmo.model.score.inner;

/**
 * Created by hpf on 10/9/16.
 */
public class CourseGapSetting {

    private double start;
    private double end;

    public CourseGapSetting(){

    }

    public CourseGapSetting(double s, double e){
        start = s;
        end = e;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }
}