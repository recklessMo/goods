package com.recklessmo.web.graduate;

import com.recklessmo.model.exam.Exam;
import com.recklessmo.model.graduate.Graduate;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.exam.ExamService;
import com.recklessmo.service.graduate.GraduateService;
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
