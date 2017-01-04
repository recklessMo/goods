package com.recklessmo.web.setting;

import com.recklessmo.model.setting.Job;
import com.recklessmo.model.setting.Term;
import com.recklessmo.model.setting.Year;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.setting.JobSettingService;
import com.recklessmo.service.setting.YearSettingService;
import com.recklessmo.web.webmodel.page.Page;
import com.recklessmo.web.webmodel.page.YearPage;
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
public class JobSettingController {

    @Resource
    private JobSettingService jobSettingService;

    @RequestMapping(value = "/job/add", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse addJob(@RequestBody Job job){
        //TODO do some check here
        jobSettingService.insert(job);
        return new JsonResponse(200, null, null);
    }

    @RequestMapping(value = "/job/list", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse listJob(@RequestBody Page page){
        //TODO do some check here
        int count = jobSettingService.listJobCount(page);
        List<Job> jobList = jobSettingService.listJob(page);
        return new JsonResponse(200, jobList, count);
    }

    @RequestMapping(value = "/job/delete", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse deleteJob(@RequestBody long id){
        //TODO do some check here
        jobSettingService.deleteJob(id);
        return new JsonResponse(200, null, null);
    }

}
