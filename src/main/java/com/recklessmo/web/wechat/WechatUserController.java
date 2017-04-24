package com.recklessmo.web.wechat;

import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.model.wechat.WechatTicket;
import com.recklessmo.model.wechat.WechatUser;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.wechat.WechatMessageService;
import com.recklessmo.service.wechat.WechatTicketService;
import com.recklessmo.service.wechat.WechatUserService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hpf on 8/29/16.
 *
 * 用于处理crm端请求
 *
 */
@RequestMapping("/v1/wechat/user")
@Controller
public class WechatUserController {

    @Resource
    private WechatUserService wechatUserService;

    @Resource
    private WechatTicketService wechatTicketService;


    /**
     *
     * 生成二维码绑定用户
     *
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/bind", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse bindUser(@RequestParam("sid") String sid){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        String ticket = wechatTicketService.createTicket(userDetails.getOrgId(), sid, WechatTicket.SCENE_ID);
        String url = String.format("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=%s", ticket);
        return new JsonResponse(200, url, null);
    }

    /**
     * 取消绑定
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/release", method = RequestMethod.POST)
    public JsonResponse releaseUser(@RequestBody long wechatUserId){
        return new JsonResponse(200, null, null);
    }




    /****************微信用户********************/
    @ResponseBody
    @RequestMapping(value = "/recent/list", method = RequestMethod.POST)
    public JsonResponse listRecentUser(@RequestBody Page page){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        page.setOrgId(userDetails.getOrgId());
        List<WechatUser> wechatUsers = wechatUserService.getRecentUserList(page);
        int totalCount = wechatUserService.getRecentUserCount(page);
        return new JsonResponse(200, wechatUsers, totalCount);
    }


    @ResponseBody
    @RequestMapping(value = "/all/list", method = RequestMethod.POST)
    public JsonResponse listAllUser(@RequestBody Page page){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        page.setOrgId(userDetails.getOrgId());
        List<WechatUser> wechatUsers = wechatUserService.getAllUserList(page);
        int totalCount = wechatUserService.getAllUserCount(page);
        return new JsonResponse(200, wechatUsers, totalCount);
    }

}
