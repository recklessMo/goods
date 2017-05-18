package com.recklessmo.web.wechat;

import com.recklessmo.model.assignment.Assignment;
import com.recklessmo.model.exam.Exam;
import com.recklessmo.model.score.Score;
import com.recklessmo.model.score.result.total.ClassTotal;
import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.setting.Group;
import com.recklessmo.model.student.StudentInfo;
import com.recklessmo.model.system.Org;
import com.recklessmo.model.wechat.page.WechatIndexModel;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.assignment.AssignmentService;
import com.recklessmo.service.exam.ExamService;
import com.recklessmo.service.score.ScoreAnalyseService;
import com.recklessmo.service.score.ScoreService;
import com.recklessmo.service.setting.GradeSettingService;
import com.recklessmo.service.student.StudentService;
import com.recklessmo.service.system.OrgService;
import com.recklessmo.util.score.ScoreUtils;
import com.recklessmo.util.wechat.WechatCookieUtils;
import com.recklessmo.web.webmodel.page.AssignmentListPage;
import com.recklessmo.web.webmodel.page.ExamListPage;
import com.recklessmo.web.webmodel.page.Page;
import com.recklessmo.web.webmodel.page.ScoreListPage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 *
 * 用于处理微信的直接请求(我们请求分为直接请求和回调请求)
 *
 */
@Controller
@RequestMapping("/public/wechat")
public class WechatRequestController {

    private static final Log LOGGER = LogFactory.getLog(WechatRequestController.class);

    @Resource
    private StudentService studentService;

    @Resource
    private GradeSettingService gradeSettingService;

    @Resource
    private AssignmentService assignmentService;

    @Resource
    private OrgService orgService;

    @Resource
    private ScoreService scoreService;

    @Resource
    private ExamService examService;

    @Resource
    private ScoreAnalyseService scoreAnalyseService;

