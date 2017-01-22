package com.recklessmo.web.student;

import com.recklessmo.model.student.StudentAddInfo;
import com.recklessmo.model.student.StudentAllInfo;
import com.recklessmo.model.student.StudentBaseInfo;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.student.StudentService;
import com.recklessmo.web.webmodel.page.StudentPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * 主要用于保存学生的毕业去向
 */
@Controller
public class StudentGraduateController {

    @Resource
    private StudentService studentService;

    @ResponseBody
    @RequestMapping(value = "/v1/graduate/add", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse addStudentInfo(@RequestBody StudentAddInfo studentAddInfo){
        studentService.insertStudentAddInfo(studentAddInfo);
        return new JsonResponse(200, null, null);
    }

    @ResponseBody
    @RequestMapping(value = "/v1/graduate/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse list(@RequestBody StudentPage page){
        int cnt = studentService.getStudentBaseInfoTotalCount(page);
        List<StudentBaseInfo> infoList = studentService.getStudentBaseInfo(page);
        return new JsonResponse(200, infoList, cnt);
    }



}
