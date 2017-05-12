package com.recklessmo.web.webmodel.model;

import java.util.List;

/**
 * Created by hpf on 4/10/17.
 */
public class TrendModel {

    private List<String> examTypes;
    private String sid;
    private String showType;

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

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }
}
