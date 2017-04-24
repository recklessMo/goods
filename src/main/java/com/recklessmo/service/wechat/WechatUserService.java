package com.recklessmo.service.wechat;

import com.google.common.base.Functions;
import com.recklessmo.dao.wechat.WechatMessageDAO;
import com.recklessmo.dao.wechat.WechatUserDAO;
import com.recklessmo.model.student.StudentGradeInfo;
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
        List<StudentGradeInfo> studentGradeInfoList = studentService.getStudentGradeInfoBySidList(orgId, sidList);
        Map<String, StudentGradeInfo> studentGradeInfoMap = studentGradeInfoList.stream().collect(Collectors.toMap(StudentGradeInfo::getSid, Function.identity()));
        wechatUserList.stream().forEach(wechatUser -> {
            StudentGradeInfo studentGradeInfo = studentGradeInfoMap.get(wechatUser.getSid());
            if(studentGradeInfo != null) {
                wechatUser.setName(studentGradeInfo.getName());
                wechatUser.setGradeId(studentGradeInfo.getGradeId());
                wechatUser.setGradeName(studentGradeInfo.getGradeName());
                wechatUser.setClassId(studentGradeInfo.getClassId());
                wechatUser.setClassName(studentGradeInfo.getClassName());
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
