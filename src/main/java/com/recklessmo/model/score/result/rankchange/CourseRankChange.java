package com.recklessmo.model.score.result.rankchange;

/**
 * Created by hpf on 4/14/17.
 */
public class CourseRankChange {

    private String courseName;
    private Double firstScore;
    private int firstRank;
    private Double secondScore;
    private int secondRank;
    private Double scoreChange;
    private int rankGapNum;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Double getFirstScore() {
        return firstScore;
    }

    public void setFirstScore(Double firstScore) {
        this.firstScore = firstScore;
    }

    public int getFirstRank() {
        return firstRank;
    }

    public void setFirstRank(int firstRank) {
        this.firstRank = firstRank;
    }

    public Double getSecondScore() {
        return secondScore;
    }

    public void setSecondScore(Double secondScore) {
        this.secondScore = secondScore;
    }

    public int getSecondRank() {
        return secondRank;
    }

    public void setSecondRank(int secondRank) {
        this.secondRank = secondRank;
    }

    public Double getScoreChange() {
        return scoreChange;
    }

    public void setScoreChange(Double scoreChange) {
        this.scoreChange = scoreChange;
    }

    public int getRankGapNum() {
        return rankGapNum;
    }

    public void setRankGapNum(int rankGapNum) {
        this.rankGapNum = rankGapNum;
    }
}
