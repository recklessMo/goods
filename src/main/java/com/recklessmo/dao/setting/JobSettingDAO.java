package com.recklessmo.dao.setting;

import com.recklessmo.model.setting.Job;
import com.recklessmo.model.setting.Term;
import com.recklessmo.model.setting.Year;
import com.recklessmo.web.webmodel.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hpf on 8/17/16.
 */
public interface JobSettingDAO {
    void addJob(Job job);
    List<Job> listJob(Page page);
    int listJobCount(Page page);
    void deleteJob(@Param("orgId")long orgId, @Param("id") long id);
}
