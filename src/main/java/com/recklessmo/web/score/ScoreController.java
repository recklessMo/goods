package com.recklessmo.web.score;

import com.recklessmo.model.score.Score;
import com.recklessmo.service.score.ScoreService;
import com.recklessmo.service.score.model.total.SingleCourseTotal;
import com.recklessmo.web.webmodel.page.ScoreListPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.recklessmo.response.JsonResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
@Controller
@RequestMapping("/v1/score")
public class ScoreController {

    @Resource
    private ScoreService scoreService;

    /**
     *
     * 分析结果
     *
     * @param examId
     * @param templateId
     * @return
     */
    @RequestMapping(value = "/analyze", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse analyze(@RequestParam("examId")long examId, @RequestParam("tid")long templateId){
        scoreService.analyze(examId, templateId);
        return new JsonResponse(200, "ok", null);
    }


    /**
     *
     * 装载结果
     *
     * @param examId
     * @return
     */
    @RequestMapping(value = "/load", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse load(@RequestParam("id")long examId){
        long cid = 0;
        List<SingleCourseTotal> data = scoreService.loadTotalScore(examId, cid);
        return new JsonResponse(200, data, null);
    }

    /**
     * 查询成绩单
     *
     * 1. 根据exam找到对应的年级
     * 2. 根据对应的年级找到对应的班级
     * 3. 选择班级,然后返回成绩
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse list(@RequestBody ScoreListPage scoreListPage){
        List<Score> data = scoreService.loadScoreList(scoreListPage);
        return new JsonResponse(200, data, null);
    }

}
