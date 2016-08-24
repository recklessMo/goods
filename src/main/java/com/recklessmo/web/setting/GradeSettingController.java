package com.recklessmo.web.setting;

import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.setting.Group;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.setting.GradeSettingService;
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
@RequestMapping("/v1/setting")
public class GradeSettingController {

    @Resource
    private GradeSettingService gradeSettingService;

    @RequestMapping(value = "/grade/add", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse addGrade(@RequestBody Grade grade){
        //TODO do some check here
        gradeSettingService.addGrade(grade);
        return new JsonResponse(200, null, null);
    }

    @RequestMapping(value = "/class/add", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse addClass(@RequestBody Group group){
        gradeSettingService.addClass(group);
        return new JsonResponse(200, null, null);
    }

    @RequestMapping(value = "/grade/delete", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse deleteGrade(@RequestBody long id){
        gradeSettingService.deleteGrade(id);
        return new JsonResponse(200, null, null);
    }

    @RequestMapping(value = "/class/delete", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse deleteClass(@RequestBody long id){
        gradeSettingService.deleteClass(id);
        return new JsonResponse(200, null, null);
    }

    @RequestMapping(value = "/grade/update", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse updateGrade(@RequestBody Grade grade){
        gradeSettingService.updateGrade(grade);
        return new JsonResponse(200, null, null);
    }

    @RequestMapping(value = "/class/update", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse updateClass(@RequestBody Group group){
        gradeSettingService.updateClass(group);
        return new JsonResponse(200, null, null);
    }

    @RequestMapping(value = "/grade/list", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse listGrade(@RequestBody Page page){
        int cnt = gradeSettingService.listGradeCount(page);
        List<Grade> data = gradeSettingService.listGrade(page);
        return new JsonResponse(200, data, cnt);
    }

    @RequestMapping(value = "/class/list", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse listClass(@RequestBody GradePage page){
        int cnt = gradeSettingService.listClassCount(page);
        List<Group> data = gradeSettingService.listClass(page);
        return new JsonResponse(200, data, cnt);
    }



}
