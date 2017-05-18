package com.recklessmo.web.assignment;

import com.recklessmo.model.assignment.Assignment;
import com.recklessmo.model.exam.Exam;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.assignment.AssignmentService;
import com.recklessmo.service.exam.ExamService;
import com.recklessmo.service.score.ScoreService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.web.webmodel.page.AssignmentListPage;
import com.recklessmo.web.webmodel.page.ExamListPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *  作业设置
 */
@RequestMapping("/v1/assignment")
@Controller
public class AssignmentController {

    @Resource
    private AssignmentService assignmentService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JsonResponse listExam(@RequestBody AssignmentListPage page){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        page.setOrgId(userDetails.getOrgId());
        List<Assignment> assignmentList = assignmentService.listAssignment(page);
        int count = assignmentService.listAssignmentCount(page);
        return new JsonResponse(200, assignmentList, count);
    }


    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JsonResponse addExam(@RequestBody Assignment assignment){
        DefaultUserDetails defaultUserDetails = ContextUtils.getLoginUserDetail();
        assignment.setOrgId(defaultUserDetails.getOrgId());
        assignment.setOpId(defaultUserDetails.getId());
        assignment.setOpName(defaultUserDetails.getName());
        assignment.setCreated(new Date());
        assignmentService.addAssignment(assignment);
        return new JsonResponse(200, null, null);
    }


}
