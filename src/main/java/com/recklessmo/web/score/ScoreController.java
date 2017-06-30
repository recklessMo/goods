package com.recklessmo.web.score;

import com.alibaba.fastjson.JSONObject;
import com.recklessmo.model.dynamicTable.DynamicTable;
import com.recklessmo.model.dynamicTable.TableColumn;
import com.recklessmo.model.score.CourseScore;
import com.recklessmo.model.score.Score;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.score.ScoreAnalyseService;
import com.recklessmo.service.score.ScoreService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.web.webmodel.page.ScoreListPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.naming.NameParser;
import java.util.*;

/**
 * Created by hpf on 8/29/16.
 */
@Controller
@RequestMapping("/v1/score")
public class ScoreController {

    @Resource
    private ScoreService scoreService;

    @Resource
    private ScoreAnalyseService scoreAnalyseService;

    /**
     * 查询成绩单 done
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResponse list(@RequestBody ScoreListPage scoreListPage) {
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        scoreListPage.setOrgId(userDetails.getOrgId());
        List<Score> data = scoreService.loadScoreList(scoreListPage);
        Object result =  scoreAnalyseService.analyseScore(data);
        return new JsonResponse(200, result, null);
    }


}
