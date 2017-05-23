package com.recklessmo.service.attendance;

import com.recklessmo.dao.attendance.AttendanceDAO;
import com.recklessmo.dao.exam.ExamDAO;
import com.recklessmo.model.attendance.Attendance;
import com.recklessmo.model.exam.Exam;
import com.recklessmo.model.setting.Course;
import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.student.StudentInfo;
import com.recklessmo.service.setting.CourseSettingService;
import com.recklessmo.service.setting.GradeSettingService;
import com.recklessmo.service.student.StudentService;
import com.recklessmo.web.webmodel.page.ExamListPage;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by hpf on 8/29/16.
 */
@Service
public class AttendanceService {

    @Resource
    private StudentService studentService;

    @Resource
    private AttendanceDAO attendanceDAO;

    public List<Attendance> listAttendance(Page page){
        List<Attendance> attendanceList = attendanceDAO.listAttendance(page);
        compose(page.getOrgId(), attendanceList);
        return attendanceList;
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


    private void compose(long orgId, List<Attendance> attendanceList){
        if(attendanceList.size() == 0){
            return;
        }
        List<String> sidList = attendanceList.stream().map(o -> o.getSid()).collect(Collectors.toList());
        List<StudentInfo> studentInfoList = studentService.getStudentInfoBySidList(orgId, sidList);
        Map<String, StudentInfo> studentInfoMap = studentInfoList.stream().collect(Collectors.toMap(StudentInfo::getSid, Function.identity()));
        attendanceList.stream().forEach(attendance -> {
            StudentInfo studentInfo = studentInfoMap.get(attendance.getSid());
            if(studentInfo != null) {
                attendance.setStudentName(studentInfo.getName());
            }
        });
    }


}
