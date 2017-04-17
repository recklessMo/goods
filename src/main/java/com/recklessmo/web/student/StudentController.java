package com.recklessmo.web.student;

import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.model.student.StudentAddInfo;
import com.recklessmo.model.student.StudentAllInfo;
import com.recklessmo.model.student.StudentBaseInfo;
import com.recklessmo.model.student.StudentGradeInfo;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.student.StudentService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.web.webmodel.page.StudentPage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.RequestToViewNameTranslator;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hpf on 7/23/16.
 *
 * 主要用于保存学生的信息
 */
@Controller
public class StudentController {

    @Resource
    private StudentService studentService;

    @ResponseBody
    @RequestMapping(value = "/v1/student/add", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse addStudentInfo(@RequestBody StudentAddInfo studentAddInfo){
        //TODO 可能需要做一些字段的校验,可以考虑写个annotation来进行校验
        studentService.insertStudentAddInfo(studentAddInfo);
        return new JsonResponse(200, null, null);
    }

    @ResponseBody
    @RequestMapping(value = "/v1/student/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse list(@RequestBody StudentPage page){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        page.setOrgId(userDetails.getOrgId());
        int cnt = studentService.getStudentBaseInfoTotalCount(page);
        List<StudentBaseInfo> infoList = studentService.getStudentBaseInfo(page);
        return new JsonResponse(200, infoList, cnt);
    }

    @ResponseBody
    @RequestMapping(value = "/v1/student/listall", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse listall(@RequestBody StudentPage page){
        int cnt = studentService.getStudentAllInfoTotalCount(page);
        List<StudentAllInfo> infoList = studentService.getStudentAllInfo(page);
        return new JsonResponse(200, infoList, cnt);
    }


    /**
     *
     * 用于通过考试来搜索学生
     *
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/v1/student/searchByExam", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse searchByExam(@RequestBody StudentPage page){
        List<StudentGradeInfo> studentGradeInfoList = studentService.searchStudentByExam(page);
        return new JsonResponse(200, studentGradeInfoList, studentGradeInfoList.size());
    }

    /**
     *
     * 通过sid获取学生全部信息
     * @param sid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/v1/student/get", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse getBySid(@RequestBody String sid){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        StudentAllInfo studentAllInfo = studentService.getStudentInfoBySid(userDetails.getOrgId(), sid);
        return new JsonResponse(200, studentAllInfo, null);
    }


    /**
     *
     * 更新信息
     *
     * @param studentAllInfo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/v1/student/save", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse saveStudentAllInfo(@RequestBody StudentAllInfo studentAllInfo){
        studentService.updateStudentInfo(studentAllInfo);
        return new JsonResponse(200, null, null);
    }


}
