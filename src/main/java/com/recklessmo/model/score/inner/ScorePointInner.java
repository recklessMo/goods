package com.recklessmo.model.score.inner;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by hpf on 29/05/2017.
 */
public class ScorePointInner {

    private long classId;
    private String className;
    private Map<Double, Integer> scorePointPairMap = new TreeMap<>();
    private List<Map.Entry<Double, Integer>> scorePointPairList = null;

    public ScorePointInner(){

    }

    public ScorePointInner(long id, String name){
        this.classId = id;
        this.className = name;
    }

    public List<Map.Entry<Double, Integer>> getScorePointPairList() {
        return scorePointPairList;
    }

    public void setScorePointPairList(List<Map.Entry<Double, Integer>> scorePointPairList) {
        this.scorePointPairList = scorePointPairList;
    }

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<Double, Integer> getScorePointPairMap() {
        return scorePointPairMap;
    }

    public void setScorePointPairMap(Map<Double, Integer> scorePointPairMap) {
        this.scorePointPairMap = scorePointPairMap;
    }
}
