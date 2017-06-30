package com.recklessmo.dao.score;

import com.recklessmo.model.score.ScoreTemplate;
import com.recklessmo.web.webmodel.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hpf on 10/8/16.
 */
public interface ScoreTemplateDAO {

    List<ScoreTemplate> getList(Page page);
    int countList(Page page);
    void addTemplate(ScoreTemplate scoreTemplate);
    void deleteTemplate(@Param("id") long id);
    void updateTemplate(ScoreTemplate scoreTemplate);
    ScoreTemplate getById(@Param("id") long id);

    void cancelDefault(@Param("type")int type);
    void makeDefault(@Param("id")long id);

}
