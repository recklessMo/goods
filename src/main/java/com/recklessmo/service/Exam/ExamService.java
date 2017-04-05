package com.recklessmo.service.exam;

import com.recklessmo.dao.exam.ExamDAO;
import com.recklessmo.model.exam.Exam;
import com.recklessmo.web.webmodel.page.ExamListPage;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
@Service
public class ExamService {

    @Resource
    private ExamDAO examDAO;

    public List<Exam> listExam(ExamListPage page){
        return examDAO.listExam(page);
    }

    public int listExamCount(Page page){
        return examDAO.listExamCount(page);
    }

    public void addExam(Exam exam){
        examDAO.addExam(exam);
    }

    public void updateExam(Exam exam){

    }

    public Exam getExamById(long id){
        return examDAO.getExamById(id);
    }

}
