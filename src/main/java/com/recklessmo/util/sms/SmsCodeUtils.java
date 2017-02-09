package com.recklessmo.util.sms;

/**
 * Created by hpf on 2/9/17.
 */
public class SmsCodeUtils {

    public static String generateCode(){
        long now = System.currentTimeMillis();
        return "" + now % 10000;
    }

}
