package com.recklessmo.service.wechat;

import com.recklessmo.dao.wechat.WechatTicketDAO;
import com.recklessmo.dao.wechat.WechatUserDAO;
import com.recklessmo.model.wechat.WechatTicket;
import com.recklessmo.model.wechat.WechatUser;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *
 * 生成用于绑定的二维码
 *
 */
@Service
public class WechatTicketService {

    @Resource
    private WechatTicketDAO wechatTicketDAO;

    @Resource
    private WechatNetworkService wechatNetworkService;


    /**
     *
     * 生成ticket
     *
     * @param orgId
     * @param sid
     * @param senceId 场景ID, 用于判断是从哪里进行的绑定, 可以方便以后进行界面优化
     */
    public String createTicket(long orgId, String sid, int sceneId){
        WechatTicket wechatTicket = new WechatTicket();
        wechatTicket.setOrgId(orgId);
        wechatTicket.setCreated(new Date());
        wechatTicket.setSid(sid);
        String ticket = wechatNetworkService.getWechatTicket(sceneId);
        wechatTicket.setTicket(ticket);
        wechatTicketDAO.insertTicket(wechatTicket);
        return ticket;
    }


    /**
     *
     * 根据ticket获取wechatticket
     *
     * @param ticket
     * @return
     */
    public WechatTicket getTicketByTicket(String ticket){
        return wechatTicketDAO.getTicketByTicket(ticket);
    }


}
