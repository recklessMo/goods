package com.recklessmo.model.stock;

import java.util.Date;

/**
 * Created by hpf on 8/5/16.
 */
public class Goods {

    public static int GOODS_NORMAL = 0;
    public static int GOODS_DELETE = 1;


    private long id;
    private long orgId;

    //唯一确定一种物资
    private String name = "";
    private String pym = "";
    private String gg = "";
    //类别,教材,耗材什么的
    private String type = "";
    private String dw = "";
    private String cjmc = "";
    private int dcbz = 0;
    //添加时间
    private Date inTime = new Date();

    private long inUserId;
    private String inUserName = "";

    //剩余数量
    private long currentCount;
    private int status = 0;
    private Date updated = new Date();
    //备注
    private String comment = "";

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getPym() {
        return pym;
    }

    public void setPym(String pym) {
        this.pym = pym;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public int getDcbz() {
        return dcbz;
    }

    public void setDcbz(int dcbz) {
        this.dcbz = dcbz;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getInUserName() {
        return inUserName;
    }

    public void setInUserName(String inUserName) {
        this.inUserName = inUserName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getInUserId() {
        return inUserId;
    }

    public void setInUserId(long inUserId) {
        this.inUserId = inUserId;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(long currentCount) {
        this.currentCount = currentCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGg() {
        return gg;
    }

    public void setGg(String gg) {
        this.gg = gg;
    }

    public String getCjmc() {
        return cjmc;
    }

    public void setCjmc(String cjmc) {
        this.cjmc = cjmc;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
