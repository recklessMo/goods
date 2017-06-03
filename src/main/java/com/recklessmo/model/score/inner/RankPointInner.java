package com.recklessmo.model.score.inner;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by hpf on 29/05/2017.
 */
public class RankPointInner {

    private long classId;
    private String className;
    private List<RankPointPair> rankPointPairList = new LinkedList<>();

    public RankPointInner(){

    }

    public RankPointInner(long id, String name){
        this.classId = id;
        this.className = name;
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

    public List<RankPointPair> getRankPointPairList() {
        return rankPointPairList;
    }

    public void setRankPointPairList(List<RankPointPair> rankPointPairList) {
        this.rankPointPairList = rankPointPairList;
    }
}
