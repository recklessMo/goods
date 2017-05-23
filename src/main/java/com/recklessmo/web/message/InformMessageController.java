package com.recklessmo.web.message;

import com.recklessmo.model.message.InformMessage;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.message.InformMessageService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 用于发送通知
 *
 * 可能需要集成发短信服务
 *
 * Created by hpf on 2/9/17.
 */
@Controller
@RequestMapping("/v1/informMessage")
public class InformMessageController {

    @Resource
    private InformMessageService informMessageService;

    /**
     * list 消息
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JsonResponse listInformMessage(@RequestBody Page page){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        List<InformMessage> messageList = informMessageService.getInformMessageList(page);
        int count = informMessageService.getInformMessageListCount(page);
        return new JsonResponse(200, messageList, count);
    }


    /**
     * 新增消息
     * @param informMessage
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JsonResponse addInformMessage(@RequestBody InformMessage informMessage){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        informMessage.setOrgId(userDetails.getOrgId());
        informMessage.setCreated(new Date());
        informMessage.setOpId(userDetails.getId());
        informMessage.setOpName(userDetails.getName());
        informMessageService.addInformMessage(informMessage);
        return new JsonResponse(200, null, null);
    }


}
