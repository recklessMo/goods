package com.recklessmo.web.score;

import com.recklessmo.model.score.Score;
import com.recklessmo.model.score.result.gap.CourseGap;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.score.ScoreAnalyseService;
import com.recklessmo.service.score.ScoreService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.web.webmodel.model.ContrastModel;
import com.recklessmo.web.webmodel.model.SelfModel;
import com.recklessmo.web.webmodel.model.TrendModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
@Controller
@RequestMapping("/v1/analyse")
public class ScoreAnalyseTotalController {

    @Resource
    private ScoreService scoreService;

    @Resource
    private ScoreAnalyseService scoreAnalyseService;


    /**
     *
     * 成绩趋势分析
     *
     * 分析个人综合情况， 个人所有的考试的综合成绩情况
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
     * 成绩进退步分析
     *
     * 个人进退步分析，暂时只支持两场考试
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
     * 分析个人成绩趋势
     *
     * @param sid
     * @param showType
     * @param examType
     * @return
     */
    @RequestMapping(value = "/trend", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse analyzeTrend(@RequestBody TrendModel trendModel){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        List<Score> scoreList = scoreService.getScoreListBySid(userDetails.getOrgId(), trendModel.getSid());
        Object result = scoreAnalyseService.analyseTrend(trendModel.getExamTypes(), trendModel.getShowType(), scoreList);
        return new JsonResponse(200, result, null);
    }


    /**
     *
     * 个人综合对比
     *
     * @param trendModel
     * @return
     */
    @RequestMapping(value = "/contrast", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse analyzeContrast(@RequestBody ContrastModel contrastModel){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        if(contrastModel.getSidList().size() == 0){
            return new JsonResponse(200, null, null);
        }
        List<Score> scoreList = scoreService.getScoreListBySidList(userDetails.getOrgId(), contrastModel.getSidList());
        Object result = scoreAnalyseService.analyseContrast(contrastModel.getExamTypes(), contrastModel.getShowType(), scoreList);
        return new JsonResponse(200, result, null);
    }
}
