package com.recklessmo.model.course;

import com.recklessmo.model.setting.Course;
import com.recklessmo.model.setting.Schedule;

import java.util.List;

/**
 * Created by hpf on 1/20/17.
 */
public class AllClassScheduleCourseInfo {

    private List<Schedule> scheduleList;
    private List<SingleClassScheduleCourseInfo> singleClassScheduleCourseInfoList;

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public List<SingleClassScheduleCourseInfo> getSingleClassScheduleCourseInfoList() {
        return singleClassScheduleCourseInfoList;
    }

    public void setSingleClassScheduleCourseInfoList(List<SingleClassScheduleCourseInfo> singleClassScheduleCourseInfoList) {
        this.singleClassScheduleCourseInfoList = singleClassScheduleCourseInfoList;
    }
}
