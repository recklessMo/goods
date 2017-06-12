package com.recklessmo.web.exam;

import com.recklessmo.model.exam.Exam;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.exam.ExamService;
import com.recklessmo.service.score.ScoreService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.web.webmodel.page.ExamListPage;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
@RequestMapping("/v1/exam")
@Controller
public class ExamController {

    @Resource
    private ExamService examService;

    @Resource
    private ScoreService scoreService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JsonResponse listExam(@RequestBody ExamListPage page){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        page.setOrgId(userDetails.getOrgId());
        List<Exam> examList = examService.listExam(page);
        int count = examService.listExamCount(page);
        return new JsonResponse(200, examList, count);
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public JsonResponse searchExam(@RequestParam("str") String searchStr){
        ExamListPage page = new ExamListPage();
        page.setSearchStr(searchStr);
        List<Exam> examList = examService.listExam(page);
        int count = examService.listExamCount(page);
        return new JsonResponse(200, examList, count);
    }


    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JsonResponse addExam(@RequestBody Exam exam){
        DefaultUserDetails defaultUserDetails = ContextUtils.getLoginUserDetail();
        if(exam.getExamId() == 0) {
            //新加
            exam.setOrgId(defaultUserDetails.getOrgId());
            exam.setUploadStatus(Exam.EXAM_UN_UPLOADED);
            exam.getCourseList().sort((a, b) -> a.compareTo(b));
            exam.setOpId(defaultUserDetails.getId());
            exam.setOpName(defaultUserDetails.getName());
            examService.addExam(exam);
        }
        return new JsonResponse(200, null, null);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public JsonResponse deleteExam(@RequestBody long id){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        Exam exam = examService.getExamById(userDetails.getOrgId(), id);
        if(exam == null) {
            return new JsonResponse(300, "考试不存在！", null);
        }
        if(exam.getOpId() == userDetails.getId()) {
            examService.deleteExam(userDetails.getOrgId(), id);
            scoreService.removeScoreList(userDetails.getOrgId(), id);
        }else{
            return new JsonResponse(300, "只能删除自己创建的考试！", null);
        }
        return new JsonResponse(200, null, null);
    }


}
