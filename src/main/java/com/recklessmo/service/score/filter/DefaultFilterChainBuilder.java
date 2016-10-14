package com.recklessmo.service.score.filter;

import com.recklessmo.model.score.ScoreTemplate;
import com.recklessmo.service.score.ScoreService;
import com.recklessmo.service.score.ScoreTemplateService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by hpf on 9/29/16.
 */
@Component
public class DefaultFilterChainBuilder {

    @Resource
    private ScoreTemplateService scoreTemplateService;

    @Resource
    private ScoreService scoreService;

    //创建filter的时候需要讲模板参数融合进去
    public DefaultFilterChain createDefaultFilterChain(long examId, long templateId){
        ScoreTemplate scoreTemplate = scoreTemplateService.get(templateId);
        if(scoreTemplate == null){
            //应该抛出异常!
        }
        DefaultFilterChain defaultFilterChain = new DefaultFilterChain();
        defaultFilterChain.addFilter(new GradeTotalFilter(examId, scoreTemplate, scoreService));
        defaultFilterChain.addFilter(new ClassTotalFilter(examId, scoreTemplate, scoreService));
        return  defaultFilterChain;
    }


}
