package com.recklessmo.dao.score;

import com.recklessmo.model.score.Score;
import com.recklessmo.web.webmodel.page.ScoreListPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hpf on 10/8/16.
 */
public interface ScoreDAO {

    void insertList(@Param("scoreList") List<Score> scoreList);

    void removeList(@Param("orgId")long orgId, @Param("examId")long examId);

    List<Score> getList(ScoreListPage page);

    List<Score> getScoreListByExamId(@Param("examId")long examId);

    Score getScoreByExamIdAndSid(@Param("examId")long examId, @Param("sid")String sid);

    List<Score> getScoreListBySid(@Param("orgId")long orgId, @Param("sid")String sid);

    List<Score> getScoreListBySidList(@Param("orgId")long orgId, @Param("sidList")List<String> sidList);

    List<Score> getScoreByExamIdAndSidList(@Param("examId")long examId, @Param("sidList")List<String> sidList);

    List<String> searchSidByExamId(@Param("orgId") long orgId, @Param("examId")long examId);

}
