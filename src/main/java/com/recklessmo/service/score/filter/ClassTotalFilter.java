package com.recklessmo.service.score.filter;

import com.recklessmo.model.score.Score;
import com.recklessmo.model.score.ScoreTemplate;
import com.recklessmo.service.score.ScoreService;
import com.recklessmo.service.score.model.total.AllCourseTotal;
import com.recklessmo.service.score.model.total.SingleCourseTotal;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 分析整体情况
 *
 * Created by hpf on 9/29/16.
 */
public class ClassTotalFilter implements IDefaultFilter {

    private Map<Long, AllCourseTotal> classMap = null;
    private ScoreTemplate scoreTemplate = null;
    private ScoreService scoreService = null;
    private long examId = 0;

    public ClassTotalFilter(long examId, ScoreTemplate scoreTemplate, ScoreService scoreService){
        this.examId = examId;
        this.scoreTemplate = scoreTemplate;
        this.scoreService = scoreService;
    }

    @Override
    public void  init(){
        classMap = new HashMap<>();
    }

    @Override
    public void process(Score score){

    }

    @Override
    public void postProcess(){

    }

    @Override
    public  void save(){

    }
}
