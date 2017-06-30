package com.recklessmo.service.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.recklessmo.constant.WechatConstants;
import com.recklessmo.model.setting.Schedule;
import com.recklessmo.model.wechat.WechatTemplateMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;


/**
 *
 * 用于对微信进行请求对service, 这个service负责api调用
 *
 *
 * Created by hpf on 1/12/17.
 *
 *
 * 1. 获取access_token
 * 2. 获取头像
 * 3. 获取openId
 * 4.
 *
 */
@Service
public class WechatNetworkService {

    private String accessToken;

    private static final Log logger = LogFactory.getLog(WechatNetworkService.class);

    private static CloseableHttpClient httpclient = HttpClients.createDefault();

    private long lastUpdateTime = 0;

    /**
     *
     * 从内存中中获取access_token
     *
     * 首先判断是否需要刷新
     */
    public String getAccessToken() {
        refreshAccessToken();
        return accessToken;
    }


    private void refreshAccessToken() {
        //时间到了就更新,没到不更新
        long now = System.currentTimeMillis();
        long last = lastUpdateTime;
        if (now - lastUpdateTime >= WechatConstants.GAP) {
            String token = getTokenFromWechat();
            if(token != null){
                accessToken = token;
                lastUpdateTime = now;
            }
        }
    }


    private String getTokenFromWechat() {
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(String.format(WechatConstants.GET_TOKEN_URL, WechatConstants.APPID, WechatConstants.APPSECRET));
            response = httpclient.execute(httpGet);
            HttpEntity entity1 = response.getEntity();
            String token = EntityUtils.toString(entity1);
            JSONObject object = JSON.parseObject(token);
            logger.info("getTokenFromWechat: " + object.toJSONString());
            logger.info(object.toString());
            return (String) object.get("access_token");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     *
     * 获取openId
     *
     * @param code
     * @return
     */
    public String getBrowerOpenId(String code) {
        CloseableHttpResponse response = null;
        try {
            String str = String.format(WechatConstants.GET_NET_TOKEN_URL, WechatConstants.APPID, WechatConstants.APPSECRET, code);
            logger.info(str);
            HttpGet httpGet = new HttpGet(str);
            response = httpclient.execute(httpGet);
            HttpEntity entity1 = response.getEntity();
            String token = EntityUtils.toString(entity1);
            JSONObject object = JSON.parseObject(token);
            logger.info("getBrowerOpenId: " + object.toJSONString());
            return (String) object.get("openid");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     *
     * 通过openId获取头像, 绑定到wechatUser里面,
     *
     * @param openId
     * @return
     */
    public String getHeadUrl(String openId) {
        CloseableHttpResponse response = null;
        try {
            String accessToken = getAccessToken();
            if (accessToken != null && !accessToken.isEmpty()) {
                String str = String.format(WechatConstants.GET_HEAD_URL, accessToken, openId);
                logger.info(str);
                HttpGet httpGet = new HttpGet(str);
                response = httpclient.execute(httpGet);
                HttpEntity entity1 = response.getEntity();
                String token = EntityUtils.toString(entity1);
                JSONObject object = JSON.parseObject(token);
                logger.info("getHeadUrl: " + object.toJSONString());
                return (String) object.get("headimgurl");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     *
     * 发送消息到微信
     *
     * 暂时只支持文本消息
     *
     */
    public int sendMsgToWechat(String content, String openId){
        CloseableHttpResponse response = null;
        try {
            String accessToken = getAccessToken();
            if (accessToken != null) {
                HttpPost httpPost = new HttpPost(WechatConstants.SEND_MSG_URL + accessToken);
                JSONObject object = new JSONObject();
                object.put("touser", openId);
                object.put("msgtype", "text");
                JSONObject obj = new JSONObject();
                obj.put("content", content);
                object.put("text", obj);
                StringEntity stringEntity = new StringEntity(object.toJSONString(), "UTF-8");
                stringEntity.setContentType("application/json");
                httpPost.setEntity(stringEntity);
                response = httpclient.execute(httpPost);
                StatusLine status = response.getStatusLine();
                return status.getStatusCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 500;
    }


    /**
     *
     * 发送模板消息到微信
     *
     * 暂时只支持文本消息
     *
     */
    public int sendTemplateMsgToWechat(WechatTemplateMessage wechatTemplateMessage){
        CloseableHttpResponse response = null;
        try {
            String accessToken = getAccessToken();
            if (accessToken != null) {
                HttpPost httpPost = new HttpPost(WechatConstants.SEND_TEMPLATE_MSG_URL + accessToken);
                JSONObject object = new JSONObject();
                object.put("touser", wechatTemplateMessage.getOpenId());
                object.put("template_id", wechatTemplateMessage.getTemplateId());
                object.put("url", wechatTemplateMessage.getUrl());
                object.put("data", wechatTemplateMessage.getData());
                StringEntity stringEntity = new StringEntity(object.toJSONString(), "UTF-8");
                stringEntity.setContentType("application/json");
                httpPost.setEntity(stringEntity);
                response = httpclient.execute(httpPost);
                StatusLine status = response.getStatusLine();
                return status.getStatusCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 500;
    }


    /**
     *
     * 获取临时二维码
     *
     * @return
     */
    public String getWechatTicket(int sceneId){
        CloseableHttpResponse response = null;
        try {
            String accessToken = getAccessToken();
            if (accessToken != null && !accessToken.isEmpty()) {
                String str = String.format(WechatConstants.GET_QR_CODE_URL, accessToken);
                logger.info(str);
                HttpPost httpPost = new HttpPost(str);
                JSONObject obj = new JSONObject();
                obj.put("expire_seconds", 2592000);
                obj.put("action_name", "QR_SCENE");
                obj.put("", String.format("{\"scene\": {\"scene_id\":%s}}", sceneId));
                httpPost.setEntity(new StringEntity(obj.toJSONString(), "UTF-8"));
                response = httpclient.execute(httpPost);
                HttpEntity entity1 = response.getEntity();
                String token = EntityUtils.toString(entity1);
                JSONObject object = JSON.parseObject(token);
                logger.info("getWechatTicket: " + object.toJSONString());
                return (String) object.get("ticket");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) {

        System.out.println((int)(-7/6));


    }
}
