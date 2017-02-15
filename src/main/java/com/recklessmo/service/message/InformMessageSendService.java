package com.recklessmo.service.message;

import com.recklessmo.dao.message.InformMessageDAO;
import com.recklessmo.model.message.InformMessage;
import com.recklessmo.model.sms.SmsCode;
import com.recklessmo.service.sms.SmsService;
import com.recklessmo.web.webmodel.page.Page;
import com.sun.tools.classfile.InnerClasses_attribute;
import org.apache.ibatis.annotations.Case;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * created by hpf 02/12/2017
 */
@Service
public class InformMessageSendService {

    @Resource
    private SmsService smsService;


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
        //处理, 获取所有的待发送对象

        //发送短信
        if(informMessage.getSendType() == InformMessage.SEND_TYPE_SMS){

        }else if(informMessage.getSendType() == InformMessage.SEND_TYPE_WECHAT){
            //todo 目前先只支持微信发送



        }else if(informMessage.getSendType() == InformMessage.SEND_TYPE_WECHAT_AND_SMS){

        }else if(informMessage.getSendType() == InformMessage.SEND_TYPE_WECHAT_BEFORE_SMS){

        }


    }




}
