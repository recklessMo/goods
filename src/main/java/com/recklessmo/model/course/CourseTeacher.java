package com.recklessmo.model.course;

import java.util.Date;

/**
 * Created by hpf on 8/23/16.
 */
public class CourseTeacher {

    //常用的学科名字
    public static String CHINESE = "语文";
    public static String MATH = "数学";
    public static String ENGLISH = "英语";
    public static String POLITICS = "政治";
    public static String HISTORY = "历史";
    public static String GEO = "地理";
    public static String PHYSICS = "物理";
    public static String CHEMISTRY = "化学";
    public static String BIOLOGY = "生物";


    private long courseId;
    private String courseName;
    private long userId;
    private String userName;

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
