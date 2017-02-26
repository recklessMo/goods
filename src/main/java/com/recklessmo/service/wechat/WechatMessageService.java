package com.recklessmo.service.wechat;

import com.recklessmo.dao.wechat.WechatMessageDAO;
import com.recklessmo.dao.wechat.WechatUserDAO;
import com.recklessmo.model.wechat.WechatMessage;
import com.recklessmo.model.wechat.WechatUser;
import com.recklessmo.web.webmodel.page.Page;
import com.recklessmo.web.webmodel.page.WechatMsgPage;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.List;

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
        return wechatMessageDAO.getListByOrgIdAndOpenId(page);
    }

    public int getMessageListCount(WechatMsgPage page){
        return wechatMessageDAO.getCountByOrgIdAndOpenId(page);
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

    public void receiveMessage(WechatMessage wechatMessage) {
        //插入数据库中进行记录
        wechatMessageDAO.insertMessage(wechatMessage);
    }

}
