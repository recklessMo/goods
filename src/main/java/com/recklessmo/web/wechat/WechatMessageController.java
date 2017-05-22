package com.recklessmo.web.wechat;

import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.model.wechat.WechatMessage;
import com.recklessmo.model.wechat.WechatTemplateMessage;
import com.recklessmo.model.wechat.page.WechatTemplateMsgModel;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.wechat.WechatMessageService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.web.webmodel.page.WechatMsgPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        page.setOrgId(userDetails.getOrgId());
        List<WechatMessage> wechatMessages = wechatMessageService.getMessageList(page);
        int totalCount = wechatMessageService.getMessageListCount(page);
        return new JsonResponse(200, wechatMessages, totalCount);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JsonResponse addMessage(@RequestBody WechatMessage wechatMessage){
        DefaultUserDetails defaultUserDetails = ContextUtils.getLoginUserDetail();
        wechatMessage.setOrgId(defaultUserDetails.getOrgId());
        wechatMessage.setCreated(new Date());
        wechatMessage.setMessageType(WechatMessage.MSG_DIRECTION_SEND);
        wechatMessage.setUserId(defaultUserDetails.getId());
        wechatMessage.setUserName(defaultUserDetails.getUsername());
        wechatMessage.setType(WechatMessage.MSG_TYPE_TEXT);
        boolean result = wechatMessageService.sendMessage(wechatMessage, true);
        return new JsonResponse(200, result, null);
    }

//    @ResponseBody
//    @RequestMapping(value = "/addTemplateMsg", method = RequestMethod.POST)
//    public JsonResponse addTemplateMsg(@RequestBody WechatTemplateMsgModel wechatTemplateMsgModel){
//        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
//        wechatMessageService.sendTemplateMessage(userDetails.getOrgId(), wechatTemplateMsgModel);
//        return new JsonResponse(200, null, null);
//    }

}
