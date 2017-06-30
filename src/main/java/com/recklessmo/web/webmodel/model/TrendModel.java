package com.recklessmo.web.webmodel.model;

import java.util.List;

/**
 * Created by hpf on 4/10/17.
 */
public class TrendModel {

    private List<String> examTypes;
    private String sid;
    private int showType;

    public List<String> getExamTypes() {
        return examTypes;
    }

    public void setExamTypes(List<String> examTypes) {
        this.examTypes = examTypes;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }
}
