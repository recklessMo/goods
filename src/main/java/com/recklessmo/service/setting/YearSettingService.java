package com.recklessmo.service.setting;

import com.recklessmo.dao.setting.GradeSettingDAO;
import com.recklessmo.dao.setting.YearSettingDAO;
import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.setting.Group;
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
public class YearSettingService {

    @Resource
    private YearSettingDAO yearSettingDAO;

    public void addYear(Year year){
        yearSettingDAO.addYear(year);
    }

    public void addTerm(Term term){
        yearSettingDAO.addTerm(term);
    }

    public void updateYear(Year year){
        yearSettingDAO.updateYear(year);
    }

    public void updateTerm(Term term){
        yearSettingDAO.updateTerm(term);
    }

    public List<Year> listYear(Page page){
        return yearSettingDAO.listYear(page);
    }

    public int listYearCount(Page page){
        return yearSettingDAO.listYearCount(page);
    }

    public List<Term> listTerm(Page page){
        return yearSettingDAO.listTerm(page);
    }

    public int listTermCount(Page page){
        return yearSettingDAO.listTermCount(page);
    }


}
