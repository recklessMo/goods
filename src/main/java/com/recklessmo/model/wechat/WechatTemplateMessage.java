package com.recklessmo.model.wechat;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * Created by hpf on 1/12/17.
 */
public class WechatTemplateMessage {

    public static int TYPE_ASSIGNMENTNOTICE = 1;
    public static String ASSIGNMENTNOTICE = "goqxAPYZbTnvUaPBRH19higWrfdxkqy6rtNGhn4xNKQ";

    private long orgId;
    private String sid;
    private String openId;

    private String templateId;
    private String url;
    private JSONObject data;


    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }
}
