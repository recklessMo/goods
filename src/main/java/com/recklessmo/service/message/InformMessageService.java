package com.recklessmo.service.message;

import com.recklessmo.dao.message.InformMessageDAO;
import com.recklessmo.model.message.InformMessage;
import com.recklessmo.service.sms.SmsNetworkService;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * created by hpf 02/12/2017
 */
@Service
public class InformMessageService {

    @Resource
    private InformMessageDAO informMessageDAO;

    public List<InformMessage> getInformMessageList(Page page){
        return informMessageDAO.getInformMessageList(page);
    }

    public int getInformMessageListCount(Page page){
        return informMessageDAO.getInformMessageCount(page);
    }

    public void addInformMessage(InformMessage informMessage){
        informMessageDAO.addInformMessage(informMessage);
    }


}
