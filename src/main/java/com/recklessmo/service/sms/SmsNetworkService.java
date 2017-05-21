package com.recklessmo.service.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.recklessmo.model.sms.SmsCode;
import com.recklessmo.service.http.RemoteHttpService;
import com.recklessmo.util.EncryptUtils;
import com.recklessmo.util.TimeUtils;
import com.recklessmo.util.sms.SmsCodeUtils;
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
 * 用的是阿里大于, 看起来比较便宜, 大概0.045一条
 *
 * Created by hpf on 2/9/17.
 */
@Service
public class SmsNetworkService {

    @Resource
    private RemoteHttpService remoteHttpService;

    @Resource
    private SmsCodeService smsCodeService;

    //公共参数
    public static String APPKEY = "23839745";
    public static String APPSECRET = "eddf9162eb59766350443261d4f5d793";
    public static String HTTPURL = "http://gw.api.taobao.com/router/rest";
    public static String HTTPSURL = "https://eco.taobao.com/router/rest";
    public static String SIGN = "立体校园云平台";
    //发送
    public static String APINAME = "alibaba.aliqin.fc.sms.num.send";
    //短信模板
    public static String CODETEMPLATEID = "SMS_67565021";
    //返回结果
    public static String RETURNOK = "alibaba_aliqin_fc_sms_num_send_response";

    private CloseableHttpClient httpClient = null;

    public SmsNetworkService(){
        httpClient = HttpClients.createDefault();
    }

    /**
     * 发送短信接口, 内部产生一个4位验证码, 单个发送
     *
     * 异常内部消化, 返回null代表发送失败.
     *
     * @param phone 电话号码
     * @return
     * @throws Exception
     */
    public int sendSmsCode(String phone, String code){
        try {
            Map<String, String> params = new HashMap<>();
            //公共参数
            putPublicParams(params);
            //业务参数
            params.put("sms_type", "normal");
            params.put("sms_free_sign_name", SIGN);
            //验证码参数
            StringBuilder sb = new StringBuilder();
            sb.append("{\"code\":\"");
            sb.append(code);
            sb.append("\"}");
            params.put("sms_param", sb.toString());
            params.put("rec_num", phone);
            params.put("sms_template_code", CODETEMPLATEID);
            putSign(params);
            //调用http进行发送
            String result = remoteHttpService.postHttpRequest(HTTPSURL, params);
            //解析返回结果
            JSONObject object = JSON.parseObject(result);
            if (object.containsKey(RETURNOK)) {
                //成功
                return 200;
            }
        }catch(Exception e){
            //异常处理
            e.printStackTrace();
        }
        return 500;
    }

    /**
     *
     * 给一组电话发送text
     *
     * @param phoneList
     * @param text
     * @return
     */
    public boolean sendSmsMessage(List<String> phoneList, String text){

        return true;
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

}
