package com.recklessmo.web.webmodel.model;

import java.util.List;

/**
 * Created by hpf on 4/10/17.
 */
public class ContrastModel {

    private List<String> examTypes;
    private List<String> sidList;
    private int showType;

    public List<String> getExamTypes() {
        return examTypes;
    }

    public void setExamTypes(List<String> examTypes) {
        this.examTypes = examTypes;
    }

    public List<String> getSidList() {
        return sidList;
    }

    public void setSidList(List<String> sidList) {
        this.sidList = sidList;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }
}
