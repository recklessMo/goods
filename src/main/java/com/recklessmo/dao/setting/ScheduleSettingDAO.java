package com.recklessmo.dao.setting;

import com.recklessmo.model.setting.Job;
import com.recklessmo.model.setting.Schedule;
import com.recklessmo.web.webmodel.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 2017/01/25
 */
public interface ScheduleSettingDAO {
    void addSchedule(Schedule schedule);
    List<Schedule> listSchedule(Page page);
    void deleteSchedule(@Param("id") long id);
}
