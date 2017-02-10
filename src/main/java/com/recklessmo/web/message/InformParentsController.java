package com.recklessmo.web.message;

import com.recklessmo.model.exam.Exam;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用于发送通知
 *
 * 可能需要集成发短信服务
 *
 * Created by hpf on 2/9/17.
 */
@Controller
@RequestMapping("/v1/informmessage")
public class InformParentsController {


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
