package com.recklessmo.service.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.recklessmo.model.sms.SmsCode;
import com.recklessmo.service.http.RemoteHttpService;
import com.recklessmo.util.EncryptUtils;
import com.recklessmo.util.TimeUtils;
import com.thoughtworks.xstream.converters.basic.StringBufferConverter;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 发送短信接口
 *
 * 用的是阿里大于, 看起来比较便宜
 *
 * Created by hpf on 2/9/17.
 */
@Service
public class SmsService {

    @Resource
    private RemoteHttpService remoteHttpService = new RemoteHttpService();

    private SmsCodeService smsCodeService;

    //公共参数
    public static String APPKEY = "23629911";
    public static String APPSECRET = "394939089b0a4db334cf68c1e3ad2681";
    public static String HTTPURL = "http://gw.api.taobao.com/router/rest";
    public static String HTTPSURL = "https://eco.taobao.com/router/rest";
    public static String SIGN = "云校园科技平台";
    //发送
    public static String APINAME = "alibaba.aliqin.fc.sms.num.send";
    //短信模板
    public static String CODETEMPLATEID = "SMS_45675130";

    private CloseableHttpClient httpClient = null;

    public SmsService(){
        httpClient = HttpClients.createDefault();
    }


    public SmsCode sendSmsCode(String phone, String code) throws Exception{
        Map<String, String> params = new HashMap<>();
        //公共参数
        putPublicParams(params);
        //业务参数
        params.put("sms_type", "normal");
        params.put("sms_free_sign_name", SIGN);
        //验证码参数
        StringBuilder sb = new StringBuilder();
        sb.append("{\"number\":\"");
        sb.append(code);
        sb.append("\"}");
        params.put("sms_param", sb.toString());
        params.put("rec_num", phone);
        params.put("sms_template_code",CODETEMPLATEID);
        putSign(params);
        //调用http进行发送
        String result = remoteHttpService.postHttpRequest(HTTPSURL, params);
        //解析返回结果
        JSONObject object = JSON.parseObject(result);
        if(object.containsKey("alibaba_aliqin_fc_sms_num_send_response")){
            //成功
//            return smsCodeService.addSmsCode(code);
        }
        return null;
    }

    private void putPublicParams(Map<String, String> params){
        params.put("method", APINAME);
        params.put("app_key", APPKEY);
        params.put("timestamp", new DateTime().toString(TimeUtils.dateTimeFormatter));
        params.put("format", "json");
        params.put("v", "2.0");
        params.put("sign_method", "md5");
    }

    private void putSign(Map<String, String> params){
        //对参数进行排序
        List<String> keyList = new ArrayList<>(params.keySet());
        Collections.sort(keyList);
        //拼接
        StringBuilder sb = new StringBuilder();
        sb.append(APPSECRET);
        keyList.forEach(key -> {
            sb.append(key);
            sb.append(params.get(key));
        });
        sb.append(APPSECRET);
        params.put("sign", EncryptUtils.getMd5Hex(sb.toString()).toUpperCase());
    }


    public static void main(String[] args){

        SmsService smsService = new SmsService();
        try {
            smsService.sendSmsCode("13088063013", "1342");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
