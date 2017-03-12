package com.recklessmo.model.score;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.recklessmo.model.score.inner.CourseTotalSetting;
import com.recklessmo.model.setting.Course;
import javafx.beans.binding.ObjectExpression;

import java.util.Date;
import java.util.Map;

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
    private Date created;
    private Date updated;

    /**
     * 内存和前端交互字段
     */
    private Map<String, CourseTotalSetting> courseTotalSettingMap;

    public String getDetail() {
        return JSON.toJSONString(courseTotalSettingMap);
    }

    public void setDetail(String detail) {
        this.courseTotalSettingMap = JSON.parseObject(detail, new TypeReference<Map<String, CourseTotalSetting>>(){});
    }

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

    public Map<String, CourseTotalSetting> getCourseTotalSettingMap() {
        return courseTotalSettingMap;
    }

    public void setCourseTotalSettingMap(Map<String, CourseTotalSetting> courseTotalSettingMap) {
        this.courseTotalSettingMap = courseTotalSettingMap;
    }
}
