package com.recklessmo.web.questionset;

/**
 * Created by yyq on 17/6/30.
 */

import com.recklessmo.model.score.Score;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.model.student.StudentInfo;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.questionset.QuestionsetService;
import com.recklessmo.service.score.ScoreService;
import com.recklessmo.service.student.StudentService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.web.webmodel.page.StudentPage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;


@Controller
public class QuestionsetController {

    @Resource
    private QuestionsetService questionsetService;

    @Resource
    private ScoreService scoreService;

    private static final Log LOGGER = LogFactory.getLog(com.recklessmo.web.questionset.QuestionsetController.class);

    /**
     *
     * 单个录入学生信息
     *
     * @param studentInfo
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "/v1/questionset/add", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse addStudentInfo(@RequestBody StudentInfo studentInfo){
        //TODO 可能需要做一些字段的校验,可以考虑写个annotation来进行校验
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        studentInfo.setOrgId(userDetails.getOrgId());
        try {
            questionsetService.insertStudentAddInfo(studentInfo);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return new JsonResponse(301, "修改数据失败，学号重复！", null);
        }
        return new JsonResponse(200, null, null);
    }

    /**
     * list
     *
     * 学生信息查询
     *
     * @param page
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "/v1/questionset/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse list(@RequestBody StudentPage page){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        page.setOrgId(userDetails.getOrgId());
        int cnt = questionsetService.getStudentInfoTotalCount(page);
        List<StudentInfo> infoList = questionsetService.getStudentInfo(page);
        return new JsonResponse(200, infoList, cnt);
    }
}

