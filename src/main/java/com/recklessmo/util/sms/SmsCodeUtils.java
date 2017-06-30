package com.recklessmo.util.sms;

import com.recklessmo.model.sms.SmsCode;

/**
 * Created by hpf on 2/9/17.
 */
public class SmsCodeUtils {

    public static String generateCode(int num){
        String now = String.valueOf(System.currentTimeMillis());
        int len = now.length();
        return now.substring(len - num, len);
    }

    public static void main(String[] args){
        String res = SmsCodeUtils.generateCode(6);
        System.out.println(res);
    }

}
