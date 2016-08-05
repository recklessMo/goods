package com.recklessmo.model.stock;

import java.util.Date;

/**
 * Created by hpf on 8/5/16.
 */
public class Goods {

    private long id;
    //剩余数量
    private long currentCount;
    //唯一确定一种物资
    private String name = "";
    private String gg = "";
    private String cjmc = "";
    //备注
    private String comment = "";
    //类别,教材,耗材什么的
    private String type = "";

    //添加时间
    private Date inTime = new Date();

    private long inUserId;
    private int status;


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
