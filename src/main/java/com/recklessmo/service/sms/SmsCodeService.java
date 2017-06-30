package com.recklessmo.service.sms;

import com.recklessmo.dao.sms.SmsCodeDAO;
import com.recklessmo.model.sms.SmsCode;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.security.access.PermissionCacheOptimizer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.Date;

/**
 *
 * 验证码管理service
 *
 * Created by hpf on 2/9/17.
 */
@Service
public class SmsCodeService {

    @Resource
    private SmsCodeDAO smsCodeDAO;

    public SmsCode addSmsCode(SmsCode code){
        smsCodeDAO.addSmsCode(code);
        return code;
    }

    public SmsCode getSmsCodeByNameAndPhone(String name, String phone){
        return smsCodeDAO.getSmsCodeByNameAndPhone(name, phone);
    }

}
