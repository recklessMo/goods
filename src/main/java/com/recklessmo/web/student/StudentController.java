package com.recklessmo.web.student;

import com.recklessmo.model.score.Score;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.model.student.StudentInfo;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.score.ScoreService;
import com.recklessmo.service.student.StudentService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.web.webmodel.page.StudentPage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by hpf on 7/23/16.
 *
 * 主要用于保存学生的信息
 */
@Controller
public class StudentController {

    @Resource
    private StudentService studentService;

    @Resource
    private ScoreService scoreService;

    private static final Log LOGGER = LogFactory.getLog(StudentController.class);

    /**
     *
     * 单个录入学生信息
     *
     * @param studentInfo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/v1/student/add", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse addStudentInfo(@RequestBody StudentInfo studentInfo){
        //TODO 可能需要做一些字段的校验,可以考虑写个annotation来进行校验
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        studentInfo.setOrgId(userDetails.getOrgId());
        try {
            studentService.insertStudentAddInfo(studentInfo);
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
    @RequestMapping(value = "/v1/student/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse list(@RequestBody StudentPage page){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        page.setOrgId(userDetails.getOrgId());
        int cnt = studentService.getStudentInfoTotalCount(page);
        List<StudentInfo> infoList = studentService.getStudentInfo(page);
        return new JsonResponse(200, infoList, cnt);
    }

    /**
     *
     * 通过sid获取学生全部信息
     * @param sid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/v1/student/get", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse getBySid(@RequestBody String sid){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        StudentInfo studentInfo = studentService.getStudentInfoBySid(userDetails.getOrgId(), sid);
        return new JsonResponse(200, studentInfo, null);
    }

    /**
     *
     * 用于通过考试来搜索学生
     *
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/v1/student/searchByExam", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse searchByExam(@RequestBody StudentPage page){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        page.setOrgId(userDetails.getOrgId());
        List<StudentInfo> studentInfoList = studentService.searchStudentByExam(page);
        return new JsonResponse(200, studentInfoList, studentInfoList.size());
    }

    /**
     *
     * 更新信息
     *
     * @param studentAllInfo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/v1/student/save", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse saveStudentAllInfo(@RequestBody StudentInfo studentInfo){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        studentInfo.setOrgId(userDetails.getOrgId());
        try {
            studentService.updateStudentInfo(studentInfo);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return new JsonResponse(301, "修改数据失败，学号重复！", null);
        }
        return new JsonResponse(200, null, null);
    }


    /**
     *
     * 获取学生的成绩列表
     *
     * @param sid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/v1/student/scoreList", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse getScoreList(@RequestBody String sid){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        List<Score> scoreList = scoreService.getScoreListBySid(userDetails.getOrgId(), sid);
        return new JsonResponse(200, scoreList, scoreList.size());
    }


}
