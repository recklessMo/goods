package com.recklessmo.service.setting;

import com.recklessmo.dao.setting.EduSettingDAO;
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
public class EduSettingService {

    @Resource
    private EduSettingDAO eduSettingDAO;

    public void addGrade(Grade grade){
        eduSettingDAO.addGrade(grade);
    }

    public void addClass(Group group){
        eduSettingDAO.addClass(group);
    }

    public void deleteGrade(long id){
        eduSettingDAO.deleteGrade(id);
    }

    public void deleteClass(long id){
        eduSettingDAO.deleteClass(id);
    }

    public void updateGrade(Grade grade){
        eduSettingDAO.updateGrade(grade);
    }

    public void updateClass(Group group){
        eduSettingDAO.updateClass(group);
    }

    public List<Grade> listGrade(Page page){
        return eduSettingDAO.listGrade(page);
    }

    public int listGradeCount(Page page){
        return eduSettingDAO.listGradeCount(page);
    }

    public List<Group> listClass(Page page){
        return eduSettingDAO.listClass(page);
    }

    public int listClassCount(Page page){
        return eduSettingDAO.listClassCount(page);
    }

}
