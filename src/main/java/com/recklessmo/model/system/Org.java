package com.recklessmo.model.system;

/**
 * Created by hpf on 2/20/17.
 */
public class Org {

    private long orgId;
    private String orgName;
    private String created;
    private String adminName;
    private String adminPhone;
    private int type;
    private int wechatStatus;


    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getWechatStatus() {
        return wechatStatus;
    }

    public void setWechatStatus(int wechatStatus) {
        this.wechatStatus = wechatStatus;
    }

}
