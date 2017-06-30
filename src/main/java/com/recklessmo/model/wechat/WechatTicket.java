package com.recklessmo.model.wechat;

import java.util.Date;

/**
 * Created by hpf on 1/12/17.
 */
public class WechatTicket {

    //暂时先写死
    public static int SCENE_ID = 10;

    private long id;
    private long orgId;
    private String sid;
    private String ticket;
    private Date created;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
