package com.recklessmo.model.score.inner;

/**
 * Created by hpf on 29/05/2017.
 */
public class ScorePointPair {

    private double point;
    private int count;

    public ScorePointPair(){

    }

    public ScorePointPair(double p, int c){
        this.point = p;
        this.count = c;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
