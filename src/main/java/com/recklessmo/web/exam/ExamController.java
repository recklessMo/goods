package com.recklessmo.web.exam;

import com.recklessmo.model.exam.Exam;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.Exam.ExamService;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
@RequestMapping("/v1/exam")
@Controller
public class ExamController {

    @Resource
    private ExamService examService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JsonResponse listExam(@RequestBody Page page){
        List<Exam> examList = examService.listExam(page);
        return new JsonResponse(200, examList, null);
    }



}
