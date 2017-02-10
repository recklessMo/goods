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
public class InformMessageService {

    @Resource
    private InformMessageDAO informMessageDAO;

    public List<InformMessage> listInformMessage(Page page){
        return informMessageDAO.getInformMessageList(page);
    }

    public int listInformMessageCount(Page page){
        return informMessageDAO.getInformMessageCount(page);
    }

    public void addInformMessage(InformMessage informMessage){
        informMessageDAO.addInformMessage(informMessage);
    }

}
