package com.recklessmo.model.score.inner;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by hpf on 29/05/2017.
 */
public class RankPointPair {

    private int rank;
    private double score;
    private String sid;
    private String name;

    public RankPointPair(){

    }

    public RankPointPair(int rank, double score, String sid, String name){
        this.rank = rank;
        this.score = score;
        this.sid = sid;
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
