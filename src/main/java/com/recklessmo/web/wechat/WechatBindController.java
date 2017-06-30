package com.recklessmo.web.wechat;

import com.recklessmo.model.assignment.Assignment;
import com.recklessmo.model.score.Score;
import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.setting.Group;
import com.recklessmo.model.sms.SmsCode;
import com.recklessmo.model.student.StudentInfo;
import com.recklessmo.model.system.Org;
import com.recklessmo.model.wechat.WechatUser;
import com.recklessmo.model.wechat.page.WechatIndexModel;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.assignment.AssignmentService;
import com.recklessmo.service.exam.ExamService;
import com.recklessmo.service.score.ScoreAnalyseService;
import com.recklessmo.service.score.ScoreService;
import com.recklessmo.service.setting.GradeSettingService;
import com.recklessmo.service.sms.SmsCodeService;
import com.recklessmo.service.sms.SmsNetworkService;
import com.recklessmo.service.student.StudentService;
import com.recklessmo.service.system.OrgService;
import com.recklessmo.service.wechat.WechatUserService;
import com.recklessmo.util.sms.SmsCodeUtils;
import com.recklessmo.util.wechat.WechatCookieUtils;
import com.recklessmo.web.webmodel.page.AssignmentListPage;
import com.recklessmo.web.webmodel.page.Page;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.annotations.Param;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * 用于处理微信的绑定请求
 *
 */
@Controller
@RequestMapping("/public/wechat")
public class WechatBindController {

    private static final Log LOGGER = LogFactory.getLog(WechatBindController.class);

    @Resource
    private StudentService studentService;

    @Resource
    private SmsCodeService smsCodeService;

    @Resource
    private SmsNetworkService smsNetworkService;

    @Resource
    private WechatUserService wechatUserService;


    /**
     *
     * 获取绑定的学生个人主页
     *
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/sendSmsCode", method = RequestMethod.GET)
    public JsonResponse sendSmsCode(@RequestParam("name")String name, @RequestParam("phone")String phone, HttpServletRequest request, HttpServletResponse response) throws Exception {
        StudentInfo studentInfo = studentService.getStudentInfoByNameAndPhone(name, phone);
        if(studentInfo == null){
            return null;
        }
        //找到student之后开始发送验证码
        String code = SmsCodeUtils.generateCode(4);
        int status = smsNetworkService.sendSmsCode(phone, code);
        if(status == 200) {
            SmsCode smsCode = new SmsCode();
            smsCode.setName(name);
            smsCode.setPhone(phone);
            DateTime dateTime = new DateTime();
            smsCode.setTime(dateTime.toDate());
            Period period = new Period().withMillis((int) SmsCode.DEFAULT_GAP);
            smsCode.setExpire(dateTime.plus(period).toDate());
            smsCode.setCode(code);
            smsCodeService.addSmsCode(smsCode);
        }
        return new JsonResponse(200, null, null);
    }


    @ResponseBody
    @RequestMapping(value = "/selfBind", method = RequestMethod.GET)
    public JsonResponse bindSmsCode(@RequestParam("name")String name, @RequestParam("phone")String phone, @RequestParam("smsCode")String smsCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String openId = WechatCookieUtils.getOpenIdByCookie(request.getCookies());
        if(openId == null){
            openId = "o2mBHwqHpFzTcZXVvAmmBTjazR_k";
        }
        StudentInfo studentInfo = studentService.getStudentInfoByNameAndPhone(name, phone);
        if(studentInfo == null){
            return null;
        }
        SmsCode tempCode = smsCodeService.getSmsCodeByNameAndPhone(name, phone);
        if(tempCode != null && tempCode.getExpire().after(new Date())){
            if(smsCode.equals(tempCode.getCode())){
                //进行绑定
                studentService.updateWechatIdBySid(studentInfo.getOrgId(), studentInfo.getSid(), openId);
                //create User
                wechatUserService.bindUser(studentInfo.getOrgId(), studentInfo.getSid(), openId);
            }else{
                //提示验证码错误
            }
        }else{
            //提示验证码过期
        }
        return new JsonResponse(200, null, null);
    }


}
