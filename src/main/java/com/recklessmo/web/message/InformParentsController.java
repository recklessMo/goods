package com.recklessmo.web.message;

import com.recklessmo.model.message.InformMessage;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.message.InformMessageStoreService;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用于发送通知
 *
 * 可能需要集成发短信服务
 *
 * Created by hpf on 2/9/17.
 */
@Controller
@RequestMapping("/v1/informmessage")
public class InformParentsController {

    @Resource
    private InformMessageStoreService informMessageStoreService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JsonResponse listInformMessage(@RequestBody Page page){
        List<InformMessage> messageList = informMessageStoreService.listInformMessage(page);
        int count = informMessageStoreService.listInformMessageCount(page);
        return new JsonResponse(200, messageList, count);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JsonResponse addInformMessage(@RequestBody InformMessage informMessage){
        informMessageStoreService.addInformMessage(informMessage, true);
        return new JsonResponse(200, null, null);
    }


}
