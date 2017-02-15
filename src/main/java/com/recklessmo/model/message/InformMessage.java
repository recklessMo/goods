package com.recklessmo.model.message;

import java.util.Date;

/**
 *
 * 通知消息
 *
 * Created by hpf on 2/10/17.
 */
public class InformMessage {

    // 1. 纯微信
    // 2. 纯短信
    // 3. 微信和短信同时发送
    // 4. 先尝试发微信, 微信发送失败则发送短信
    public static int SEND_TYPE_WECHAT = 1;
    public static int SEND_TYPE_SMS = 2;
    public static int SEND_TYPE_WECHAT_AND_SMS = 3;
    public static int SEND_TYPE_WECHAT_BEFORE_SMS = 4;

    private long id;
    //通知内容
    private String text;
    //消息发送类型
    private int sendType;
    //创建时间
    private Date created;
    //创建人
    private long create_user;
    //是否删除
    private int deleted;
    //发送范围



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public long getCreate_user() {
        return create_user;
    }

    public void setCreate_user(long create_user) {
        this.create_user = create_user;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}
