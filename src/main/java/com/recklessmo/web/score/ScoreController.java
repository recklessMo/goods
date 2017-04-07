package com.recklessmo.web.score;

import com.recklessmo.model.score.Score;
import com.recklessmo.service.score.ScoreService;
import com.recklessmo.util.score.ScoreUtils;
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
     * 查询成绩单
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
