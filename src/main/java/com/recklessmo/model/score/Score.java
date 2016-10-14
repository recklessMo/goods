package com.recklessmo.model.score;

/**
 * Created by hpf on 8/29/16.
 */
public class Score {

    private long sid;
    private String name;
    private long examId;
    //通过sid获取对应的cid,set进去
    private long cid;

    private double chinese;
    private double math;
    private double english;
    private double polotics;
    private double history;
    private double geo;
    private double physics;
    private double chemistry;
    private double biology;

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getChinese() {
        return chinese;
    }

    public void setChinese(double chinese) {
        this.chinese = chinese;
    }

    public double getMath() {
        return math;
    }

    public void setMath(double math) {
        this.math = math;
    }

    public double getEnglish() {
        return english;
    }

    public void setEnglish(double english) {
        this.english = english;
    }

    public double getPolotics() {
        return polotics;
    }

    public void setPolotics(double polotics) {
        this.polotics = polotics;
    }

    public double getHistory() {
        return history;
    }

    public void setHistory(double history) {
        this.history = history;
    }

    public double getGeo() {
        return geo;
    }

    public void setGeo(double geo) {
        this.geo = geo;
    }

    public double getPhysics() {
        return physics;
    }

    public void setPhysics(double physics) {
        this.physics = physics;
    }

    public double getChemistry() {
        return chemistry;
    }

    public void setChemistry(double chemistry) {
        this.chemistry = chemistry;
    }

    public double getBiology() {
        return biology;
    }

    public void setBiology(double biology) {
        this.biology = biology;
    }
}
