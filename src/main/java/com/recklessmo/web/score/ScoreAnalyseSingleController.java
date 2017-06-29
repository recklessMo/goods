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
import java.util.*;

/**
 * Created by hpf on 8/29/16.
 *
 *
 *  用于分析单场考试的一些情况
 *
 */
@Controller
@RequestMapping("/v1/analyse")
public class ScoreAnalyseSingleController {

    @Resource
    private ScoreService scoreService;

    @Resource
    private ScoreAnalyseService scoreAnalyseService;

    /**
     *
     * 整体分析结果 done
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
     * 分数段分析的结果, 可以通过更改不同的分数段进行分析 done
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
     * 分析排名 done
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
        Object obj = scoreAnalyseService.analyseRank(scoreList, templateId);
        return new JsonResponse(200, obj, null);
    }


    /**
     *
     * 分析缺考 done
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

    /**
     * 分数点阵图
     * @param contrastModel
     * @return
     */
    @RequestMapping(value = "/scorePoint", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse analyzeScorePoint(@RequestBody long examId){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        List<Score> scoreList = scoreService.loadScoreByExamId(userDetails.getOrgId(), examId);
        Object result = scoreAnalyseService.analyseScorePoint(userDetails.getOrgId(), scoreList);
        return new JsonResponse(200, result, null);
    }

    /**
     * 名次点阵图
     * @param contrastModel
     * @return
     */
    @RequestMapping(value = "/rankPoint", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse analyzeRankPoint(@RequestBody long examId){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        List<Score> scoreList = scoreService.loadScoreByExamId(userDetails.getOrgId(), examId);
        Object result = scoreAnalyseService.analyseRankPoint(userDetails.getOrgId(), scoreList);
        return new JsonResponse(200, result, null);
    }

}
