package com.recklessmo.model.score.result;


import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 1/8/17.
 */
public class CourseRank {

    //名字
    private String name;

    private List<RankGap> gapList = new LinkedList<>();
    private List<RankInner> gapInnerList = new LinkedList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RankGap> getGapList() {
        return gapList;
    }

    public void setGapList(List<RankGap> gapList) {
        this.gapList = gapList;
    }

    public List<RankInner> getGapInnerList() {
        return gapInnerList;
    }

    public void setGapInnerList(List<RankInner> gapInnerList) {
        this.gapInnerList = gapInnerList;
    }
}
