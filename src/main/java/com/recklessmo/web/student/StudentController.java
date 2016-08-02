package com.recklessmo.web.student;

import com.recklessmo.model.student.StudentAddInfo;
import com.recklessmo.model.student.StudentBaseInfo;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.student.StudentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by hpf on 7/23/16.
 *
 * 主要用于保存学生的信息
 */
@Controller
public class StudentController {

    @Resource
    private StudentService studentService;

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @ResponseBody
    @RequestMapping(value = "/v1/student/add", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse addStudentInfo(@RequestBody StudentAddInfo studentAddInfo){
        //TODO 可能需要做一些字段的校验
        studentService.insertStudentAddInfo(studentAddInfo);
        return new JsonResponse(200, null, null);
    }


}
