package com.recklessmo.web.setting;

import com.recklessmo.model.score.inner.CourseGapSetting;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.model.setting.Course;
import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.setting.Group;
import com.recklessmo.model.setting.Job;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.setting.CourseSettingService;
import com.recklessmo.service.setting.GradeSettingService;
import com.recklessmo.service.setting.JobSettingService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.web.webmodel.page.GradePage;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hpf on 8/17/16.
 */
@Controller
@RequestMapping("/v1/dic")
public class DicController {

    @Resource
    private GradeSettingService gradeSettingService;

    @Resource
    private JobSettingService jobSettingService;

    @Resource
    private CourseSettingService courseSettingService;


    @RequestMapping(value = "/grade/list", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public JsonResponse listGrade(){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        List<Grade> grades = gradeSettingService.listAllGrade(userDetails.getOrgId());
        return new JsonResponse(200, grades, null);
    }


    /**
     *
     * 列出所有的班级
     *
     * @param gradePage
     * @return
     */
    @RequestMapping(value = "/class/list", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse listClass(@RequestBody GradePage gradePage){
        List<Group> groups = gradeSettingService.listClass(gradePage);
        return new JsonResponse(200, groups, null);
    }


    /**
     * 列出所有的职业
     * @return
     */
    @RequestMapping(value = "/job/list", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse listJob(@RequestBody Page page){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        page.setOrgId(userDetails.getOrgId());
        List<Job> jobs = jobSettingService.listJob(page);
        return new JsonResponse(200, jobs, null);
    }


    @RequestMapping(value = "/course/list", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse courseList(){
        DefaultUserDetails defaultUserDetails = ContextUtils.getLoginUserDetail();
        Page page = new Page();
        page.setOrgId(defaultUserDetails.getOrgId());
        page.setPage(1);
        page.setCount(100);
        List<Course> courseList = courseSettingService.listCourse(page);
        return new JsonResponse(200, courseList, null);
    }




}
