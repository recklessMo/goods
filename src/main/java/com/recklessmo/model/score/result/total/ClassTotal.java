package com.recklessmo.model.score.result.total;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 1/8/17.
 */
public class ClassTotal {

    private long classId;
    private String className;
    private List<TotalInner> courseTotalList = new LinkedList<>();

    public ClassTotal(){

    }

    public ClassTotal(long cid, String cname){
        this.classId = cid;
        this.className = cname;
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

    public List<TotalInner> getCourseTotalList() {
        return courseTotalList;
    }

    public void setCourseTotalList(List<TotalInner> courseTotalList) {
        this.courseTotalList = courseTotalList;
    }
}
