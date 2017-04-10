package com.recklessmo.web.webmodel.model;

import java.util.List;

/**
 * Created by hpf on 4/10/17.
 */
public class SelfModel {

    private long examId;
    private List<String> sidList;
    private long templateId;

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public List<String> getSidList() {
        return sidList;
    }

    public void setSidList(List<String> sidList) {
        this.sidList = sidList;
    }

    public long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(long templateId) {
        this.templateId = templateId;
    }
}
