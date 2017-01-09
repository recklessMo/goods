package com.recklessmo.model.score.result;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 1/8/17.
 */
public class ClassTotal {

    private long cid;
    private String className;

    private List<TotalInner> courseTotalList = new LinkedList<>();


    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<TotalInner> getCourseTotalList() {
        return courseTotalList;
    }

    public void setCourseTotalList(List<TotalInner> courseTotalList) {
        this.courseTotalList = courseTotalList;
    }
}
