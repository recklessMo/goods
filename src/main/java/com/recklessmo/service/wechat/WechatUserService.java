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
 * 负责处理微信端的用户逻辑
 *
 * Created by hpf on 1/12/17.
 */
@Service
public class WechatUserService {

    @Resource
    private WechatUserDAO wechatUserDAO;

    /*************************微信用户*****************************/
    public List<WechatUser> getRecentUserList(Page page){
        return wechatUserDAO.getListByOrgId(page);
    }

    public int getRecentUserCount(Page page){
        return wechatUserDAO.getCountByOrgId(page);
    }

    public void insertUser(WechatUser user){

    }

    public void releaseUserByOpenId(String openId){

    }

    public void releaseUserBySid(String sid){

    }

}
