package com.recklessmo.dao.setting;

import com.recklessmo.model.setting.ClassLevel;
import com.recklessmo.model.setting.Job;
import com.recklessmo.web.webmodel.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hpf on 8/17/16.
 */
public interface ClassLevelSettingDAO {
    void addClassLevel(ClassLevel classLevel);
    List<ClassLevel> listClassLevel(Page page);
    void deleteClassLevel(@Param("orgId")long orgId, @Param("id") long id);
}
