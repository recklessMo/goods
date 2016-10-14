package com.recklessmo.service.score.filter;

import com.recklessmo.model.score.Score;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 9/29/16.
 */
public class DefaultFilterChain {

    private List<IDefaultFilter> filterList;

    public DefaultFilterChain(){
        filterList = new LinkedList<>();
    }

    public void addFilter(IDefaultFilter IDefaultFilter){
        filterList.add(IDefaultFilter);
        IDefaultFilter.init();
    }

    /**
     * 处理
     * @param score
     */
    public void processScore(Score score){
        for(int i = 0; i < filterList.size(); i++){
            filterList.get(i).process(score);
        }
    }

    /**
     * 后处理
     */
    public void postProcess() {
        for (int i = 0; i < filterList.size(); i++) {
            filterList.get(i).postProcess();
        }
    }

    /**
     * 保存结果
     */
    public void saveResult() {
        for (int i = 0; i < filterList.size(); i++) {
            filterList.get(i).save();
        }
    }
}
