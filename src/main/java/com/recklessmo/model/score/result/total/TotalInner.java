package com.recklessmo.model.score.result.total;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 1/8/17.
 */
public class TotalInner {

    private long id;
    private String name;
    private int full;
    private int best;
    private int good;
    private int qualified;
    private int totalCount;
    private int absenseCount;
    private double max = -100.0;
    private double min = 200.0;

    private double sum = 0.0;
    private double avg = 0.0;
    private double avgGap = 0.0;
    private double stdDev = 0.0;

    //用于计算标准差
    @JSONField(serialize = false)
    private List<Double> scoreList = new LinkedList<>();

    public TotalInner(){

    }

    public TotalInner(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public double getAvgGap() {
        return avgGap;
    }

    public void setAvgGap(double avgGap) {
        this.avgGap = avgGap;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAbsenseCount() {
        return absenseCount;
    }

    public void setAbsenseCount(int absenseCount) {
        this.absenseCount = absenseCount;
    }

    public List<Double> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Double> scoreList) {
        this.scoreList = scoreList;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getStdDev() {
        return stdDev;
    }

    public void setStdDev(double stdDev) {
        this.stdDev = stdDev;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
