package com.recklessmo.model.wechat.page;

import java.util.List;

/**
 * Created by hpf on 3/26/17.
 */
public class WechatTemplateMsgModel {
    //发送给谁, 进行去重后发送
    private List<GradePair> gradePairList;
    private List<String> sidList;
    //发送类型
    private int type;
    private long workId;

    public long getWorkId() {
        return workId;
    }

    public void setWorkId(long workId) {
        this.workId = workId;
    }

    public List<GradePair> getGradePairList() {
        return gradePairList;
    }

    public void setGradePairList(List<GradePair> gradePairList) {
        this.gradePairList = gradePairList;
    }

    public List<String> getSidList() {
        return sidList;
    }

    public void setSidList(List<String> sidList) {
        this.sidList = sidList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
