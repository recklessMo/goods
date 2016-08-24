package com.recklessmo.web.setting;

import com.recklessmo.model.setting.Term;
import com.recklessmo.model.setting.Course;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.setting.CourseSettingService;
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
@RequestMapping("/v1/setting")
public class CourseSettingController {

    @Resource
    private CourseSettingService courseSettingService;

    @RequestMapping(value = "/course/add", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse addCourse(@RequestBody Course Course){
        //TODO do some check here
        courseSettingService.addCourse(Course);
        return new JsonResponse(200, null, null);
    }

    @RequestMapping(value = "/course/update", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse updateCourse(@RequestBody Course Course){
        //TODO do some check here
        courseSettingService.updateCourse(Course);
        return new JsonResponse(200, null, null);
    }

    @RequestMapping(value = "/course/list", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse listCourse(@RequestBody Page page){
        //TODO do some check here
        int count = courseSettingService.listCourseCount(page);
        List<Course> CourseList = courseSettingService.listCourse(page);
        return new JsonResponse(200, CourseList, count);
    }


}
