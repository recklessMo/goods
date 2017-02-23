package com.recklessmo.dao.setting;

import com.recklessmo.model.setting.Term;
import com.recklessmo.model.setting.Course;
import com.recklessmo.web.webmodel.page.Page;
import com.sun.tracing.dtrace.ProviderAttributes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hpf on 8/17/16.
 */
public interface CourseSettingDAO {

    void addCourse(Course Course);

    void updateCourse(Course Course);

    List<Course> listCourse(Page page);
    int listCourseCount(Page page);

    void deleteCourse(@Param("id")long id);

    Course getCourseById(@Param("id")long id);

}
