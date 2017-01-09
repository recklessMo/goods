package com.recklessmo.web.score;

import com.recklessmo.model.score.CourseScore;
import com.recklessmo.model.score.NewScore;
import com.recklessmo.model.score.Score;
import com.recklessmo.model.score.result.CourseTotal;
import com.recklessmo.model.score.result.CourseGap;
import com.recklessmo.model.score.result.ScoreGap;
import com.recklessmo.response.JsonResponse;
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


    /**
     * 整体分析
     * */
    @RequestMapping(value = "/total", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse analyzeTotal(@RequestParam("examId")long examId, @RequestParam("classId")long classId){
        ScoreListPage page = new ScoreListPage();
        page.setExamId(examId);
        page.setPage(1);
        page.setCount(100000);
        List<Score> scoreList = scoreService.loadScoreList(page);
        //根据classId获取全部的数据
        List<NewScore> newScores = ScoreUtils.changeScoreToNewScore(scoreList);
        //开始进行整体分析
        Map<String, CourseTotal> result = new HashMap<>();
        for(NewScore newScore : newScores) {
            for (CourseScore courseScore : newScore.getCourseScoreList()) {
                CourseTotal courseTotal = result.get(courseScore.getName());
                if (courseTotal == null) {
                    courseTotal = new CourseTotal();
                    courseTotal.setName(courseScore.getName());
                    result.put(courseScore.getName(), courseTotal);
                }
                if(courseScore.getScore() >= 60) {
                    courseTotal.setQualified(courseTotal.getQualified() + 1);
                }
                if(courseScore.getScore() >= 80) {
                    courseTotal.setGood(courseTotal.getGood() + 1);
                }
                if(courseScore.getScore() >= 90){
                    courseTotal.setBest(courseTotal.getBest() + 1);
                }
                if(courseScore.getScore() >= 100) {
                    courseTotal.setFull(courseTotal.getFull() + 1);
                }
                //总人数
                courseTotal.setTotalCount(courseTotal.getTotalCount() + 1);
                //最高分
                if(courseScore.getScore() > courseTotal.getMax()){
                    courseTotal.setMax(courseScore.getScore());
                }
                //最低分
                if(courseScore.getScore() < courseTotal.getMin()){
                    courseTotal.setMin(courseScore.getScore());
                }
            }
        }
        return new JsonResponse(200, result.values(), null);
    }

    @RequestMapping(value = "/gap", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse analyzeGap(@RequestParam("examId")long examId, @RequestParam("classId")long classId){
        ScoreListPage page = new ScoreListPage();
        page.setExamId(examId);
        page.setPage(1);
        page.setCount(100000);
        List<Score> scoreList = scoreService.loadScoreList(page);
        //根据classId获取全部的数据
        List<NewScore> newScores = ScoreUtils.changeScoreToNewScore(scoreList);
        //根据模板Id获取模板设置的数据
        Map<String, CourseGap> gapMap = new HashMap<>();

        /**
         * 模拟一些参数
         */


        for(NewScore score : newScores){
            for(CourseScore courseScore : score.getCourseScoreList()) {
                CourseGap gap = gapMap.get(courseScore.getName());
                if(gap == null){
                    gap = new CourseGap();
                    gap.setName(courseScore.getName());
                    List<ScoreGap> gapList = new LinkedList<>();
                    gapList.add(new ScoreGap(1d,20d));
                    gapList.add(new ScoreGap(21d,100d));
                    gap.setGapList(gapList);
                    gap.initGapCount();
                    gapMap.put(courseScore.getName(), gap);
                }
                for(int i = 0; i < gap.getGapList().size(); i++){
                    ScoreGap scoreGap = gap.getGapList().get(i);
                    if(courseScore.getScore() >= scoreGap.getStart() && courseScore.getScore()<= scoreGap.getEnd()){
                        gap.getGapCount().set(i, gap.getGapCount().get(i) + 1);
                    }
                }
            }
        }

        Collection<CourseGap> res = gapMap.values();

        return new JsonResponse(200, res, null);
    }


}
