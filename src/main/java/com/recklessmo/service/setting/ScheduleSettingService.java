package com.recklessmo.service.setting;

import com.recklessmo.dao.setting.JobSettingDAO;
import com.recklessmo.dao.setting.ScheduleSettingDAO;
import com.recklessmo.model.setting.Job;
import com.recklessmo.model.setting.Schedule;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hpf on 1/24/17.
 */
@Service
public class ScheduleSettingService {

    @Resource
    private ScheduleSettingDAO scheduleSettingDAO;

    public void insert(Schedule schedule){
        scheduleSettingDAO.addSchedule(schedule);
    }

    public void save(Schedule schedule){
        scheduleSettingDAO.saveSchedule(schedule);
    }

    public List<Schedule> listSchedule(){
        return scheduleSettingDAO.listSchedule();
    }

    public void deleteSchedule(long id) {scheduleSettingDAO.deleteSchedule(id);}

}
