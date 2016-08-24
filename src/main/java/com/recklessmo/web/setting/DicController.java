package com.recklessmo.web.setting;

import com.recklessmo.model.setting.Grade;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.setting.GradeSettingService;
import org.springframework.stereotype.Controller;
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
    public JsonResponse addGrade(){
        List<Grade> grades = gradeSettingService.listAllGrade();
        return new JsonResponse(200, grades, null);
    }


}
