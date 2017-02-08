package com.recklessmo.service.wechat;

import com.recklessmo.dao.student.StudentDAO;
import com.recklessmo.model.student.StudentAllInfo;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;

/**
 *
 * 负责处理微信回调相关的逻辑
 *
 * 给微信回调页面提供数据用, 页面还是在controller里面进行组装
 *
 * Created by hpf on 1/12/17.
 */
@Service
public class WechatCallbackService {

    @Resource
    private StudentDAO studentDAO;

    /**
     * 根据微信id获取学生信息
     * @param wechatId
     * @return
     */
    public StudentAllInfo getStudentInfoByWechatId(String wechatId){
        return studentDAO.getStudentAllInfoByWechatId(wechatId);
    }



}
