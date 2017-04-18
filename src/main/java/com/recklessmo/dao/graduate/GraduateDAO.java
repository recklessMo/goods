package com.recklessmo.dao.graduate;

import com.recklessmo.model.exam.Exam;
import com.recklessmo.model.graduate.Graduate;
import com.recklessmo.web.webmodel.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
public interface GraduateDAO {

    List<Graduate> listGraduate(Page page);
    void addGraduate(Graduate graduate);
    void updateGraduate(Graduate graduate);
    void deleteGraduate(@Param("id")long id);

}
