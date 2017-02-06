package com.recklessmo.dao.setting;

import com.recklessmo.model.setting.Job;
import com.recklessmo.model.setting.Schedule;
import com.recklessmo.web.webmodel.page.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.type.ShortTypeHandler;

import java.util.List;


/**
 * 2017/01/25
 */
public interface ScheduleSettingDAO {
    void addSchedule(Schedule schedule);
    void saveSchedule(Schedule schedule);
    List<Schedule> listSchedule();
    void deleteSchedule(@Param("id") long id);
}
