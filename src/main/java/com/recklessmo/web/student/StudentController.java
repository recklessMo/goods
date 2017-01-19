package com.recklessmo.web.student;

import com.recklessmo.model.student.StudentAddInfo;
import com.recklessmo.model.student.StudentAllInfo;
import com.recklessmo.model.student.StudentBaseInfo;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.student.StudentService;
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



}
