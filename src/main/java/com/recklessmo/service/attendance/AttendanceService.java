package com.recklessmo.service.attendance;

import com.recklessmo.dao.attendance.AttendanceDAO;
import com.recklessmo.dao.exam.ExamDAO;
import com.recklessmo.model.attendance.Attendance;
import com.recklessmo.model.exam.Exam;
import com.recklessmo.model.setting.Course;
import com.recklessmo.model.setting.Grade;
import com.recklessmo.service.setting.CourseSettingService;
import com.recklessmo.service.setting.GradeSettingService;
import com.recklessmo.web.webmodel.page.ExamListPage;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hpf on 8/29/16.
 */
@Service
public class AttendanceService {

    @Resource
    private AttendanceDAO attendanceDAO;

    public List<Attendance> listAttendance(Page page){
        return attendanceDAO.listAttendance(page);
    }

    public int listAttendanceCount(Page page){
        return attendanceDAO.listAttendanceCount(page);
    }

    public void addAttendance(Attendance attendance){
        attendanceDAO.addAttendance(attendance);
    }

    public void deleteAttendance(long orgId, long id){
        attendanceDAO.deleteAttendance(orgId, id);
    }



}
