package com.recklessmo.dao.score;

import com.recklessmo.model.score.ScoreTemplate;
import com.recklessmo.web.webmodel.page.Page;

import java.util.List;

/**
 * Created by hpf on 10/8/16.
 */
public interface ScoreTemplateDAO {

    List<ScoreTemplate> getList(Page page);
    int countList(Page page);
    void addTemplate(ScoreTemplate scoreTemplate);
    void deleteTemplate(long id);
    void updateTemplate(ScoreTemplate scoreTemplate);
    ScoreTemplate getById(long id);

}
