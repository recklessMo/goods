package com.recklessmo.web.setting;

import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.setting.Group;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.setting.GradeSettingService;
import com.recklessmo.web.webmodel.page.GradePage;
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



    @RequestMapping(value = "/grade/list", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public JsonResponse listGrade(){
        List<Grade> grades = gradeSettingService.listAllGrade();
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


}
