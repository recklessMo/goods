package com.recklessmo.service.score;

import com.alibaba.fastjson.JSON;
import com.recklessmo.dao.score.ScoreDAO;
import com.recklessmo.dao.score.result.ScoreTotalDAO;
import com.recklessmo.model.score.Score;
import com.recklessmo.model.score.result.ScoreTotal;
import com.recklessmo.service.score.model.total.AllCourseTotal;
import com.recklessmo.service.score.model.total.SingleCourseTotal;
import com.recklessmo.web.webmodel.page.ScoreListPage;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 9/29/16.
 */
@Service
public class ScoreService {

    @Resource
    private ScoreDAO scoreDAO;
    @Resource
    private ScoreTotalDAO scoreTotalDAO;


    /**
     *
     * 插入成绩数据
     *
     * @param scoreList
     */
    public void insertScoreList(List<Score> scoreList){
        if(scoreList != null && scoreList.size() != 0) {
            scoreDAO.insertList(scoreList);
        }
    }


    /**
     * 获取成绩列表
     *
     * @param page
     * @return
     */
    public List<Score> loadScoreList(ScoreListPage page){
        List<Score> scoreList = scoreDAO.getList(page);
        //处理一下,处理成标准的可以自定义科目的结果
        //
        return scoreList;
    }



}
