package com.recklessmo.web.score;

import com.recklessmo.model.score.Score;
import com.recklessmo.model.score.result.gap.CourseGap;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.score.ScoreAnalyseService;
import com.recklessmo.service.score.ScoreService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.web.webmodel.model.SelfModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by hpf on 8/29/16.
 */
@Controller
@RequestMapping("/v1/analyse")
public class ScoreAnalyseController {

    @Resource
    private ScoreService scoreService;

    @Resource
    private ScoreAnalyseService scoreAnalyseService;


    /**
     *
     * 整体分析结果
     *
     * pre: score进行缓存
     *
     * type代表
     *
     * @param examId 考试Id
     * @param type  分析的维度, 班级维度或者学科维度
     * @param templateId 模板的ID
     * @return
     */
    @RequestMapping(value = "/total", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse analyzeTotal(@RequestParam("examId")long examId, @RequestParam("type")int type, @RequestParam("templateId")long templateId){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        List<Score> scoreList = scoreService.loadScoreByExamId(userDetails.getOrgId(), examId);
        //开始进行整体分析
        Object result = scoreAnalyseService.analyseTotal(scoreList, type, templateId);
        return new JsonResponse(200, result, null);
    }

    /**
     *
     * 分数段分析的结果, 可以通过更改不同的分数段进行分析
     *
     * @param examId 考试Id
     * @param templateId
     * @return
     */
    @RequestMapping(value = "/gap", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse analyzeGap(@RequestParam("examId")long examId, @RequestParam("templateId")long templateId){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        List<Score> scoreList = scoreService.loadScoreByExamId(userDetails.getOrgId(),examId);
        //根据模板Id获取模板设置的数据
        Collection<CourseGap> obj = scoreAnalyseService.analyseGap(scoreList, templateId);
        return new JsonResponse(200, obj, null);
    }


    /**
     *
     * 分析排名
     *
     * @param examId
     * @param templateId
     * @return
     */
    @RequestMapping(value = "/rank", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse analyzeRank(@RequestParam("examId")long examId, @RequestParam("templateId")long templateId){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        List<Score> scoreList = scoreService.loadScoreByExamId(userDetails.getOrgId(), examId);
        Object obj = scoreAnalyseService.analyseRank(scoreList, 0);
        return new JsonResponse(200, obj, null);
    }

    /**
     *
     * 分析均分
     *
     * @param examId
     * @param templateId
     * @return
     */
    @RequestMapping(value = "/avg", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse analyzeAvg(@RequestParam("examId")long examId, @RequestParam("templateId")long templateId){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        List<Score> scoreList = scoreService.loadScoreByExamId(userDetails.getOrgId(), examId);
        Object obj = scoreAnalyseService.analyseAvg(scoreList, 0);
        return new JsonResponse(200, obj, null);
    }

    /**
     *
     * 分析个人综合情况
     *
     * @param examId
     * @param templateId
     * @return
     */
    @RequestMapping(value = "/self", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse analyzeSelf(@RequestBody SelfModel selfModel){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        List<Score> scoreList = scoreService.getScoreByExamIdAndSidList(selfModel.getExamId(), selfModel.getSidList());
        Object obj = scoreAnalyseService.analyseSelf(userDetails.getOrgId(), scoreList, selfModel.getTemplateId());
        return new JsonResponse(200, obj, null);
    }


    /**
     *
     * 分析两场考试的排名变化
     *
     * @param examIdList
     * @return
     */
    @RequestMapping(value = "/rankchange", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse analyzeRankChange(@RequestBody Long[] examIdList){
        if(examIdList.length != 2){
            return new JsonResponse(402, null, null);
        }
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        List<Score> first = scoreService.loadScoreByExamId(userDetails.getOrgId(), examIdList[0]);
        List<Score> second = scoreService.loadScoreByExamId(userDetails.getOrgId(), examIdList[1]);
        Object obj = scoreAnalyseService.analyseRankChange(first, second);
        return new JsonResponse(200, obj, null);
    }


    /**
     *
     * 分析两场考试的排名变化
     *
     * @param examIdList
     * @return
     */
    @RequestMapping(value = "/absense", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse analyzeAbsense(@RequestBody long examId){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        List<Score> examScore = scoreService.loadScoreByExamId(userDetails.getOrgId(), examId);
        Object obj = scoreAnalyseService.analyseAbsense(userDetails.getOrgId(), examScore);
        return new JsonResponse(200, obj, null);
    }




}
