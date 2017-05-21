package com.recklessmo.service.wechat;

import com.alibaba.fastjson.JSONObject;
import com.recklessmo.constant.WechatConstants;
import com.recklessmo.dao.wechat.WechatMessageDAO;
import com.recklessmo.dao.wechat.WechatUserDAO;
import com.recklessmo.model.student.StudentInfo;
import com.recklessmo.model.wechat.WechatMessage;
import com.recklessmo.model.wechat.WechatTemplateMessage;
import com.recklessmo.model.wechat.WechatUser;
import com.recklessmo.model.wechat.page.GradePair;
import com.recklessmo.model.wechat.page.WechatTemplateMsgModel;
import com.recklessmo.service.student.StudentService;
import com.recklessmo.web.webmodel.page.Page;
import com.recklessmo.web.webmodel.page.WechatMsgPage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * 负责处理微信端的页面逻辑
 *
 * Created by hpf on 1/12/17.
 */
@Service
public class WechatMessageService {

    @Resource
    private WechatNetworkService wechatNetworkService;

    @Resource
    private WechatUserService wechatUserService;

    @Resource
    private StudentService studentService;

    @Resource
    private WechatMessageDAO wechatMessageDAO;

    /*************************微信消息***********************************/

    /**
     *
     *  获取聊天消息列表
     *
     * @param page
     * @return
     */
    public List<WechatMessage> getMessageList(WechatMsgPage page){
        return wechatMessageDAO.getListByOpenId(page);
    }

    public int getMessageListCount(WechatMsgPage page){
        return wechatMessageDAO.getCountByOpenId(page);
    }

    /**
     * 发送消息
     * @param wechatMessage
     * @return
     */
    public boolean sendMessage(WechatMessage wechatMessage, boolean needSave) {
        //首先发送消息to 微信
        int status = wechatNetworkService.sendMsgToWechat(wechatMessage.getMessage(), wechatMessage.getOpenId());
        //然后插入数据库中进行记录
        if (status == 200) {
            if(needSave) {
                wechatMessageDAO.insertMessage(wechatMessage);
                wechatUserService.updateWechatUserLastMessage(wechatMessage.getMessage(), wechatMessage.getOrgId(), wechatMessage.getOpenId(), wechatMessage.getSid());
            }
            return true;
        }
        return false;
    }

    /**
     *
     * 发送自动回复消息
     *
     * @param wechatMessage
     * @return
     */
    public boolean sendAutoMessage(String type, long orgId, String openId, String sid){
        WechatMessage wechatMessage = new WechatMessage();
        wechatMessage.setOrgId(orgId);
        wechatMessage.setOpenId(openId);
        wechatMessage.setSid(sid);
        wechatMessage.setCreated(new Date());
        wechatMessage.setMessageType(WechatMessage.MSG_DIRECTION_SEND);
        wechatMessage.setUserId(0);
        wechatMessage.setUserName("系统");
        wechatMessage.setType(WechatMessage.MSG_TYPE_TEXT);
        wechatMessage.setMessage(getMsgByType(type));
        return sendMessage(wechatMessage, true);
    }

    private String getMsgByType(String type){
        if("subscribe".equals(type)){
            return WechatConstants.BIND_SUCCESS;
        }
        return WechatConstants.WELCOME_DEFAULT;
    }

    /**
     *
     * 未关注的时候发送一条消息
     *
     * @param openId
     * @return
     */
    public boolean sendUnsubscribeMessage(String openId, String message){
        WechatMessage wechatMessage = new WechatMessage();
        wechatMessage.setOpenId(openId);
        wechatMessage.setMessage(message);
        return sendMessage(wechatMessage, false);
    }

    /**
     *
     * 发送模板消息
     *
     * @param orgId
     * @param wechatTemplateMsgMode
     * @return
     */
    public boolean sendTemplateMessage(long orgId, WechatTemplateMsgModel wechatTemplateMsgModel){
        List<GradePair> gradePairList = wechatTemplateMsgModel.getGradePairList();
        List<String> sidList = wechatTemplateMsgModel.getSidList();
        Set<String> sidSet = new HashSet<>();
        sidSet.addAll(sidList);
        gradePairList.stream().forEach(gradePair -> {
            List<StudentInfo> studentInfoList = studentService.getStudentListByGradeIdAndClassId(orgId, gradePair.getGradeId(), gradePair.getClassId());
            List<String> sidTempList = studentInfoList.stream().map(o -> o.getSid()).collect(Collectors.toList());
            sidSet.addAll(sidTempList);
        });

        if(wechatTemplateMsgModel.getType() == WechatTemplateMessage.TYPE_ASSIGNMENTNOTICE){

        }
        return true;
    }

    /**
     *
     * 发送作业提醒模板消息
     *
     * @param first
     * @param name
     * @param subject
     * @param content
     * @param remark
     * @return
     */
    private boolean sendAssignmentTemplateMessage(long orgId, String openId, String sid, String first, String name, String subject, String content, String remark){
        WechatTemplateMessage wechatTemplateMessage = new WechatTemplateMessage();
        wechatTemplateMessage.setOpenId(openId);
        wechatTemplateMessage.setOrgId(orgId);
        wechatTemplateMessage.setSid(sid);
        wechatTemplateMessage.setTemplateId(WechatTemplateMessage.ASSIGNMENTNOTICE);
        wechatTemplateMessage.setUrl("www.baidu.com");
        JSONObject obj = new JSONObject();
        obj.put("first", getJsonObject(first));
        obj.put("name", getJsonObject(name));
        obj.put("subject", getJsonObject(subject));
        obj.put("content", getJsonObject(content));
        obj.put("remark", getJsonObject(remark));
        wechatTemplateMessage.setData(obj);
        int status = wechatNetworkService.sendTemplateMsgToWechat(wechatTemplateMessage);
        if(status == 200){
            //记录消息内容到wechatMessage里面

        }
        return false;
    }

    private JSONObject getJsonObject(String obj){
        JSONObject temp = new JSONObject();
        temp.put("value", obj);
        return temp;
    }

    /**
     *
     * 收到消息
     *
     * @param wechatMessage
     */
    public void receiveMessage(WechatMessage wechatMessage) {
        //插入数据库中进行记录
        wechatMessageDAO.insertMessage(wechatMessage);
        wechatUserService.updateWechatUserLastMessage(wechatMessage.getMessage(), wechatMessage.getOrgId(), wechatMessage.getOpenId(), wechatMessage.getSid());
    }

}
