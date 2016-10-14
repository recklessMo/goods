package com.recklessmo.web.score;

import com.recklessmo.model.score.result.ScoreTotal;
import com.recklessmo.service.score.ScoreService;
import com.recklessmo.service.score.model.total.SingleCourseTotal;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
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

    @RequestMapping(value = "/analyze", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse analyze(@RequestParam("examId")long examId, @RequestParam("tid")long templateId){
        scoreService.analyze(examId, templateId);
        return new JsonResponse(200, "ok", null);
    }


    @RequestMapping(value = "/load", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse load(@RequestParam("id")long examId){
        List<SingleCourseTotal> data = scoreService.loadTotalScore(examId);
        return new JsonResponse(200, data, null);
    }

}
