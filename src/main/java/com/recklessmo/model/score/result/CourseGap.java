package com.recklessmo.model.score.result;


import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by hpf on 1/8/17.
 */
public class CourseGap {

    //名字
    private String name;

    private List<ScoreGap> gapList = new LinkedList<>();
    private List<Integer> gapCount = new LinkedList<>();


    public void initGapCount(){
        gapCount.clear();
        for(int i = 0; i < gapList.size(); i++){
            gapCount.add(0);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ScoreGap> getGapList() {
        return gapList;
    }

    public void setGapList(List<ScoreGap> gapList) {
        this.gapList = gapList;
    }

    public List<Integer> getGapCount() {
        return gapCount;
    }

    public void setGapCount(List<Integer> gapCount) {
        this.gapCount = gapCount;
    }
}
