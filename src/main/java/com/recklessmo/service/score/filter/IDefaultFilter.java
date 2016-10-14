package com.recklessmo.service.score.filter;

import com.recklessmo.model.score.Score;

/**
 * Created by hpf on 9/29/16.
 */
public interface IDefaultFilter {

    /**
     * 初始化内存结构
     */
    void init();

    /**
     * 处理每一条记录
     * @param score
     */
    void process(Score score);

    /**
     * 处理每行记录处理完毕后,可能需要做一些特殊处理
     * 比如计算标准差, 就需要二次计算
     */
    void postProcess();

    /**
     * 保存结构到数据库
     */
    void save();

}
