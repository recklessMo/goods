package com.recklessmo.model.sms;

import java.util.Date;

/**
 * Created by hpf on 2/9/17.
 */
public class SmsCode {

    public static long DEFAULT_GAP = 60 * 60 * 1000;

    //id
    private long id;
    //验证码
    private String code;
    //生成时间
    private Date time;
    //过期时间
    private Date expire;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
