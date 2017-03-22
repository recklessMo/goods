package com.recklessmo.model.score;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import com.recklessmo.model.score.inner.CourseGapSetting;
import com.recklessmo.model.score.inner.CourseTotalSetting;
import com.recklessmo.model.setting.Course;
import javafx.beans.binding.ObjectExpression;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hpf on 9/30/16.
 */
public class ScoreTemplate {

    public static int TEMPLATE_TYPE_SCORE_TOTAL = 1;
    public static int TEMPLATE_TYPE_SCORE_GAP = 2;

    /**
     * 数据库字段
     */
    private long id;
    private String name = "default";
    //代表是哪种分析的模板
    private int type = 0;
    private Date created;
    private Date updated;

    //模板详细内容
    @JSONField(serialize = false)
    private String detail;

    /**
     * 内存和前端交互字段
     */
    //整体分析的模板, type为1
    private Map<String, CourseTotalSetting> courseTotalSettingMap;
    //分数段分析的模板, type为2
    private Map<String, String> courseGapSettingMap;


    public String getDetail() {
        if (type == TEMPLATE_TYPE_SCORE_TOTAL) {
            return JSON.toJSONString(courseTotalSettingMap);
        }else if(type == TEMPLATE_TYPE_SCORE_GAP){
            return JSON.toJSONString(courseGapSettingMap);
        }
        return "";
    }

    public void setDetail(String detail) {
        this.detail = detail;
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
        if (type == TEMPLATE_TYPE_SCORE_TOTAL) {
            return JSON.parseObject(detail, new TypeReference<Map<String, CourseTotalSetting>>() {});
        }
        return null;
    }

    public void setCourseTotalSettingMap(Map<String, CourseTotalSetting> courseTotalSettingMap) {
        this.courseTotalSettingMap = courseTotalSettingMap;
    }

    public Map<String, String> getCourseGapSettingMap() {
        if(type == TEMPLATE_TYPE_SCORE_GAP){
            return JSON.parseObject(detail, new TypeReference<Map<String, String>>(){});
        }
        return null;
    }

    public void setCourseGapSettingMap(Map<String, String> courseGapSettingMap) {
        this.courseGapSettingMap = courseGapSettingMap;
    }
}
