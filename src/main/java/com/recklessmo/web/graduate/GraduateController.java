package com.recklessmo.web.graduate;

import com.recklessmo.model.exam.Exam;
import com.recklessmo.model.graduate.Graduate;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.student.StudentAddInfo;
import com.recklessmo.model.student.StudentAllInfo;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.exam.ExamService;
import com.recklessmo.service.graduate.GraduateService;
import com.recklessmo.service.student.StudentService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.web.webmodel.page.ExamListPage;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
@RequestMapping("/v1/graduate")
@Controller
public class GraduateController {

    @Resource
    private GraduateService graduateService;

    @Resource
    private StudentService studentService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JsonResponse listGraduate(@RequestBody Page page){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        page.setOrgId(userDetails.getOrgId());
        List<Graduate> graduateList = graduateService.getGraduateList(page);
        return new JsonResponse(200, graduateList, null);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JsonResponse addGraduate(@RequestBody Graduate graduate){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        graduate.setOrgId(userDetails.getOrgId());
        graduate.setOpId(userDetails.getId());
        graduate.setOpName(userDetails.getName());
        graduate.setCreated(new Date());
        StudentAllInfo studentAllInfo = studentService.getStudentInfoBySid(userDetails.getOrgId(), graduate.getSid());
        graduate.setName(studentAllInfo.getName());
        graduateService.addGraduate(graduate);
        return new JsonResponse(200, null, null);
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JsonResponse updateGraduate(@RequestBody Graduate graduate){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        graduate.setOrgId(userDetails.getOrgId());
        graduateService.updateGraduate(graduate);
        return new JsonResponse(200, null, null);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public JsonResponse deleteGraduate(@RequestBody long id){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        graduateService.deleteGraduate(id);
        return new JsonResponse(200, null, null);
    }


}
