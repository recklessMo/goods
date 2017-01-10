package com.recklessmo.service.score;

import com.recklessmo.model.score.CourseScore;
import com.recklessmo.model.score.NewScore;
import com.recklessmo.model.score.result.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 */
@Service
public class ScoreAnalyseService {


    /**
     * 进行整体分析.
     * <p>
     * cond:
     * 1. 适应不同的维度
     * 2. 根据学科的进行自适应, 有对应的学科就显示对应的分析结果,无则不显示
     * 3. todo看需不需要返回图形的特定结果(or 我们可以在前端进行数据结构的转换)
     * <p>
     * type为1代表班级维度
     * type为2代表学科维度
     */
    public Object analyseTotal(List<NewScore> scoreList, int type, long templateId) {
        //根据templateId获取模板参数
        //开始分析
        //班级维度
        if (type == 1) {
            Map<Long, ClassTotal> result = new HashMap<>();
            for (NewScore newScore : scoreList) {
                for (CourseScore courseScore : newScore.getCourseScoreList()) {
                    ClassTotal classTotal = result.get(newScore.getCid());
                    if (classTotal == null) {
                        classTotal = new ClassTotal();
                        classTotal.setCid(newScore.getCid());
                        classTotal.setCname(newScore.getCname());
                        result.put(newScore.getCid(), classTotal);
                    }
                    TotalInner totalInner;
                    Optional<TotalInner> data = classTotal.getCourseTotalList().stream().filter(m->m.getName().equals(courseScore.getName())).findAny();
                    if(data.isPresent()){
                        totalInner = data.get();
                    }else{
                        totalInner = new TotalInner();
                        totalInner.setName(courseScore.getName());
                        classTotal.getCourseTotalList().add(totalInner);
                    }
                    totalInner(courseScore, totalInner);
                }
            }
            return result.values();
        } else if (type == 2) {
            Map<String, CourseTotal> result = new HashMap<>();
            for (NewScore newScore : scoreList) {
                List<CourseScore> data = newScore.getCourseScoreList();
                for(CourseScore courseScore : data){
                    CourseTotal courseTotal = result.get(courseScore.getName());
                    if(courseTotal == null){
                        courseTotal = new CourseTotal();
                        courseTotal.setCourseName(courseScore.getName());
                        result.put(courseScore.getName(), courseTotal);
                    }

                    TotalInner totalInner;
                    Optional<TotalInner> temp = courseTotal.getClassTotalList().stream().filter(m->m.getName().equals(courseScore.getCname())).findAny();
                    if(temp.isPresent()){
                        totalInner = temp.get();
                    }else{
                        totalInner = new TotalInner();
                        totalInner.setName(courseScore.getCname());
                        courseTotal.getClassTotalList().add(totalInner);
                    }
                    totalInner(courseScore, totalInner);
                }
            }
            return result.values();
        }
        return null;
    }

    private void totalInner(CourseScore courseScore, TotalInner totalInner){
        if (courseScore.getScore() >= 60) {
            totalInner.setQualified(totalInner.getQualified() + 1);
        }
        if (courseScore.getScore() >= 80) {
            totalInner.setGood(totalInner.getGood() + 1);
        }
        if (courseScore.getScore() >= 90) {
            totalInner.setBest(totalInner.getBest() + 1);
        }
        if (courseScore.getScore() >= 100) {
            totalInner.setFull(totalInner.getFull() + 1);
        }
        //总人数
        totalInner.setTotalCount(totalInner.getTotalCount() + 1);
        //最高分
        if (courseScore.getScore() > totalInner.getMax()) {
            totalInner.setMax(courseScore.getScore());
        }
        //最低分
        if (courseScore.getScore() < totalInner.getMin()) {
            totalInner.setMin(courseScore.getScore());
        }
    }


