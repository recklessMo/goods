package com.recklessmo.web.exam;

import com.recklessmo.model.exam.Exam;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.exam.ExamService;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        int count = examService.listExamCount(page);
        return new JsonResponse(200, examList, count);
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public JsonResponse searchExam(@RequestParam("str") String searchStr){
        Page page = new Page();
        page.setSearchStr(searchStr);
        List<Exam> examList = examService.listExam(page);
        int count = examService.listExamCount(page);
        return new JsonResponse(200, examList, count);
    }


    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JsonResponse addExam(@RequestBody Exam exam){


        return new JsonResponse(200, null, null);
    }


}
