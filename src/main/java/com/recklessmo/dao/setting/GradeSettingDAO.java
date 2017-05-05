package com.recklessmo.dao.setting;

import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.setting.Group;
import com.recklessmo.web.webmodel.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hpf on 8/17/16.
 */
public interface GradeSettingDAO {

    void addGrade(Grade grade);
    void addClass(Group group);

    void deleteGrade(@Param("id")long id);
    void deleteClass(@Param("id")long id);

    void updateGrade(Grade grade);
    void updateClass(Group group);

    List<Grade> listGrade(Page page);
    int listGradeCount(Page page);
    List<Group> listClass(Page page);
    int listClassCount(Page page);

    List<Grade> listAllGrade(@Param("orgId")long orgId);

    Group getSingleGroup(@Param("classId")long id);
    Grade getSingleGrade(@Param("gradeId")long id);

}
