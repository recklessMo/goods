package com.recklessmo.dao.setting;

import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.setting.Group;
import com.recklessmo.model.setting.Term;
import com.recklessmo.model.setting.Year;
import com.recklessmo.web.webmodel.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hpf on 8/17/16.
 */
public interface YearSettingDAO {

    void addYear(Year year);
    void addTerm(Term term);

    void updateYear(Year year);
    void updateTerm(Term term);

    List<Year> listYear(Page page);
    int listYearCount(Page page);
    List<Term> listTerm(Page page);
    int listTermCount(Page page);

}
