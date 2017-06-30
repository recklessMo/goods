package com.recklessmo.web.setting;

import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.model.setting.ClassLevel;
import com.recklessmo.model.setting.Job;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.setting.ClassLevelSettingService;
import com.recklessmo.service.setting.JobSettingService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hpf on 8/17/16.
 */
@Controller
@RequestMapping("/v1/setting")
public class ClassLevelSettingController {

    @Resource
    private ClassLevelSettingService classLevelSettingService;

    @RequestMapping(value = "/classlevel/add", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse addClassLevel(@RequestBody ClassLevel classLevel){
        DefaultUserDetails defaultUserDetails = ContextUtils.getLoginUserDetail();
        classLevel.setOrgId(defaultUserDetails.getOrgId());
        classLevelSettingService.insert(classLevel);
        return new JsonResponse(200, null, null);
    }

    @RequestMapping(value = "/classlevel/list", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse listClassLevel(@RequestBody Page page){
        page.setOrgId(ContextUtils.getLoginUserDetail().getOrgId());
        List<ClassLevel> classLevelList = classLevelSettingService.listClassLevel(page);
        int count = classLevelList.size();
        return new JsonResponse(200, classLevelList, count);
    }

    @RequestMapping(value = "/classlevel/delete", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse deleteClassLevel(@RequestBody long id){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        classLevelSettingService.deleteClassLevel(userDetails.getOrgId(), id);
        return new JsonResponse(200, null, null);
    }

}
