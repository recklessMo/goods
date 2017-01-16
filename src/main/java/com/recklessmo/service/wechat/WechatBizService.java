package com.recklessmo.service.wechat;

import com.recklessmo.dao.wechat.WechatUserDAO;
import com.recklessmo.model.wechat.WechatUser;
import com.recklessmo.web.webmodel.page.Page;
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
public class WechatBizService {

    @Resource
    private WechatUserDAO wechatUserDAO;

    public List<WechatUser> getRecentUserList(Page page){
        return wechatUserDAO.getListByOrgId(page);
    }

    public int getRecentUserCount(Page page){
        return wechatUserDAO.getCountByOrgId(page);
    }

    /**
     *
     * 主页内容
     *
     * @param model
     * @return
     */
    public String getIndexPage(Model model){

        return "";
    }

}
