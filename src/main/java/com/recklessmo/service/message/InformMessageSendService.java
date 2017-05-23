package com.recklessmo.service.message;

import com.recklessmo.model.message.InformMessage;
import com.recklessmo.service.sms.SmsNetworkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * created by hpf 02/12/2017
 */
@Service
public class InformMessageSendService {

    @Resource
    private SmsNetworkService smsNetworkService;


    /**
     *
     * 根据type进行发送,
     * 1. 微信发送模板消息
     * 2. 短信发送短信消息
     *
     * 同时需要记录发送的状态, 发送成功或者失败, 以及具体原因.
     *
     * @param informMessage
     */
    public void sendMessage(InformMessage informMessage){


    }




}
