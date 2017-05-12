package com.recklessmo.web.score;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.recklessmo.model.score.Score;
import com.recklessmo.model.score.ScoreTemplate;
import com.recklessmo.model.score.inner.CourseGapSetting;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.score.ScoreTemplateService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.web.webmodel.page.Page;
import com.recklessmo.web.webmodel.page.ScoreListPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 用于模板构建
 *
 * Created by hpf on 9/30/16.
 */
@Controller
@RequestMapping("/v1/template")
public class ScoreTemplateController {

    @Resource
    private ScoreTemplateService scoreTemplateService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse add(@RequestBody ScoreTemplate scoreTemplate){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        scoreTemplate.setOrgId(userDetails.getOrgId());
        scoreTemplateService.save(scoreTemplate);
        return new JsonResponse(200, null, null);
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse list(@RequestBody Page page){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        page.setOrgId(userDetails.getOrgId());
        List<ScoreTemplate> scoreTemplates = scoreTemplateService.getList(page);
        int cnt = scoreTemplateService.countList(page);
        return new JsonResponse(200, scoreTemplates, cnt);
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse delete(@RequestBody long id){
        scoreTemplateService.delete(id);
        return new JsonResponse(200, null, null);
    }

    @RequestMapping(value = "/get", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse get(@RequestParam("id")long id){
        return new JsonResponse(200, null, null);
    }


    public static void main(String[] args){
    }

}
