package com.recklessmo.service.setting;

import com.recklessmo.dao.setting.ClassLevelSettingDAO;
import com.recklessmo.model.setting.ClassLevel;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hpf on 8/17/16.
 */
@Service
public class ClassLevelSettingService {

    @Resource
    private ClassLevelSettingDAO classLevelSettingDAO;

    public void insert(ClassLevel classLevel){
        classLevelSettingDAO.addClassLevel(classLevel);
    }


    public List<ClassLevel> listClassLevel(Page page){
        return classLevelSettingDAO.listClassLevel(page);
    }

    public void deleteClassLevel(long orgId, long id) {classLevelSettingDAO.deleteClassLevel(orgId, id);}
}
