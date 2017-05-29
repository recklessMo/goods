package com.recklessmo.model.score.inner;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 29/05/2017.
 */
public class ScorePointInner {

    private long classId;
    private String className;
    private List<ScorePointPair> classInnerList = new LinkedList<>();

    public ScorePointInner(){

    }

    public ScorePointInner(long id, String name){
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

    public List<ScorePointPair> getClassInnerList() {
        return classInnerList;
    }

    public void setClassInnerList(List<ScorePointPair> classInnerList) {
        this.classInnerList = classInnerList;
    }
}
