package com.recklessmo.service.score.model.total;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.AfterFilter;
import com.recklessmo.model.score.Score;
import com.recklessmo.model.score.ScoreTemplate;
import com.recklessmo.model.score.inner.CourseTotalSetting;
import com.recklessmo.model.setting.Course;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.util.*;

/**
 * Created by hpf on 9/29/16.
 */
public class AllCourseTotal {

    private Map<String, SingleCourseTotal> courseMap = null;

    public AllCourseTotal(){
        courseMap = new HashMap<>();
    }

    public Map<String, SingleCourseTotal> getCourseMap() {
        return courseMap;
    }

    public void setCourseMap(Map<String, SingleCourseTotal> courseMap) {
        this.courseMap = courseMap;
    }


    public void addCourseScore(Score score, ScoreTemplate scoreTemplate){
        addScoreInner(Course.CHINESE, score.getChinese(), scoreTemplate);
        addScoreInner(Course.MATH, score.getMath(), scoreTemplate);
        addScoreInner(Course.ENGLISH, score.getEnglish(), scoreTemplate);
        addScoreInner(Course.PHYSICS, score.getPhysics(), scoreTemplate);
        addScoreInner(Course.CHEMISTRY, score.getChemistry(), scoreTemplate);
        addScoreInner(Course.BIOLOGY, score.getBiology(), scoreTemplate);
        addScoreInner(Course.POLITICS, score.getPolotics(), scoreTemplate);
        addScoreInner(Course.HISTORY, score.getHistory(), scoreTemplate);
        addScoreInner(Course.GEO, score.getGeo(), scoreTemplate);
    }

    private void addScoreInner(String name, double score, ScoreTemplate scoreTemplate){
        SingleCourseTotal singleCourseTotal = courseMap.get(name);
        if(singleCourseTotal == null){
            singleCourseTotal = new SingleCourseTotal();
            singleCourseTotal.setCourseName(name);
            courseMap.put(name, singleCourseTotal);
        }
        //增加总的分数
        singleCourseTotal.setTotalScore(singleCourseTotal.getTotalScore() + score);
        //增加总的参考人数
        singleCourseTotal.setTotalCount(singleCourseTotal.getTotalCount() + 1);
        //比较最高分
        if(score > singleCourseTotal.getMax()){
            singleCourseTotal.setMax(score);
        }
        //比较最低分
        if(score < singleCourseTotal.getMin()){
            singleCourseTotal.setMin(score);
        }
        //增加分数数据, 用于计算标准差
        singleCourseTotal.getScoreList().add(score);
        //计算优秀人数
        calScoreLevel(name, score, singleCourseTotal, scoreTemplate);
    }

