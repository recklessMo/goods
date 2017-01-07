package com.recklessmo.model.score;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.recklessmo.model.score.inner.CourseTotalSetting;
import com.recklessmo.model.setting.Course;
import javafx.beans.binding.ObjectExpression;

import java.util.Date;

/**
 * Created by hpf on 9/30/16.
 */
public class ScoreTemplate {

    /**
     * 数据库字段
     */
    private long id;
    private String name = "default";
    //代表是哪种分析的模板
    private int type = 0;
    private String detail;

    private Date created;
    private Date updated;

    /**
     * 转换方法
     * 把多个字段转换成为json
     */
    public void toJsonDetail(){
        JSONObject object = new JSONObject();
        object.put("courseTotalSetting", courseTotalSetting);
        detail = object.toJSONString();
    }

    /**
     * 将json转换成为对应的内存字段
     */
    public void parseJsonDetail(){
        JSONObject object = JSON.parseObject(detail);
        courseTotalSetting = JSON.parseObject(object.get("courseTotalSetting").toString(), CourseTotalSetting.class);
    }

    /**
     * 内存和前端交互字段
     */
    private CourseTotalSetting courseTotalSetting;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public CourseTotalSetting getCourseTotalSetting() {
        return courseTotalSetting;
    }

    public void setCourseTotalSetting(CourseTotalSetting courseTotalSetting) {
        this.courseTotalSetting = courseTotalSetting;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
