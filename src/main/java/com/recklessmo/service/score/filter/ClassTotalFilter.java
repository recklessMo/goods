package com.recklessmo.service.score.filter;

import com.alibaba.fastjson.JSON;
import com.recklessmo.model.score.Score;
import com.recklessmo.model.score.ScoreTemplate;
import com.recklessmo.model.score.result.ScoreTotal;
import com.recklessmo.service.score.ScoreService;
import com.recklessmo.service.score.model.total.AllCourseTotal;
import com.recklessmo.service.score.model.total.SingleCourseTotal;
import org.apache.xmlbeans.impl.xb.xsdschema.All;
import org.apache.xmlbeans.impl.xb.xsdschema.NamedGroup;

import java.util.HashMap;
import java.util.Iterator;
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
        long cid = score.getCid();
        AllCourseTotal allCourseTotal = classMap.get(cid);
        if(allCourseTotal == null){
            allCourseTotal = new AllCourseTotal();
            classMap.put(cid, allCourseTotal);
        }
        allCourseTotal.addCourseScore(score, scoreTemplate);
    }

    @Override
    public void postProcess(){
        Iterator<Map.Entry<Long, AllCourseTotal>> it = classMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Long, AllCourseTotal> temp = it.next();
            AllCourseTotal allCourseTotal = temp.getValue();
            allCourseTotal.afterScore();
        }
    }

    @Override
    public  void save(){
        Iterator<Map.Entry<Long, AllCourseTotal>> it = classMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Long, AllCourseTotal> temp = it.next();
            AllCourseTotal allCourseTotal = temp.getValue();
            long cid = temp.getKey();
            //进行序列化保存到数据库,采用json格式
            ScoreTotal scoreTotal = new ScoreTotal();
            scoreTotal.setExamId(examId);
            scoreTotal.setCid(cid);
            String str = JSON.toJSONString(allCourseTotal);
            scoreTotal.setDetail(str);
            System.out.println(str.length());
            scoreService.saveTotalScore(scoreTotal);
        }
    }
}
