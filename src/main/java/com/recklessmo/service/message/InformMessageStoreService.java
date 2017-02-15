package com.recklessmo.service.message;

import com.recklessmo.dao.exam.ExamDAO;
import com.recklessmo.dao.message.InformMessageDAO;
import com.recklessmo.model.exam.Exam;
import com.recklessmo.model.message.InformMessage;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
@Service
public class InformMessageStoreService {

    @Resource
    private InformMessageSendService informMessageSendService;

    @Resource
    private InformMessageDAO informMessageDAO;

    public List<InformMessage> listInformMessage(Page page){
        return informMessageDAO.getInformMessageList(page);
    }

    public int listInformMessageCount(Page page){
        return informMessageDAO.getInformMessageCount(page);
    }


    /**
     *
     * 发送消息, 先存储消息.
     *
     * 同时需要记录消息历史, 记录每个人的收到的状态, 单独加一张表, 这样可以方便的进行查看.
     *
     *
     *
     * @param informMessage
     */
    public void addInformMessage(InformMessage informMessage, boolean send){
        //存储消息
        informMessageDAO.addInformMessage(informMessage);
        //如果需要进行发送
        if(send){
            informMessageSendService.sendMessage(informMessage);
        }
    }

}
