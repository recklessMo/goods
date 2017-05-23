package com.recklessmo.web.message;

import com.recklessmo.model.message.InformMessage;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.message.InformMessageService;
import com.recklessmo.service.student.StudentService;
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

    @Resource
    private StudentService studentService;

    /**
     * list 消息
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JsonResponse listInformMessage(@RequestBody Page page){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        page.setOrgId(userDetails.getOrgId());
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
        informMessage.setStatus(InformMessage.STATUS_INIT);
        informMessage.setTotalCount(studentService.getStudentListCountByGradeIdAndClassId(userDetails.getOrgId(), informMessage.getGradeId(), informMessage.getClassId()));
        informMessageService.addInformMessage(informMessage);
        return new JsonResponse(200, null, null);
    }


    /**
     * 删除消息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public JsonResponse deleteInformMessage(@RequestBody long id){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        informMessageService.deleteInformMessage(userDetails.getOrgId(), id);
        return new JsonResponse(200, null, null);
    }


}
