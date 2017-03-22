package com.recklessmo.web.score;

import com.recklessmo.model.score.CourseScore;
import com.recklessmo.model.score.NewScore;
import com.recklessmo.model.score.Score;
import com.recklessmo.model.score.result.CourseGap;
import com.recklessmo.model.score.result.ScoreGap;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.score.ScoreAnalyseService;
import com.recklessmo.service.score.ScoreService;
import com.recklessmo.util.score.ScoreUtils;
import com.recklessmo.web.webmodel.page.ScoreListPage;
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


    private List<NewScore> getScoreList(long examId){
        ScoreListPage page = new ScoreListPage();
        page.setExamId(examId);
        page.setPage(1);
        page.setCount(100000);
        List<Score> scoreList = scoreService.loadScoreList(page);
        //根据classId获取全部的数据
        List<NewScore> newScores = ScoreUtils.changeScoreToNewScore(scoreList);
        return newScores;
    }

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
        List<NewScore> newScores = getScoreList(examId);
        //开始进行整体分析
        Object result = scoreAnalyseService.analyseTotal(newScores, type, templateId);
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
        List<NewScore> newScores = getScoreList(examId);
        //根据模板Id获取模板设置的数据
        Collection<CourseGap> obj = scoreAnalyseService.analyseGap(newScores, templateId);
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
        List<NewScore> newScores = getScoreList(examId);
        Object obj = scoreAnalyseService.analyseRank(newScores, 0);
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
        List<NewScore> newScores = getScoreList(examId);
        Object obj = scoreAnalyseService.analyseAvg(newScores, 0);
        return new JsonResponse(200, obj, null);
    }


}
