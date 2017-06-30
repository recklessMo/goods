package com.recklessmo.model.score.result.rank;

/**
 * Created by hpf on 1/8/17.
 */
public class RankGap {

    private int start;
    private int end;

    public RankGap(int start, int end){
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
