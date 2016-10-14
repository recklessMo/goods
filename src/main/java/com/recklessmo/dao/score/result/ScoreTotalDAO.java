package com.recklessmo.dao.score.result;

import com.recklessmo.model.score.result.ScoreTotal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * 用于保存整体分数线分析的结果, 比如满分,及格,优秀,良好等等~
 * Created by hpf on 10/12/16.
 */
public interface ScoreTotalDAO {

    void save(ScoreTotal scoreTotal);
    List<ScoreTotal> getByExamAndCID(@Param("examId")long examId);

}
