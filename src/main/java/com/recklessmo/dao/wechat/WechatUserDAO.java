package com.recklessmo.dao.wechat;

import com.recklessmo.model.wechat.WechatUser;
import com.recklessmo.web.webmodel.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hpf on 1/12/17.
 */
public interface WechatUserDAO {

    //创建新用户
    void insertUser(WechatUser wechatUser);

    //更新最后一条聊天消息
    void updateWechatUserLastMessage(@Param("lastMessage")String lastMessage, @Param("orgId")long orgId, @Param("openId")String openId, @Param("sid")String sid);

    //获取最近咨询的微信用户
    List<WechatUser> getRecentListByOrgId(Page page);
    int getRecentCountByOrgId(Page page);

    //获取所有微信用户
    List<WechatUser> getAllListByOrgId(Page page);
    int getAllCountByOrgId(Page page);


    WechatUser getByOpenId(@Param("openId")String openId);

    void releaseUserByOpenId(@Param("openId")String openId);

}
