package com.recklessmo.service.wechat;

import com.google.common.base.Functions;
import com.recklessmo.dao.wechat.WechatMessageDAO;
import com.recklessmo.dao.wechat.WechatUserDAO;
import com.recklessmo.model.student.StudentInfo;
import com.recklessmo.model.wechat.WechatMessage;
import com.recklessmo.model.wechat.WechatUser;
import com.recklessmo.service.student.StudentService;
import com.recklessmo.web.webmodel.page.Page;
import com.recklessmo.web.webmodel.page.WechatMsgPage;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * 负责处理微信端的用户逻辑
 *
 * Created by hpf on 1/12/17.
 */
@Service
public class WechatUserService {

    @Resource
    private WechatUserDAO wechatUserDAO;

    @Resource
    private StudentService studentService;

    private void composeWechatUserInfo(long orgId, List<WechatUser> wechatUserList){
        List<String> sidList = wechatUserList.stream().map(o -> o.getSid()).collect(Collectors.toList());
        List<StudentInfo> studentInfoList = studentService.getStudentInfoBySidList(orgId, sidList);
        Map<String, StudentInfo> studentInfoMap = studentInfoList.stream().collect(Collectors.toMap(StudentInfo::getSid, Function.identity()));
        wechatUserList.stream().forEach(wechatUser -> {
            StudentInfo studentInfo = studentInfoMap.get(wechatUser.getSid());
            if(studentInfo != null) {
                wechatUser.setName(studentInfo.getName());
                wechatUser.setGradeId(studentInfo.getGradeId());
                wechatUser.setGradeName(studentInfo.getGradeName());
                wechatUser.setClassId(studentInfo.getClassId());
                wechatUser.setClassName(studentInfo.getClassName());
            }
        });
    }

    /*************************微信用户 前端展示*****************************/
    public List<WechatUser> getRecentUserList(Page page){
        List<WechatUser> wechatUserList = wechatUserDAO.getRecentListByOrgId(page);
        composeWechatUserInfo(page.getOrgId(), wechatUserList);
        return wechatUserList;
    }

    public int getRecentUserCount(Page page){
        return wechatUserDAO.getRecentCountByOrgId(page);
    }

    public List<WechatUser> getAllUserList(Page page){
        List<WechatUser>  wechatUserList = wechatUserDAO.getAllListByOrgId(page);
        composeWechatUserInfo(page.getOrgId(), wechatUserList);
        return wechatUserList;
    }

    public int getAllUserCount(Page page){
        return wechatUserDAO.getAllCountByOrgId(page);
    }


    /*************************微信用户 绑定和解绑定*****************************/
    public void insertUser(WechatUser user){

    }

    public void releaseUserByOpenId(String openId){

    }

    public void releaseUserBySid(String sid){

    }

}
