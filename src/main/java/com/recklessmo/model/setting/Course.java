package com.recklessmo.model.setting;

import java.util.Date;

/**
 * Created by hpf on 8/23/16.
 */
public class Course {

    private static int COURSE_TYPE_MAIN = 1;
    private static int COURSE_TYPE_WEN = 2;
    private static int COURSE_TYPE_LI = 3;

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

    private long id;
    private long orgId;
    private long courseId;
    private String courseName;
    private int type;
    //用于表示学校是否导入
    private boolean hasImport = false;
    private int deleted = 0;

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public boolean isHasImport() {
        return hasImport;
    }

    public void setHasImport(boolean hasImport) {
        this.hasImport = hasImport;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

}
