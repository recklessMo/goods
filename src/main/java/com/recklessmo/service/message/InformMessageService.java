package com.recklessmo.service.message;

import com.recklessmo.dao.message.InformMessageDAO;
import com.recklessmo.model.message.InformMessage;
import com.recklessmo.model.setting.Grade;
import com.recklessmo.model.setting.Group;
import com.recklessmo.service.setting.GradeSettingService;
import com.recklessmo.service.sms.SmsNetworkService;
import com.recklessmo.web.webmodel.page.InformListPage;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * created by hpf 02/12/2017
 */
@Service
public class InformMessageService {

    @Resource
    private GradeSettingService gradeSettingService;

    @Resource
    private InformMessageDAO informMessageDAO;

    public List<InformMessage> getInformMessageList(Page page){
        List<InformMessage> informMessageList = informMessageDAO.getInformMessageList(page);
        compose(page.getOrgId(), informMessageList);
        return informMessageList;
    }

    public int getInformMessageListCount(Page page){
        return informMessageDAO.getInformMessageCount(page);
    }

    public void addInformMessage(InformMessage informMessage){
        informMessageDAO.addInformMessage(informMessage);
    }

    public InformMessage getInformMessageById(long orgId, long id){
        InformMessage informMessage = informMessageDAO.getInformMessageById(orgId, id);
        compose(orgId, informMessage);
        return informMessage;
    }

    public void deleteInformMessage(long orgId, long id){
        informMessageDAO.deleteInformMessage(orgId, id);
    }

    public List<InformMessage> getInformMessageListByGrade(InformListPage page){
        List<InformMessage> informMessageList = informMessageDAO.getInformMessageListByGrade(page);
        compose(page.getOrgId(), informMessageList);
        return informMessageList;
    }

    private void compose(long orgId, InformMessage informMessage){
        List<InformMessage> informMessageList = new LinkedList<>();
        informMessageList.add(informMessage);
        compose(orgId, informMessageList);
    }

    private void compose(long orgId, List<InformMessage> informMessageList){
        if(informMessageList.size() == 0){
            return;
        }
        List<Grade> gradeList = gradeSettingService.listAllGrade(orgId);
        Map<Long, Grade> gradeMap = new HashMap<>();
        Map<Long, Group> classMap = new HashMap<>();
        gradeList.stream().forEach(grade-> {
            gradeMap.put(grade.getGradeId(), grade);
            grade.getClassList().stream().forEach(group -> {
                classMap.put(group.getClassId(), group);
            });
        });

        informMessageList.stream().forEach(informMessage -> {
            Grade grade = gradeMap.get(informMessage.getGradeId());
            if(grade != null){
                informMessage.setGradeName(grade.getGradeName());
                informMessage.setGradeOtherName(grade.getOtherName());
            }else{
                informMessage.setGradeName("全部");
                informMessage.setGradeOtherName("全部");
            }
            Group group = classMap.get(informMessage.getClassId());
            if(group != null){
                informMessage.setClassName(group.getClassName());
            }else{
                informMessage.setClassName("全部");
            }
        });
    }

}
