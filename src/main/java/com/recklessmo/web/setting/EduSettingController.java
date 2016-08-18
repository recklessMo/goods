package com.recklessmo.web.setting;

import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.setting.Group;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.setting.EduSettingService;
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
public class EduSettingController {

    @Resource
    private EduSettingService eduSettingService;

    @RequestMapping(value = "/grade/add", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse addGrade(@RequestBody Grade grade){
        //TODO do some check here
        eduSettingService.addGrade(grade);
        return new JsonResponse(200, null, null);
    }

    @RequestMapping(value = "/class/add", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse addClass(@RequestBody Group group){
        eduSettingService.addClass(group);
        return new JsonResponse(200, null, null);
    }

    @RequestMapping(value = "/grade/delete", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse deleteGrade(@RequestBody long id){
        eduSettingService.deleteGrade(id);
        return new JsonResponse(200, null, null);
    }

    @RequestMapping(value = "/class/delete", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse deleteClass(@RequestBody long id){
        eduSettingService.deleteClass(id);
        return new JsonResponse(200, null, null);
    }

    @RequestMapping(value = "/grade/update", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse updateGrade(@RequestBody Grade grade){
        eduSettingService.updateGrade(grade);
        return new JsonResponse(200, null, null);
    }

    @RequestMapping(value = "/class/update", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse updateClass(@RequestBody Group group){
        eduSettingService.updateClass(group);
        return new JsonResponse(200, null, null);
    }

    @RequestMapping(value = "/grade/list", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse listGrade(@RequestBody Page page){
        int cnt = eduSettingService.listGradeCount(page);
        List<Grade> data = eduSettingService.listGrade(page);
        return new JsonResponse(200, data, cnt);
    }

    @RequestMapping(value = "/class/list", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse listClass(@RequestBody Page page){
        int cnt = eduSettingService.listClassCount(page);
        List<Group> data = eduSettingService.listClass(page);
        return new JsonResponse(200, data, cnt);
    }



}
