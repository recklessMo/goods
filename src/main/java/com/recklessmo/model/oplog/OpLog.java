package com.recklessmo.model.oplog;

import java.util.Date;

/**
 * Created by hpf on 4/19/17.
 */
public class OpLog {

    private long id;
    private long orgId;
    private String sid;
    private int type;
    private String detail;
    private long opId;
    private String opName;
    private Date opTime;
    private Date created;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public long getOpId() {
        return opId;
    }

    public void setOpId(long opId) {
        this.opId = opId;
    }

    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }
}
