package com.recklessmo.model.score.result.gap;

/**
 * Created by hpf on 1/8/17.
 */
public class ScoreGap {

    private Double start;
    private Double end;

    public ScoreGap(){

    }

    public ScoreGap(Double start, Double end){
        this.start = start;
        this.end = end;
    }

    public Double getStart() {
        return start;
    }

    public void setStart(Double start) {
        this.start = start;
    }

    public Double getEnd() {
        return end;
    }

    public void setEnd(Double end) {
        this.end = end;
    }

    @Override
    public int hashCode(){
        return start.hashCode() + end.hashCode();
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o instanceof ScoreGap){
            ScoreGap other = (ScoreGap)o;
            return this.start.equals(other.getStart()) && this.end.equals(other.getEnd());

        }
        return false;
    }

}
