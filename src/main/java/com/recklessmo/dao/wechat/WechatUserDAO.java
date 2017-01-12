package com.recklessmo.dao.wechat;

import com.recklessmo.model.wechat.WechatUser;
import com.recklessmo.web.webmodel.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hpf on 1/12/17.
 */
public interface WechatUserDAO {

    //获取微信用户
    List<WechatUser> getListByOrgId(Page page);
    int getCountByOrgId(Page page);

    WechatUser getByOpenId(@Param("openId")String openId);


}
