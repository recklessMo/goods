package com.recklessmo.dao.wechat;

import com.recklessmo.model.wechat.WechatTicket;
import com.recklessmo.model.wechat.WechatUser;
import com.recklessmo.web.webmodel.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hpf on 1/12/17.
 */
public interface WechatTicketDAO {

    void insertTicket(WechatTicket wechatTicket);
    WechatTicket getTicketByTicket(@Param("ticket")String ticket);

}
