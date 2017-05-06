package com.recklessmo.web.teacher;

import com.recklessmo.model.course.AllClassCourseTeacherInfo;
import com.recklessmo.model.course.SingleClassCourseTeacherInfo;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.model.setting.Course;
import com.recklessmo.model.course.CourseTeacher;
import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.setting.Group;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.setting.CourseSettingService;
import com.recklessmo.service.setting.GradeSettingService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.web.webmodel.page.Page;
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
public class ClassCourseTeacherController {

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
    public JsonResponse addClassTeacherInfo(@RequestBody SingleClassCourseTeacherInfo[] singleClassCourseTeacherInfos){
        List<SingleClassCourseTeacherInfo> singleClassCourseTeacherInfoList = Arrays.asList(singleClassCourseTeacherInfos);
        singleClassCourseTeacherInfoList.forEach(singleClass -> {
            long groupId = singleClass.getGroupId();
            Group group = gradeSettingService.getSingleGroup(groupId);
            group.getCourseTeacherMap().clear();
            singleClass.getCourseTeacherList().forEach(courseClass -> {
                group.getCourseTeacherMap().put(courseClass.getCourseName(), courseClass);
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
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        Page page = new Page();
        page.setPage(1);
        page.setCount(10000);
        List<Grade> gradeList = gradeSettingService.listAllGrade(userDetails.getOrgId());
        List<Course> courseList = courseSettingService.listCourse(page);
        AllClassCourseTeacherInfo allClassCourseTeacherInfo = new AllClassCourseTeacherInfo();
        allClassCourseTeacherInfo.setCourseList(courseList);
        List<SingleClassCourseTeacherInfo> singleClassCourseTeacherInfoList = new LinkedList<>();
        gradeList.forEach(grade->{
            List<Group> groupList = grade.getClassList();
            groupList.forEach(group -> {
                SingleClassCourseTeacherInfo singleClassCourseTeacherInfo = new SingleClassCourseTeacherInfo();
                singleClassCourseTeacherInfo.setGroupId(group.getClassId());
                singleClassCourseTeacherInfo.setGroupName(group.getClassName());
                singleClassCourseTeacherInfo.setGradeId(grade.getGradeId());
                singleClassCourseTeacherInfo.setGradeName(grade.getGradeName());
                List<CourseTeacher> courseTeacherList = new LinkedList<>();
                for(Course course : courseList){
                    CourseTeacher courseTeacher = new CourseTeacher();
                    courseTeacher.setCourseId(course.getCourseId());
                    courseTeacher.setCourseName(course.getCourseName());
                    CourseTeacher value = group.getCourseTeacherMap().get(course.getCourseName());
                    courseTeacherList.add(value == null ? courseTeacher : value);
                }
                singleClassCourseTeacherInfo.setCourseTeacherList(courseTeacherList);
                singleClassCourseTeacherInfoList.add(singleClassCourseTeacherInfo);
            });
        });
        allClassCourseTeacherInfo.setSingleClassCourseTeacherInfoList(singleClassCourseTeacherInfoList);
        return new JsonResponse(200, allClassCourseTeacherInfo, null);
    }



}
