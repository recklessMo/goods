package com.recklessmo.model.score;

/**
 * Created by hpf on 8/29/16.
 */
public class Score {

    private long sid;
    private long examId;
    //通过sid获取对应的cid,set进去
    private String name;
    private long cid;

    private double total;
    private double chinese;
    private double math;
    private double english;
    private double polotics;
    private double history;
    private double geo;
    private double physics;
    private double chemistry;
    private double biology;

    //用于显示排名
    private long chineseRank;
    private long mathRank;
    private long englishRank;
    private long poloticsRank;
    private long historyRank;
    private long geoRank;
    private long physicsRank;
    private long chemistryRank;
    private long biologyRank;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getChineseRank() {
        return chineseRank;
    }

    public void setChineseRank(long chineseRank) {
        this.chineseRank = chineseRank;
    }

    public long getMathRank() {
        return mathRank;
    }

    public void setMathRank(long mathRank) {
        this.mathRank = mathRank;
    }

    public long getEnglishRank() {
        return englishRank;
    }

    public void setEnglishRank(long englishRank) {
        this.englishRank = englishRank;
    }

    public long getPoloticsRank() {
        return poloticsRank;
    }

    public void setPoloticsRank(long poloticsRank) {
        this.poloticsRank = poloticsRank;
    }

    public long getHistoryRank() {
        return historyRank;
    }

    public void setHistoryRank(long historyRank) {
        this.historyRank = historyRank;
    }

    public long getGeoRank() {
        return geoRank;
    }

    public void setGeoRank(long geoRank) {
        this.geoRank = geoRank;
    }

    public long getPhysicsRank() {
        return physicsRank;
    }

    public void setPhysicsRank(long physicsRank) {
        this.physicsRank = physicsRank;
    }

    public long getChemistryRank() {
        return chemistryRank;
    }

    public void setChemistryRank(long chemistryRank) {
        this.chemistryRank = chemistryRank;
    }

    public long getBiologyRank() {
        return biologyRank;
    }

    public void setBiologyRank(long biologyRank) {
        this.biologyRank = biologyRank;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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
