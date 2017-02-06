package com.recklessmo.web.teacher;

import com.recklessmo.model.course.SingleClassCourseTeacherInfo;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.setting.CourseSettingService;
import com.recklessmo.service.setting.GradeSettingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 课表设置
 */
@Controller
public class ClassScheduleController {

    @Resource
    private GradeSettingService gradeSettingService;

    @Resource
    private CourseSettingService courseSettingService;

    /**
     * 设置全校课表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/v1/class/schedule/save", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse addClassTeacherInfo(@RequestBody SingleClassCourseTeacherInfo[] singleClassCourseTeacherInfos){

        return new JsonResponse(200, null, null);
    }


    /**
     * 获取全校课表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/v1/class/schedule/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse listClassTeacherInfo(){

        return new JsonResponse(200, null, null);
    }



}
