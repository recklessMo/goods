package com.recklessmo.service.wechat;

import com.recklessmo.constant.WechatConstants;
import com.recklessmo.dao.wechat.WechatMessageDAO;
import com.recklessmo.dao.wechat.WechatUserDAO;
import com.recklessmo.model.wechat.WechatMessage;
import com.recklessmo.model.wechat.WechatUser;
import com.recklessmo.web.webmodel.page.Page;
import com.recklessmo.web.webmodel.page.WechatMsgPage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private WechatMessageDAO wechatMessageDAO;

    /*************************微信消息***********************************/
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
    public boolean sendMessage(WechatMessage wechatMessage) {
        //首先发送消息to 微信
        int status = wechatNetworkService.sendMsgToWechat(wechatMessage.getMessage(), wechatMessage.getOpenId());
        //然后插入数据库中进行记录
        if (status == 200) {
            wechatMessageDAO.insertMessage(wechatMessage);
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
    public boolean sendAutoMessage(String type, long orgId){
        WechatMessage wechatMessage = new WechatMessage();
        wechatMessage.setOrgId(orgId);
        wechatMessage.setCreated(new Date());
        wechatMessage.setMessageType(WechatMessage.MSG_DIRECTION_SEND);
        wechatMessage.setUserId(0);
        wechatMessage.setUserName("系统");
        wechatMessage.setType(WechatMessage.MSG_TYPE_TEXT);
        wechatMessage.setMessage(getMsgByType(type));
        return sendMessage(wechatMessage);
    }

    private String getMsgByType(String type){
        if("subscribe".equals(type)){
            return WechatConstants.BIND_SUCCESS;
        }
        return WechatConstants.WELCOME_DEFAULT;
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
    }

}
