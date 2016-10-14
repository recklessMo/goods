package com.recklessmo.service.score.filter;

import com.alibaba.fastjson.JSON;
import com.recklessmo.model.score.Score;
import com.recklessmo.model.score.ScoreTemplate;
import com.recklessmo.model.score.result.ScoreTotal;
import com.recklessmo.service.score.ScoreService;
import com.recklessmo.service.score.model.total.AllCourseTotal;

/**
 *
 * 分析整体情况
 *
 * Created by hpf on 9/29/16.
 */
public class GradeTotalFilter implements IDefaultFilter {

    private AllCourseTotal allCourseTotal = null;
    private ScoreTemplate scoreTemplate = null;
    private ScoreService scoreService = null;
    private long examId = 0;


    /**
     * 初始化, 需要根据引入模板
     */
    public GradeTotalFilter(long examId, ScoreTemplate scoreTemplate, ScoreService scoreService){
        this.examId = examId;
        this.scoreTemplate = scoreTemplate;
        this.scoreService = scoreService;
    }

    @Override
    public void  init(){
        allCourseTotal = new AllCourseTotal();
    }

    @Override
    public void process(Score score){
        //根据模板进行处理
        allCourseTotal.addCourseScore(score, scoreTemplate);
    }

    @Override
    public void postProcess(){
        //计算标准差
        allCourseTotal.afterScore();
    }

    @Override
    public void save(){
        //进行序列化保存到数据库,采用json格式
        ScoreTotal scoreTotal = new ScoreTotal();
        scoreTotal.setExamId(examId);
        scoreTotal.setCid(0);
        String str = JSON.toJSONString(allCourseTotal);
        scoreTotal.setDetail(str);
        System.out.println(str.length());
        scoreService.saveTotalScore(scoreTotal);
    }
}
