package com.recklessmo.web.wechat;

import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.model.wechat.WechatMessage;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.wechat.WechatMessageService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.web.webmodel.page.WechatMsgPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by hpf on 8/29/16.
 *
 * 用于处理crm端请求
 *
 */
@RequestMapping("/v1/wechat/message")
@Controller
public class WechatMessageController {

    @Resource
    private WechatMessageService wechatMessageService;


    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JsonResponse listMessage(@RequestBody WechatMsgPage page){
        List<WechatMessage> wechatMessages = wechatMessageService.getMessageList(page);
        int totalCount = wechatMessageService.getMessageListCount(page);
        return new JsonResponse(200, wechatMessages, totalCount);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JsonResponse addMessage(@RequestBody WechatMessage wechatMessage){
        DefaultUserDetails defaultUserDetails = ContextUtils.getLoginUserDetail();
        wechatMessage.setOrgId(0);
        wechatMessage.setCreated(new Date());
        wechatMessage.setMessageType(1);
        wechatMessage.setUserId(defaultUserDetails.getId());
        wechatMessage.setUserName("hpf");
        wechatMessage.setType(1);
        boolean result = wechatMessageService.sendMessage(wechatMessage);
        return new JsonResponse(200, result, null);
    }





}
