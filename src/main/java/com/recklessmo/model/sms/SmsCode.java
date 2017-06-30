package com.recklessmo.model.sms;

import java.util.Date;

/**
 * Created by hpf on 2/9/17.
 */
public class SmsCode {

    public static long DEFAULT_GAP = 30 * 60 * 1000;

    //姓名
    private String name;
    //电话
    private String phone;
    //验证码
    private String code;
    //生成时间
    private Date time;
    //过期时间
    private Date expire;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }
}
