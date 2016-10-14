package com.recklessmo.service.score.model.total;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 9/29/16.
 */
public class SingleCourseTotal {

    //学科名称
    private String courseName = null;
    //参考总人数
    private int totalCount = 0;
    //总分
    private double totalScore = 0;
    //平均分
    private double avg = 0;
    //最高分
    private double max = 0;
    //最低分
    private double min = 150.0;
    //标准差
    private double stdAvg = 0.0;
    //满分
    private int full = 0;
    //优秀
    private int best = 0;
    //良好
    private int good = 0;
    //及格
    private int qualified = 0;
    //分数列表
    private List<Double> scoreList;


    public SingleCourseTotal(){
        scoreList = new LinkedList<>();
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getStdAvg() {
        return stdAvg;
    }

    public void setStdAvg(double stdAvg) {
        this.stdAvg = stdAvg;
    }

    public int getFull() {
        return full;
    }

    public void setFull(int full) {
        this.full = full;
    }

    public int getBest() {
        return best;
    }

    public void setBest(int best) {
        this.best = best;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public int getQualified() {
        return qualified;
    }

    public void setQualified(int qualified) {
        this.qualified = qualified;
    }

    public List<Double> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Double> scoreList) {
        this.scoreList = scoreList;
    }
}
