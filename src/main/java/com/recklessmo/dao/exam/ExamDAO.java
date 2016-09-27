package com.recklessmo.dao.exam;

import com.recklessmo.model.exam.Exam;
import com.recklessmo.web.webmodel.page.Page;

import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
public interface ExamDAO {

    List<Exam> listExam(Page page);

}
