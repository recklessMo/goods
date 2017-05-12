package com.recklessmo.service.setting;

import com.recklessmo.dao.setting.JobSettingDAO;
import com.recklessmo.dao.setting.YearSettingDAO;
import com.recklessmo.model.setting.Job;
import com.recklessmo.model.setting.Term;
import com.recklessmo.model.setting.Year;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hpf on 8/17/16.
 */
@Service
public class JobSettingService {

    @Resource
    private JobSettingDAO jobSettingDAO;

    public void insert(Job job){
        jobSettingDAO.addJob(job);
    }

    public int listJobCount(Page page){
        return jobSettingDAO.listJobCount(page);
    }

    public List<Job> listJob(Page page){
        return jobSettingDAO.listJob(page);
    }

    public void deleteJob(long orgId, long id) {jobSettingDAO.deleteJob(orgId, id);}
}
