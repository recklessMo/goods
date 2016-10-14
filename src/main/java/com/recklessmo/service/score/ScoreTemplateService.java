package com.recklessmo.service.score;

import com.recklessmo.dao.score.ScoreTemplateDAO;
import com.recklessmo.model.score.ScoreTemplate;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hpf on 9/30/16.
 */
@Service
public class ScoreTemplateService {

    @Resource
    private ScoreTemplateDAO scoreTemplateDAO;

    public void save(ScoreTemplate scoreTemplate){
        if(scoreTemplate.getId() > 0 ) {
            scoreTemplateDAO.updateTemplate(scoreTemplate);
        }else{
            scoreTemplateDAO.addTemplate(scoreTemplate);
        }
    }

    public List<ScoreTemplate> getList(Page page){
        List<ScoreTemplate> scoreTemplates =  scoreTemplateDAO.getList(page);
        for(ScoreTemplate scoreTemplate : scoreTemplates){
            scoreTemplate.parseJsonDetail();
        }
        return scoreTemplates;
    }

    public int countList(Page page){
        return scoreTemplateDAO.countList(page);
    }

    public ScoreTemplate get(long id){
        ScoreTemplate scoreTemplate = scoreTemplateDAO.getById(id);
        scoreTemplate.parseJsonDetail();
        return scoreTemplate;
    }

    public void delete(long id){
        scoreTemplateDAO.deleteTemplate(id);
    }

}