    private void calScoreLevel(String name, double score, SingleCourseTotal singleCourseTotal, ScoreTemplate scoreTemplate){
        if(Course.CHINESE.equals(name)){
            if(score >= scoreTemplate.getCourseTotalSetting().getChineseQualified()){
                singleCourseTotal.setQualified(singleCourseTotal.getQualified() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getChinesegood()){
                singleCourseTotal.setGood(singleCourseTotal.getGood() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getChinesebest()){
                singleCourseTotal.setBest(singleCourseTotal.getBest() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getChineseFull()){
                singleCourseTotal.setFull(singleCourseTotal.getFull() + 1);
            }
        }else if(Course.MATH.equals(name)){
            if(score >= scoreTemplate.getCourseTotalSetting().getMathQualified()){
                singleCourseTotal.setQualified(singleCourseTotal.getQualified() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getMathgood()){
                singleCourseTotal.setGood(singleCourseTotal.getGood() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getMathbest()){
                singleCourseTotal.setBest(singleCourseTotal.getBest() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getMathFull()){
                singleCourseTotal.setFull(singleCourseTotal.getFull() + 1);
            }
        }else if(Course.ENGLISH.equals(name)){
            if(score >= scoreTemplate.getCourseTotalSetting().getEnglishQualified()){
                singleCourseTotal.setQualified(singleCourseTotal.getQualified() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getEnglishgood()){
                singleCourseTotal.setGood(singleCourseTotal.getGood() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getEnglishbest()){
                singleCourseTotal.setBest(singleCourseTotal.getBest() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getEnglishFull()){
                singleCourseTotal.setFull(singleCourseTotal.getFull() + 1);
            }
        }else if(Course.POLITICS.equals(name)){
            if(score >= scoreTemplate.getCourseTotalSetting().getPoliticQualified()){
                singleCourseTotal.setQualified(singleCourseTotal.getQualified() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getPoliticgood()){
                singleCourseTotal.setGood(singleCourseTotal.getGood() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getPoliticbest()){
                singleCourseTotal.setBest(singleCourseTotal.getBest() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getPoliticFull()){
                singleCourseTotal.setFull(singleCourseTotal.getFull() + 1);
            }
        }else if(Course.HISTORY.equals(name)){
            if(score >= scoreTemplate.getCourseTotalSetting().getHistoryQualified()){
                singleCourseTotal.setQualified(singleCourseTotal.getQualified() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getHistorygood()){
                singleCourseTotal.setGood(singleCourseTotal.getGood() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getHistorybest()){
                singleCourseTotal.setBest(singleCourseTotal.getBest() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getHistoryFull()){
                singleCourseTotal.setFull(singleCourseTotal.getFull() + 1);
            }
        }else if(Course.GEO.equals(name)){
            if(score >= scoreTemplate.getCourseTotalSetting().getGeoQualified()){
                singleCourseTotal.setQualified(singleCourseTotal.getQualified() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getGeogood()){
                singleCourseTotal.setGood(singleCourseTotal.getGood() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getGeobest()){
                singleCourseTotal.setBest(singleCourseTotal.getBest() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getGeoFull()){
                singleCourseTotal.setFull(singleCourseTotal.getFull() + 1);
            }
        }else if(Course.PHYSICS.equals(name)){
            if(score >= scoreTemplate.getCourseTotalSetting().getPhysicsQualified()){
                singleCourseTotal.setQualified(singleCourseTotal.getQualified() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getPhysicsgood()){
                singleCourseTotal.setGood(singleCourseTotal.getGood() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getPhysicsbest()){
                singleCourseTotal.setBest(singleCourseTotal.getBest() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getPhysicsFull()){
                singleCourseTotal.setFull(singleCourseTotal.getFull() + 1);
            }
        }else if(Course.CHEMISTRY.equals(name)){
            if(score >= scoreTemplate.getCourseTotalSetting().getChemistryQualified()){
                singleCourseTotal.setQualified(singleCourseTotal.getQualified() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getChemistrygood()){
                singleCourseTotal.setGood(singleCourseTotal.getGood() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getChemistrybest()){
                singleCourseTotal.setBest(singleCourseTotal.getBest() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getChemistryFull()){
                singleCourseTotal.setFull(singleCourseTotal.getFull() + 1);
            }
        }else if(Course.BIOLOGY.equals(name)){
            if(score >= scoreTemplate.getCourseTotalSetting().getBiologyQualified()){
                singleCourseTotal.setQualified(singleCourseTotal.getQualified() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getBiologygood()){
                singleCourseTotal.setGood(singleCourseTotal.getGood() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getBiologybest()){
                singleCourseTotal.setBest(singleCourseTotal.getBest() + 1);
            }
            if(score >= scoreTemplate.getCourseTotalSetting().getBiologyFull()){
                singleCourseTotal.setFull(singleCourseTotal.getFull() + 1);
            }
        }
    }

    public void afterScore(){
        Iterator<Map.Entry<String, SingleCourseTotal>> it = courseMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, SingleCourseTotal> data = it.next();
            SingleCourseTotal singleCourseTotal = data.getValue();
            List<Double> scoreList = singleCourseTotal.getScoreList();
            //计算平均分
            singleCourseTotal.setAvg(singleCourseTotal.getTotalScore() / singleCourseTotal.getTotalCount());
            //计算标准差
            double sum = 0;
            for(double a : scoreList){
                sum += ((a - singleCourseTotal.getAvg()) * (a  - singleCourseTotal.getAvg()));
            }
            singleCourseTotal.setStdAvg(Math.sqrt(sum / scoreList.size()));
            //将原始数据释放掉, 避免序列化到数据库中
            singleCourseTotal.setScoreList(null);
        }
    }



}
