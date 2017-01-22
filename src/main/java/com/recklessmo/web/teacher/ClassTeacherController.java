package com.recklessmo.web.teacher;

import com.recklessmo.model.course.AllClass;
import com.recklessmo.model.course.SingleClass;
import com.recklessmo.model.setting.Course;
import com.recklessmo.model.setting.CourseClass;
import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.setting.Group;
import com.recklessmo.model.student.StudentAddInfo;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.setting.CourseSettingService;
import com.recklessmo.service.setting.GradeSettingService;
import com.recklessmo.web.webmodel.page.Page;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 1/20/17.
 */
@Controller
public class ClassTeacherController {

    @Resource
    private GradeSettingService gradeSettingService;

    @Resource
    private CourseSettingService courseSettingService;

    /**
     *
     * 设置任课教师
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/v1/class/teacher/save", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse addClassTeacherInfo(@RequestBody SingleClass[] singleClasses){
        List<SingleClass> singleClassList = Arrays.asList(singleClasses);
        singleClassList.forEach(singleClass -> {
            long groupId = singleClass.getGroupId();
            Group group = gradeSettingService.getSingleGroup(groupId);
            group.getCourseClassMap().clear();
            singleClass.getCourseClassList().forEach(courseClass -> {
                group.getCourseClassMap().put(courseClass.getCourseName(), courseClass);
            });
            gradeSettingService.updateClass(group);
        });

        return new JsonResponse(200, null, null);
    }


    /**
     *
     * 获取全校任课教师
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/v1/class/teacher/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse listClassTeacherInfo(){
        Page page = new Page();
        page.setPage(1);
        page.setCount(10000);
        List<Grade> gradeList = gradeSettingService.listAllGrade();
        List<Course> courseList = courseSettingService.listCourse(page);
        AllClass allClass = new AllClass();
        allClass.setCourseList(courseList);
        List<SingleClass> singleClassList = new LinkedList<>();
        gradeList.forEach(grade->{
            List<Group> groupList = grade.getClassList();
            groupList.forEach(group -> {
                SingleClass singleClass = new SingleClass();
                singleClass.setGroupId(group.getClassId());
                singleClass.setGroupName(group.getClassName());
                singleClass.setGradeId(grade.getGradeId());
                singleClass.setGradeName(grade.getGradeName());
                List<CourseClass> courseClassList = new LinkedList<>();
                for(Course course : courseList){
                    CourseClass courseClass = new CourseClass();
                    courseClass.setCourseId(course.getCourseId());
                    courseClass.setCourseName(course.getCourseName());
                    CourseClass value = group.getCourseClassMap().get(course.getCourseName());
                    courseClassList.add(value == null ? courseClass : value);
                }
                singleClass.setCourseClassList(courseClassList);
                singleClassList.add(singleClass);
            });
        });
        allClass.setSingleClassList(singleClassList);
        return new JsonResponse(200, allClass, null);
    }



}
