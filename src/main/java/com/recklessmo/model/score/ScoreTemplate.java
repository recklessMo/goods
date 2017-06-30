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
    public static int TEMPLATE_TYPE_SCORE_RANK = 3;


    public static int STATUS_NORMAL = 0;
    public static int STATUS_DEFAULT = 1;

    /**
     * 数据库字段
     */
    private long id;
    private long orgId;
    private String name = "default";
    //代表是哪种分析的模板
    private int type = 0;
    private int status = 0;
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
    //名次分析的模板, type为3
    private Map<String, String> courseRankSettingMap;


    public String getDetail() {
        if (type == TEMPLATE_TYPE_SCORE_TOTAL) {
            return JSON.toJSONString(courseTotalSettingMap);
        }else if(type == TEMPLATE_TYPE_SCORE_GAP){
            return JSON.toJSONString(courseGapSettingMap);
        }else if(type == TEMPLATE_TYPE_SCORE_RANK){
            return JSON.toJSONString(courseRankSettingMap);
        }
        return "";
    }

    public void setDetail(String detail) {
        this.detail = detail;
        if (type == TEMPLATE_TYPE_SCORE_TOTAL) {
            courseTotalSettingMap = JSON.parseObject(detail, new TypeReference<Map<String, CourseTotalSetting>>(){});
        }else if(type == TEMPLATE_TYPE_SCORE_GAP){
            courseGapSettingMap = JSON.parseObject(detail, new TypeReference<Map<String, String>>(){});
        }else if(type == TEMPLATE_TYPE_SCORE_RANK){
            courseRankSettingMap = JSON.parseObject(detail, new TypeReference<Map<String, String>>(){});
        }
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

    public Map<String, String> getCourseGapSettingMap() {
        return courseGapSettingMap;
    }

    public void setCourseGapSettingMap(Map<String, String> courseGapSettingMap) {
        this.courseGapSettingMap = courseGapSettingMap;
    }

    public Map<String, CourseTotalSetting> getCourseTotalSettingMap() {
        return courseTotalSettingMap;
    }

    public void setCourseTotalSettingMap(Map<String, CourseTotalSetting> courseTotalSettingMap) {
        this.courseTotalSettingMap = courseTotalSettingMap;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public Map<String, String> getCourseRankSettingMap() {
        return courseRankSettingMap;
    }

    public void setCourseRankSettingMap(Map<String, String> courseRankSettingMap) {
        this.courseRankSettingMap = courseRankSettingMap;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
