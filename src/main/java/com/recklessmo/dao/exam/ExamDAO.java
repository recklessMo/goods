package com.recklessmo.dao.exam;

import com.recklessmo.model.exam.Exam;
import com.recklessmo.web.webmodel.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
public interface ExamDAO {

    List<Exam> listExam(Page page);

    int listExamCount(Page page);

    void addExam(Exam exam);

    void updateExamStatus(@Param("id") long id, @Param("status")int status, @Param("time")Date time);

    Exam getExamById(@Param("orgId")long orgId, @Param("id")long id);

    List<Exam> getExamByIdList(@Param("orgId")long orgId, @Param("idList")List<Long> idList);

    void deleteExam(@Param("orgId")long orgId, @Param("id")long id);

}
