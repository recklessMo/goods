package com.recklessmo.service.setting;

import com.recklessmo.dao.setting.GradeSettingDAO;
import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.setting.Group;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hpf on 8/17/16.
 */
@Service
public class GradeSettingService {

    @Resource
    private GradeSettingDAO gradeSettingDAO;

    public void addGrade(Grade grade){
        gradeSettingDAO.addGrade(grade);
    }

    public void addClass(Group group){
        gradeSettingDAO.addClass(group);
    }

    public void deleteGrade(long id){
        gradeSettingDAO.deleteGrade(id);
    }

    public void deleteClass(long id){
        gradeSettingDAO.deleteClass(id);
    }

    public void updateGrade(Grade grade){
        gradeSettingDAO.updateGrade(grade);
    }

    public void updateClass(Group group){
        gradeSettingDAO.updateClass(group);
    }

    public List<Grade> listGrade(Page page){
        return gradeSettingDAO.listGrade(page);
    }

    public int listGradeCount(Page page){
        return gradeSettingDAO.listGradeCount(page);
    }

    public List<Group> listClass(Page page){
        return gradeSettingDAO.listClass(page);
    }

    public int listClassCount(Page page){
        return gradeSettingDAO.listClassCount(page);
    }


    public List<Grade> listAllGrade(long orgId){
        return gradeSettingDAO.listAllGrade(orgId);
    }

    public Grade getSingleGrade(long gradeId){
        return gradeSettingDAO.getSingleGrade(gradeId);
    }

    public Group getSingleGroup(long classId){
        return gradeSettingDAO.getSingleGroup(classId);
    }
}
