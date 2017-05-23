package com.recklessmo.web.attendance;

import com.recklessmo.model.assignment.Assignment;
import com.recklessmo.model.assignment.AssignmentStatus;
import com.recklessmo.model.attendance.Attendance;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.assignment.AssignmentService;
import com.recklessmo.service.assignment.AssignmentStatusService;
import com.recklessmo.service.attendance.AttendanceService;
import com.recklessmo.service.student.StudentService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.web.webmodel.page.AssignmentListPage;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *  考勤
 */
@RequestMapping("/v1/attendance")
@Controller
public class AttendanceController {

    @Resource
    private AttendanceService attendanceService;

    @Resource
    private StudentService studentService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JsonResponse listAttendance(@RequestBody Page page){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        page.setOrgId(userDetails.getOrgId());
        List<Attendance> attendanceList = attendanceService.listAttendance(page);
        int count = attendanceService.listAttendanceCount(page);
        return new JsonResponse(200, attendanceList, count);
    }


    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JsonResponse addAttendance(@RequestBody Attendance attendance){
        DefaultUserDetails defaultUserDetails = ContextUtils.getLoginUserDetail();
        attendance.setOrgId(defaultUserDetails.getOrgId());
        attendance.setCreated(new Date());
        attendance.setOpId(defaultUserDetails.getId());
        attendance.setOpName(defaultUserDetails.getName());
        attendanceService.addAttendance(attendance);
        return new JsonResponse(200, null, null);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public JsonResponse deleteAttendance(@RequestBody long id){
        DefaultUserDetails defaultUserDetails = ContextUtils.getLoginUserDetail();
        attendanceService.deleteAttendance(defaultUserDetails.getOrgId(), id);
        return new JsonResponse(200, null, null);
    }

}
