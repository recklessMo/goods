package com.recklessmo.model.wechat;

import java.util.Date;

/**
 * Created by hpf on 1/12/17.
 */
public class WechatMessage {

    public static int MSG_DIRECTION_SEND = 1;
    public static int MSG_DIRECTION_RECEIVE = 2;

    public static int MSG_TYPE_TEXT = 1;

    private long id;
    private long orgId;
    private String openId;
    private String sid;
    private long userId = 0;
    private String userName = "";

    private int type;
    private int messageType;
    private String message;
    private Date created;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