    /**
     *
     * 获取绑定的学生信息
     *
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public JsonResponse bindInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String openId = WechatCookieUtils.getOpenIdByCookie(request.getCookies());
        if(openId == null){
            openId = "o2mBHwqHpFzTcZXVvAmmBTjazR_k";
        }
        StudentInfo studentInfo = studentService.getStudentInfoByWechatId(openId);
        response.addHeader("Access-Control-Allow-Origin", "*");
        Page page = new Page();
        List<Grade> gradeList = gradeSettingService.listAllGrade(studentInfo.getOrgId());
        Optional<Grade> gradeOptional = gradeList.stream().filter(o->o.getGradeId() == studentInfo.getGradeId()).findAny();
        Grade grade = null;
        if(gradeOptional.isPresent()){
            grade = gradeOptional.get();
        }
        Optional<Group> groupOptional = grade.getClassList().stream().filter(o->o.getClassId() == studentInfo.getClassId()).findAny();
        Group group = null;
        if(groupOptional.isPresent()) {
            group = groupOptional.get();
        }
        int totalGradeCount = gradeList.size();
        int totalClassCount = gradeList.stream().mapToInt(o->o.getClassList().size()).sum();
        long orgId = studentInfo.getOrgId();
        Org org = orgService.getOrg(orgId);
        WechatIndexModel wechatIndexModel = new WechatIndexModel();
        wechatIndexModel.setOrgName(org.getOrgName());
        wechatIndexModel.setPrincipal(org.getAdminName());
        wechatIndexModel.setCreated(org.getCreated());
        wechatIndexModel.setTotalGradeCount(totalGradeCount);
        wechatIndexModel.setTotalClassCount(totalClassCount);
        wechatIndexModel.setGradeClassCount(grade.getClassList().size());
        wechatIndexModel.setGradeName(grade.getGradeName());
        wechatIndexModel.setGradeCharger(grade.getCharger());
        wechatIndexModel.setGradePhone(grade.getPhone());
        wechatIndexModel.setClassTotalCount(studentService.getStudentListCountByGradeIdAndClassId(studentInfo.getOrgId(), studentInfo.getGradeId(), studentInfo.getClassId()));
        wechatIndexModel.setClassName(group.getClassName());
        wechatIndexModel.setClassCharger(group.getCharger());
        wechatIndexModel.setClassPhone(group.getPhone());
        return new JsonResponse(200, wechatIndexModel, null);
    }

    /**
     *
     * 获取基本info
     *
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public JsonResponse baseInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String openId = WechatCookieUtils.getOpenIdByCookie(request.getCookies());
        if(openId == null){
            openId = "o2mBHwqHpFzTcZXVvAmmBTjazR_k";
        }
        StudentInfo studentInfo = studentService.getStudentInfoByWechatId(openId);
        response.addHeader("Access-Control-Allow-Origin", "*");
        return new JsonResponse(200, studentInfo, null);
    }


    /**
     *
     * 获取考试列表
     *
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/examList", method = RequestMethod.GET)
    public JsonResponse examList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String openId = WechatCookieUtils.getOpenIdByCookie(request.getCookies());
        if(openId == null){
            openId = "o2mBHwqHpFzTcZXVvAmmBTjazR_k";
        }
        StudentInfo studentInfo = studentService.getStudentInfoByWechatId(openId);
        response.addHeader("Access-Control-Allow-Origin", "*");
        //查询考试列表
        List<Score> scoreList = scoreService.getScoreListBySid(studentInfo.getOrgId(), studentInfo.getSid());
        scoreList.sort((a, b)->{
            return b.getExamTime().compareTo(a.getExamTime());
        });
        return new JsonResponse(200, scoreList, null);
    }

    @ResponseBody
    @RequestMapping(value = "/assignmentList", method = RequestMethod.GET)
    public JsonResponse assignmentList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String openId = WechatCookieUtils.getOpenIdByCookie(request.getCookies());
        if(openId == null){
            openId = "o2mBHwqHpFzTcZXVvAmmBTjazR_k";
        }
        StudentInfo studentInfo = studentService.getStudentInfoByWechatId(openId);
        response.addHeader("Access-Control-Allow-Origin", "*");
        //查询考试列表
        AssignmentListPage page = new AssignmentListPage();
        page.setOrgId(studentInfo.getOrgId());
        page.setGradeId(studentInfo.getGradeId());
        page.setClassId(studentInfo.getClassId());
        List<Assignment> assignmentList = assignmentService.listAssignment(page);
        return new JsonResponse(200, assignmentList, null);
    }


    /**
     *
     * 获取成绩单
     *
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/score/my", method = RequestMethod.GET)
    public JsonResponse score(@Param("eid") long eid,  HttpServletRequest request, HttpServletResponse response) throws Exception {
        String openId = WechatCookieUtils.getOpenIdByCookie(request.getCookies());
        if(openId == null){
            openId = "o2mBHwqHpFzTcZXVvAmmBTjazR_k";
        }
        StudentInfo studentInfo = studentService.getStudentInfoByWechatId(openId);
        response.addHeader("Access-Control-Allow-Origin", "*");

        Score score = scoreService.getScoreByExamIdAndSid(eid, studentInfo.getSid());
//        ScoreListPage scoreListPage = new ScoreListPage();
//        scoreListPage.setExamId(eid);
//        scoreListPage.setSid(studentAllInfo.getSid());
//        scoreListPage.setPage(1);
//        scoreListPage.setCount(1);
//        List<Score> scores = scoreService.loadScoreList(scoreListPage);
//        List<NewScore> newScores = ScoreUtils.changeScoreToNewScore(scores);
//        return new JsonResponse(200, newScores.get(0), null);
        return new JsonResponse(200, score, null);
    }

    /**
     *
     * 获取成绩单
     *
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/assignment", method = RequestMethod.GET)
    public JsonResponse assignment(@Param("id") long id,  HttpServletRequest request, HttpServletResponse response) throws Exception {
        String openId = WechatCookieUtils.getOpenIdByCookie(request.getCookies());
        if(openId == null){
            openId = "o2mBHwqHpFzTcZXVvAmmBTjazR_k";
        }
        StudentInfo studentInfo = studentService.getStudentInfoByWechatId(openId);
        response.addHeader("Access-Control-Allow-Origin", "*");
        Assignment assignment = assignmentService.getAssignment(studentInfo.getOrgId(), id);
        return new JsonResponse(200, assignment, null);
    }

    /**
     *
     * 获取整体班级分析结果
     *
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/score/totalClass", method = RequestMethod.GET)
    public JsonResponse scoreTotalClass(@Param("eid") long eid,  HttpServletRequest request, HttpServletResponse response) throws Exception {
        String openId = WechatCookieUtils.getOpenIdByCookie(request.getCookies());
        if(openId == null){
            openId = "o2mBHwqHpFzTcZXVvAmmBTjazR_k";
        }
        StudentInfo studentInfo = studentService.getStudentInfoByWechatId(openId);
        response.addHeader("Access-Control-Allow-Origin", "*");
        ScoreListPage scoreListPage = new ScoreListPage();
        scoreListPage.setExamId(eid);
        scoreListPage.setPage(1);
        scoreListPage.setCount(1000);
//        List<Score> scores = scoreService.loadScoreList(scoreListPage);
//        List<NewScore> newScores = ScoreUtils.changeScoreToNewScore(scores);
//        Object result = scoreAnalyseService.analyseTotal(newScores, 1, 7);
//        return new JsonResponse(200, result, null);
        return null;
    }


    /**
     *
     * 获取整体年级分析结果
     *
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/score/totalGrade", method = RequestMethod.GET)
    public JsonResponse scoreTotalGrade(@Param("eid") long eid,  HttpServletRequest request, HttpServletResponse response) throws Exception {
        String openId = WechatCookieUtils.getOpenIdByCookie(request.getCookies());
        if(openId == null){
            openId = "o2mBHwqHpFzTcZXVvAmmBTjazR_k";
        }
        StudentInfo studentInfo = studentService.getStudentInfoByWechatId(openId);
        response.addHeader("Access-Control-Allow-Origin", "*");
//        ScoreListPage scoreListPage = new ScoreListPage();
//        scoreListPage.setExamId(eid);
//        scoreListPage.setPage(1);
//        scoreListPage.setCount(10000);
//        List<Score> scores = scoreService.loadScoreList(scoreListPage);
//        List<NewScore> newScores = ScoreUtils.changeScoreToNewScore(scores);
//        Object result = scoreAnalyseService.analyseTotal(newScores, 1, 7);
//        Collection<ClassTotal> classTotalList = (Collection<ClassTotal>)result;
//        Optional<ClassTotal> classTotal = classTotalList.stream().filter(o->o.getClassId() == -3).findAny();
//        if(classTotal.isPresent()){
//            return new JsonResponse(200, classTotal.get(), null);
//        }
//        return new JsonResponse(200, null, null);
        return null;
    }


    private void delay(long time) throws  Exception{
        Thread.sleep(time);
    }




}