    /**
     *
     * 分数线分析, 分数线就基于学科来做
     * 基于班级的话不太好做, 有可能一个班级里面多个学科的分数段设置不同
     * 这样界面不好看!
     *
     * @param newScores
     * @param templateId
     * @return
     */
    public Object analyseGap(List<NewScore> newScores, long templateId){
        Map<String, CourseGap> gapMap = new HashedMap();
        for(NewScore score : newScores){
            for(CourseScore courseScore : score.getCourseScoreList()) {
                CourseGap gap = gapMap.get(courseScore.getName());
                if(gap == null){
                    gap = new CourseGap();
                    gap.setName(courseScore.getName());
                    List<ScoreGap> gapList = new LinkedList<>();
                    gapList.add(new ScoreGap(1d,20d));
                    gapList.add(new ScoreGap(21d,60d));
                    gapList.add(new ScoreGap(61d,70d));
                    gapList.add(new ScoreGap(71d,80d));
                    gapList.add(new ScoreGap(81d,90d));
                    gapList.add(new ScoreGap(91d,100d));
                    gap.setGapList(gapList);
                    gapMap.put(courseScore.getName(), gap);
                }

                GapInner gapInner;
                Optional<GapInner> temp = gap.getGapInnerList().stream().filter(m->m.getCname().equals(courseScore.getCname())).findAny();
                if(temp.isPresent()){
                    gapInner = temp.get();
                }else{
                    gapInner = new GapInner(gap.getGapList().size());
                    gapInner.setCname(courseScore.getCname());
                    gap.getGapInnerList().add(gapInner);
                }

                for(int i = 0; i < gap.getGapList().size(); i++){
                    ScoreGap scoreGap = gap.getGapList().get(i);
                    if(courseScore.getScore() >= scoreGap.getStart() && courseScore.getScore()<= scoreGap.getEnd()){
                        gapInner.getCountList().set(i, gapInner.getCountList().get(i) + 1);
                    }
                }
            }
        }
        return gapMap.values();
    }

    /**
     *
     * 名次分析
     *
     * @param newScores
     * @param templateId
     * @return
     */
    public Object analyseRank(List<NewScore> newScores, long templateId){
        Map<String, CourseRank> rankMap = new HashedMap();
        for(NewScore score : newScores){
            for(CourseScore courseScore : score.getCourseScoreList()) {
                CourseRank rank = rankMap.get(courseScore.getName());
                if(rank == null){
                    rank = new CourseRank();
                    rank.setName(courseScore.getName());
                    List<RankGap> gapList = new LinkedList<>();
                    gapList.add(new RankGap(1,10));
                    gapList.add(new RankGap(11,20));
                    gapList.add(new RankGap(21,30));
                    gapList.add(new RankGap(31,40));
                    gapList.add(new RankGap(41,50));
                    gapList.add(new RankGap(51,60));
                    gapList.add(new RankGap(61,70));
                    gapList.add(new RankGap(71,80));
                    gapList.add(new RankGap(81,90));
                    gapList.add(new RankGap(91,100));
                    gapList.add(new RankGap(101,100000));
                    rank.setGapList(gapList);
                    rankMap.put(courseScore.getName(), rank);
                }

                RankInner rankInner;
                Optional<RankInner> temp = rank.getGapInnerList().stream().filter(m->m.getCname().equals(courseScore.getCname())).findAny();
                if(temp.isPresent()){
                    rankInner = temp.get();
                }else{
                    rankInner = new RankInner(rank.getGapList().size());
                    rankInner.setCname(courseScore.getCname());
                    rank.getGapInnerList().add(rankInner);
                }

                for(int i = 0; i < rank.getGapList().size(); i++){
                    RankGap rankGap = rank.getGapList().get(i);
                    if(courseScore.getScore() >= rankGap.getStart() && courseScore.getScore()<= rankGap.getEnd()){
                        rankInner.getCountList().set(i, rankInner.getCountList().get(i) + 1);
                    }
                }
            }
        }
        return rankMap.values();
    }


}
