package com.recklessmo.service.score;

import com.alibaba.fastjson.JSON;
import com.recklessmo.dao.score.ScoreDAO;
import com.recklessmo.dao.score.result.ScoreTotalDAO;
import com.recklessmo.model.score.Score;
import com.recklessmo.model.score.result.ScoreTotal;
import com.recklessmo.service.score.filter.DefaultFilterChain;
import com.recklessmo.service.score.filter.DefaultFilterChainBuilder;
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

    @Resource
    private DefaultFilterChainBuilder defaultFilterChainBuilder;

    //传入考试的ID, 以及分析册的ID 然后开始进行分析. 要用事务管理起来
    public void analyze(long examId, long templateId){
        //获取全部数据
        ScoreListPage page = new ScoreListPage();
        page.setExamId(examId);
        page.setCid(0);
        List<Score> scoreList = scoreDAO.getList(page);
        //开始进行分析
        analyzeInner(examId, templateId, scoreList);
    }

    /**
     *
     * 根据成绩列表进行分析
     *
     *
     * 1. 年级整体信息
     * 2. 班级整体信息
     * 3.
     *
     *
     *
     * @param scoreList
     */
    public void analyzeInner(long examId, long templateId, List<Score> scoreList){
        //构建filter-chain
        DefaultFilterChain defaultFilterChain = defaultFilterChainBuilder.createDefaultFilterChain(examId, templateId);
        //开始进行分析
        for(int i = 0; i < scoreList.size(); i++){
            Score score = scoreList.get(i);
            defaultFilterChain.processScore(score);
        }
        //分析完毕, 填入一些必须二次处理的字段, 比如标准差这类需要平均值才能进行计算的值
        defaultFilterChain.postProcess();
        //对结果进行保存, 存入对应的数据库
        defaultFilterChain.saveResult();
    }


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
     *
     *  保存到数据库表中
     *
     */
    public void saveTotalScore(ScoreTotal scoreTotal){
        scoreTotalDAO.save(scoreTotal);
    }


    /**
     * 获取整体分析的结果
     */
    public List<SingleCourseTotal> loadTotalScore(long examId, long cid){
        List<ScoreTotal> scoreTotalList =  scoreTotalDAO.getByExamAndCID(examId, cid);
        List<SingleCourseTotal> result = new LinkedList<>();
        for(ScoreTotal scoreTotal : scoreTotalList){
            AllCourseTotal allCourseTotal = JSON.parseObject(scoreTotal.getDetail(), AllCourseTotal.class);
            result.addAll(allCourseTotal.getCourseMap().values());
        }
        return result;
    }


    public List<Score> loadScoreList(ScoreListPage page){
        List<Score> scoreList = scoreDAO.getList(page);
        return scoreList;
    }



}
