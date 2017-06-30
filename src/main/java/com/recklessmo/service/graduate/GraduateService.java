package com.recklessmo.service.graduate;

import com.recklessmo.dao.exam.ExamDAO;
import com.recklessmo.dao.graduate.GraduateDAO;
import com.recklessmo.model.exam.Exam;
import com.recklessmo.model.graduate.Graduate;
import com.recklessmo.web.webmodel.page.ExamListPage;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
@Service
public class GraduateService {

    @Resource
    private GraduateDAO graduateDAO;

    public List<Graduate> getGraduateList(Page page){
        return graduateDAO.listGraduate(page);
    }

    public void addGraduate(Graduate graduate){
        graduateDAO.addGraduate(graduate);
    }

    public void updateGraduate(Graduate graduate){
        graduateDAO.updateGraduate(graduate);
    }

    public void deleteGraduate(long id){
        graduateDAO.deleteGraduate(id);
    }

}
