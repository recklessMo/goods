package com.recklessmo.web.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.recklessmo.constant.WechatConstants;
import com.recklessmo.model.exam.Exam;
import com.recklessmo.model.score.NewScore;
import com.recklessmo.model.score.Score;
import com.recklessmo.model.score.result.ClassTotal;
import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.setting.Group;
import com.recklessmo.model.student.StudentAllInfo;
import com.recklessmo.model.system.Org;
import com.recklessmo.model.wechat.WechatCallbackMsg;
import com.recklessmo.model.wechat.WechatMessage;
import com.recklessmo.model.wechat.WechatTicket;
import com.recklessmo.model.wechat.WechatUser;
import com.recklessmo.model.wechat.page.WechatIndexModel;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.exam.ExamService;
import com.recklessmo.service.score.ScoreAnalyseService;
import com.recklessmo.service.score.ScoreService;
import com.recklessmo.service.setting.GradeSettingService;
import com.recklessmo.service.student.StudentService;
import com.recklessmo.service.system.OrgService;
import com.recklessmo.service.wechat.*;
import com.recklessmo.util.score.ScoreUtils;
import com.recklessmo.util.wechat.WechatCookieUtils;
import com.recklessmo.util.wechat.WechatUtils;
import com.recklessmo.web.webmodel.page.ExamListPage;
import com.recklessmo.web.webmodel.page.Page;
import com.recklessmo.web.webmodel.page.ScoreListPage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.apache.ibatis.annotations.Param;
import org.springframework.expression.spel.ast.OpNE;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 *
 * 用于处理微信的请求
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

//        delay(5000);

        String openId = WechatCookieUtils.getOpenIdByCookie(request.getCookies());
        if(openId == null){
            openId = "o2mBHwqHpFzTcZXVvAmmBTjazR_k";
        }
        StudentAllInfo studentAllInfo = studentService.getStudentInfoByWechatId(openId);
        response.addHeader("Access-Control-Allow-Origin", "*");
        Page page = new Page();
        page.setPage(1);
        page.setCount(100);
        int gradeCount = gradeSettingService.listGradeCount(page);
        Grade grade = gradeSettingService.getSingleGrade(studentAllInfo.getGradeId());
        Optional<Group> groupOptional = grade.getClassList().stream().filter(o->o.getClassId() == studentAllInfo.getClassId()).findAny();
        Group group = null;
        if(groupOptional.isPresent()) {
            group = groupOptional.get();
        }
//        Org org = orgService.getOrg();
        WechatIndexModel wechatIndexModel = new WechatIndexModel();
        wechatIndexModel.setOrgName("湖北省公安县第一中学");
        wechatIndexModel.setOrgAge(3);
        wechatIndexModel.setOrgCount(4);
        wechatIndexModel.setTotalGradeCount(gradeCount);
        wechatIndexModel.setTotalClassCount(gradeCount);
        wechatIndexModel.setGradeClassCount(grade.getClassList().size());
        wechatIndexModel.setGradeName(grade.getGradeName());
        wechatIndexModel.setGradeCharger(grade.getCharger());
        wechatIndexModel.setGradePhone(grade.getPhone());
        wechatIndexModel.setClassTotalCount(45);
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
        StudentAllInfo studentAllInfo = studentService.getStudentInfoByWechatId(openId);
        response.addHeader("Access-Control-Allow-Origin", "*");
        return new JsonResponse(200, studentAllInfo, null);
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
    @RequestMapping(value = "/examList", method = RequestMethod.GET)
    public JsonResponse examList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String openId = WechatCookieUtils.getOpenIdByCookie(request.getCookies());
        if(openId == null){
            openId = "o2mBHwqHpFzTcZXVvAmmBTjazR_k";
        }
        StudentAllInfo studentAllInfo = studentService.getStudentInfoByWechatId(openId);
        response.addHeader("Access-Control-Allow-Origin", "*");

        ExamListPage examListPage = new ExamListPage();
        examListPage.setClassId(studentAllInfo.getClassId());
        List<Exam> examList = examService.listExam(examListPage);
        return new JsonResponse(200, examList, null);
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
    @RequestMapping(value = "/score/my", method = RequestMethod.GET)
    public JsonResponse score(@Param("eid") long eid,  HttpServletRequest request, HttpServletResponse response) throws Exception {
        String openId = WechatCookieUtils.getOpenIdByCookie(request.getCookies());
        if(openId == null){
            openId = "o2mBHwqHpFzTcZXVvAmmBTjazR_k";
        }
        StudentAllInfo studentAllInfo = studentService.getStudentInfoByWechatId(openId);
        response.addHeader("Access-Control-Allow-Origin", "*");

        ScoreListPage scoreListPage = new ScoreListPage();
        scoreListPage.setExamId(eid);
        scoreListPage.setSid(studentAllInfo.getSid());
        scoreListPage.setPage(1);
        scoreListPage.setCount(1);
        List<Score> scores = scoreService.loadScoreList(scoreListPage);
        List<NewScore> newScores = ScoreUtils.changeScoreToNewScore(scores);




        return new JsonResponse(200, newScores.get(0), null);
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
        StudentAllInfo studentAllInfo = studentService.getStudentInfoByWechatId(openId);
        response.addHeader("Access-Control-Allow-Origin", "*");
        ScoreListPage scoreListPage = new ScoreListPage();
        scoreListPage.setExamId(eid);
        scoreListPage.setClassId(studentAllInfo.getClassId());
        scoreListPage.setPage(1);
        scoreListPage.setCount(1000);
        List<Score> scores = scoreService.loadScoreList(scoreListPage);
        List<NewScore> newScores = ScoreUtils.changeScoreToNewScore(scores);
        Object result = scoreAnalyseService.analyseTotal(newScores, 1, 7);
        return new JsonResponse(200, result, null);
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
        StudentAllInfo studentAllInfo = studentService.getStudentInfoByWechatId(openId);
        response.addHeader("Access-Control-Allow-Origin", "*");
        ScoreListPage scoreListPage = new ScoreListPage();
        scoreListPage.setExamId(eid);
//        scoreListPage.setGradeId(studentAllInfo.getGradeId());
        scoreListPage.setPage(1);
        scoreListPage.setCount(10000);
        List<Score> scores = scoreService.loadScoreList(scoreListPage);
        List<NewScore> newScores = ScoreUtils.changeScoreToNewScore(scores);
        Object result = scoreAnalyseService.analyseTotal(newScores, 1, 7);
        Collection<ClassTotal> classTotalList = (Collection<ClassTotal>)result;
        Optional<ClassTotal> classTotal = classTotalList.stream().filter(o->o.getClassId() == -3).findAny();
        if(classTotal.isPresent()){
            return new JsonResponse(200, classTotal.get(), null);
        }
        return new JsonResponse(200, null, null);
    }


    private void delay(long time) throws  Exception{
        Thread.sleep(time);
    }

}
