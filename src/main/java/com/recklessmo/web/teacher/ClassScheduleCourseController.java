package com.recklessmo.web.teacher;

import com.recklessmo.model.course.*;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.model.setting.Course;
import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.setting.Group;
import com.recklessmo.model.setting.Schedule;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.setting.CourseSettingService;
import com.recklessmo.service.setting.GradeSettingService;
import com.recklessmo.service.setting.ScheduleSettingService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 课表设置
 */
@Controller
public class ClassScheduleCourseController {

    @Resource
    private GradeSettingService gradeSettingService;

    @Resource
    private CourseSettingService courseSettingService;

    @Resource
    private ScheduleSettingService scheduleSettingService;

    /**
     * 设置全校课表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/v1/class/schedule/save", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse addClassTeacherInfo(@RequestBody SingleClassScheduleCourseInfo[] singleClassScheduleCourseInfos){
        List<SingleClassScheduleCourseInfo> singleClassScheduleCourseInfoList = Arrays.asList(singleClassScheduleCourseInfos);
        singleClassScheduleCourseInfoList.forEach(singleClass -> {
            long groupId = singleClass.getGroupId();
            Group group = gradeSettingService.getSingleGroup(groupId);
            group.getScheduleCourseMap().clear();
            singleClass.getScheduleCourseList().forEach(scheduleCourse -> group.getScheduleCourseMap().put(scheduleCourse.getScheduleName(), scheduleCourse));
            gradeSettingService.updateClass(group);
        });

        return new JsonResponse(200, null, null);
    }


    /**
     * 获取全校课表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/v1/class/schedule/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse listClassTeacherInfo(){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        Page page = new Page();
        page.setPage(1);
        page.setCount(10000);
        List<Grade> gradeList = gradeSettingService.listAllGrade(userDetails.getOrgId());
        List<Schedule> scheduleList = scheduleSettingService.listSchedule();
        AllClassScheduleCourseInfo allClassScheduleCourseInfo = new AllClassScheduleCourseInfo();
        allClassScheduleCourseInfo.setScheduleList(scheduleList);
        List<SingleClassScheduleCourseInfo> singleClassScheduleCourseInfoList = new LinkedList<>();
        gradeList.forEach(grade->{
            List<Group> groupList = grade.getClassList();
            groupList.forEach(group -> {
                SingleClassScheduleCourseInfo singleClassScheduleCourseInfo = new SingleClassScheduleCourseInfo();
                singleClassScheduleCourseInfo.setGroupId(group.getClassId());
                singleClassScheduleCourseInfo.setGroupName(group.getClassName());
                singleClassScheduleCourseInfo.setGradeId(grade.getGradeId());
                singleClassScheduleCourseInfo.setGradeName(grade.getGradeName());
                List<ScheduleCourse> scheduleCourseList = new LinkedList<>();
                for(Schedule schedule : scheduleList){
                    ScheduleCourse scheduleCourse = new ScheduleCourse();
                    scheduleCourse.setScheduleId(schedule.getId());
                    scheduleCourse.setScheduleName(schedule.getScheduleName());
                    ScheduleCourse value = group.getScheduleCourseMap().get(schedule.getScheduleName());
                    scheduleCourseList.add(value == null ? scheduleCourse : value);
                }
                singleClassScheduleCourseInfo.setScheduleCourseList(scheduleCourseList);
                singleClassScheduleCourseInfoList.add(singleClassScheduleCourseInfo);
            });
        });
        allClassScheduleCourseInfo.setSingleClassScheduleCourseInfoList(singleClassScheduleCourseInfoList);
        return new JsonResponse(200, allClassScheduleCourseInfo, null);
    }



}
