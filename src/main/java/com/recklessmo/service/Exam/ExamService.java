package com.recklessmo.service.Exam;

import com.recklessmo.dao.exam.ExamDAO;
import com.recklessmo.model.exam.Exam;
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

    public List<Exam> listExam(Page page){
        return examDAO.listExam(page);
    }


}
