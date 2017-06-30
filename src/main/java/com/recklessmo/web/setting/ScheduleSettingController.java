package com.recklessmo.web.setting;

import com.recklessmo.model.setting.Job;
import com.recklessmo.model.setting.Schedule;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.setting.JobSettingService;
import com.recklessmo.service.setting.ScheduleSettingService;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 上课时间设置
 */
@Controller
@RequestMapping("/v1/setting")
public class ScheduleSettingController {

    @Resource
    private ScheduleSettingService scheduleSettingService;

    @RequestMapping(value = "/schedule/save", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse addSchedule(@RequestBody Schedule schedule){
        //TODO do some check here
        if(schedule.getId() == 0) {
            scheduleSettingService.insert(schedule);
        }else{
            scheduleSettingService.save(schedule);
        }
        return new JsonResponse(200, null, null);
    }

    @RequestMapping(value = "/schedule/list", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse listSchedule(){
        //TODO do some check here
        List<Schedule> schedules = scheduleSettingService.listSchedule();
        return new JsonResponse(200, schedules, null);
    }

    @RequestMapping(value = "/schedule/delete", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResponse deleteSchedule(@RequestBody long id){
        scheduleSettingService.deleteSchedule(id);
        return new JsonResponse(200, null, null);
    }

}
