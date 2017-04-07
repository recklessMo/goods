package com.recklessmo.model.score;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.recklessmo.model.setting.Course;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
public class Score {

    //联合主键
    private String sid;
    private long examId;
    //json存储每次考试对应的成绩详情
    private String detail;
    //对应的内部数据结构
    private List<CourseScore> courseScoreList = new LinkedList<>();

    public String getDetail() {
        return JSON.toJSONString(courseScoreList);
    }

    public void setDetail(String detail) {
        this.detail = detail;
        if(StringUtils.isNotEmpty(detail)) {
            courseScoreList = JSON.parseObject(detail, new TypeReference<List<CourseScore>>(){});
        }
    }

    public List<CourseScore> getCourseScoreList() {
        return courseScoreList;
    }

    public void setCourseScoreList(List<CourseScore> courseScoreList) {
        this.courseScoreList = courseScoreList;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

}
