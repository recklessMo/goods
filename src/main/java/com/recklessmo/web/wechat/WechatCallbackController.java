package com.recklessmo.web.wechat;

import com.alibaba.fastjson.JSON;
import com.recklessmo.constant.WechatConstants;
import com.recklessmo.model.student.StudentInfo;
import com.recklessmo.model.wechat.WechatCallbackMsg;
import com.recklessmo.model.wechat.WechatMessage;
import com.recklessmo.model.wechat.WechatTicket;
import com.recklessmo.model.wechat.WechatUser;
import com.recklessmo.service.student.StudentService;
import com.recklessmo.service.wechat.*;
import com.recklessmo.util.wechat.WechatCookieUtils;
import com.recklessmo.util.wechat.WechatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * wechat
 * 回调接口  http & no security policy so far
 * 用于处理微信回调的请求
 */
@Controller
@RequestMapping("/public/wechat")
public class WechatCallbackController {

    private static final Log LOGGER = LogFactory.getLog(WechatCallbackController.class);

    @Resource
    private WechatNetworkService wechatNetworkService;

    @Resource
    private WechatMessageService wechatMessageService;

    @Resource
    private WechatTicketService wechatTicketService;

    @Resource
    private WechatUserService wechatUserService;

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;


    @Resource
    private StudentService studentService;

    /**
     * 微信回调接口
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @RequestMapping(value = "/authentication", method ={ RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String authentication(@RequestParam(value = "signature", required = false)String signature, @RequestParam(value = "timestamp", required = false)String timestamp
            , @RequestParam(value = "nonce", required = false)String nonce, @RequestParam(value = "echostr", required = false)String echostr, @RequestBody(required = false) String content) {
        //如果是验证的话,就返回验证信息
        if (content == null) {
            LOGGER.info(signature + "," + timestamp + "," + nonce + "," + echostr);
            List<String> data = new LinkedList<>();
            data.add(WechatConstants.TOKEN);
            data.add(timestamp);
            data.add(nonce);
            Collections.sort(data);
            String param = data.get(0) + data.get(1) + data.get(2);
            String encryptData = WechatUtils.getDigestData(param);
            return encryptData.equals(signature) ? echostr : "";
        }
        //否则就走正常的业务逻辑
        WechatCallbackMsg wechatCallbackMsg = (WechatCallbackMsg) WechatUtils.fromXml(content, WechatCallbackMsg.class);
        LOGGER.info(JSON.toJSONString(wechatCallbackMsg));
        if (wechatCallbackMsg.getMsgType().equals("event")) {
            if (wechatCallbackMsg.getEvent().equals("subscribe")) {
                if (wechatCallbackMsg.getTicket() != null) {
                    //扫描指定二维码关注
                    //进行绑定
                    String ticket = wechatCallbackMsg.getTicket();
                    WechatTicket wechatTicket = wechatTicketService.getTicketByTicket(ticket);
                    if(wechatTicket != null){
                        wechatUserService.bindUser(wechatTicket.getOrgId(), wechatTicket.getSid(), wechatCallbackMsg.getFromUserName());
                    }
                } else {
                    //直接扫描公众号二维码关注. 暂时无法做任何事, 就发送一条欢迎消息
                    wechatMessageService.sendCustomMessage(wechatCallbackMsg.getFromUserName(), WechatConstants.WELCOME_DEFAULT);
                }
            } else if (wechatCallbackMsg.getEvent().equals("unsubscribe")) {
                //解绑所有的openId, 标记为删除
                wechatUserService.releaseUserByOpenId(wechatCallbackMsg.getFromUserName());
            } else if (wechatCallbackMsg.getEvent().equals("SCAN")) {
                //用户关注之后再进行扫码
                //直接更换绑定信息
                if (wechatCallbackMsg.getTicket() != null){
                    String ticket = wechatCallbackMsg.getTicket();
                    WechatTicket wechatTicket = wechatTicketService.getTicketByTicket(ticket);
                    if(wechatTicket != null){
                        //标记以前的都删除
                        wechatUserService.releaseUserByOpenId(wechatCallbackMsg.getFromUserName());
                        //创建新的user
                        wechatUserService.bindUser(wechatTicket.getOrgId(), wechatTicket.getSid(), wechatCallbackMsg.getFromUserName());
                    }
                }else{
                    ;
                }
            }
        } else if (wechatCallbackMsg.getMsgType().equals("text")){
            StudentInfo studentInfo = studentService.getStudentInfoByWechatId(wechatCallbackMsg.getFromUserName());
            if(studentInfo == null){
                wechatMessageService.sendCustomMessage(wechatCallbackMsg.getFromUserName(), WechatConstants.UNBIND_RETURN);
                return "";
            }
            //文本消息
            WechatMessage wechatMessage = new WechatMessage();
            wechatMessage.setOrgId(studentInfo.getOrgId());
            wechatMessage.setSid(studentInfo.getSid());
            wechatMessage.setType(WechatMessage.MSG_DIRECTION_RECEIVE);//接收
            wechatMessage.setMessageType(WechatMessage.MSG_TYPE_TEXT);//文本
            wechatMessage.setMessage(wechatCallbackMsg.getContent());
            wechatMessage.setCreated(new Date());
            wechatMessage.setOpenId(wechatCallbackMsg.getFromUserName());
            wechatMessageService.receiveMessage(wechatMessage);
            //同时发送websocket消息
            Map<String, Object> payLoad = new HashMap<>();
            payLoad.put("type", "RECEIVE_WECHAT_MSG");
            payLoad.put("data", wechatCallbackMsg.getFromUserName());
            simpMessagingTemplate.convertAndSend("/websocket/notify/broadcast/" + studentInfo.getOrgId(), payLoad);
        } else if (wechatCallbackMsg.getMsgType().equals("image")
                || wechatCallbackMsg.getMsgType().equals("voice") || wechatCallbackMsg.getMsgType().equals("video") || wechatCallbackMsg.getMsgType().equals("shortvideo")) {
            //暂时不支持这种类型的消息.
            wechatMessageService.sendCustomMessage(wechatCallbackMsg.getFromUserName(), WechatConstants.UNSUPPORTED_MESSAGE);
        }
        return "";
    }


    /**
     * 微信菜单,重定向到回调接口
     *
     * @param menuid
     * @return
     */
    @RequestMapping(value = "/menu/{id}", method = RequestMethod.GET)
    public void clickMenu(@PathVariable("id") int menuid, HttpServletResponse response) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(WechatConstants.GET_CODE_URL);
        sb.append("appid=");
        sb.append(WechatConstants.APPID);
        sb.append("&redirect_uri=");
        sb.append(WechatUtils.getEncodedUrl(WechatConstants.domainName + "/public/wechat/callback?"));
        sb.append("response_type=code&scope=snsapi_base&state=");
        sb.append(menuid);
        sb.append("#wechat_redirect");
        LOGGER.info(sb.toString());
        response.setStatus(302);
        response.sendRedirect(sb.toString());
    }

    /**
     * 微信回调接口
     * <p>
     * 需要在服务号内设置对应的回调url
     *
     * @param code
     * @param state
     * @return
     */
    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public void clickMenuCallback(@RequestParam("code") String code, @RequestParam("state") int state, HttpServletResponse response) throws Exception{
        String openId = wechatNetworkService.getBrowerOpenId(code);
        //根据state来进行回调判断
        StringBuilder sb = new StringBuilder();
        sb.append(WechatConstants.domainName + "/public/wechat/page/");
        sb.append(state);
        Cookie cookie = new Cookie("token", "s" + openId + "e");
        cookie.setPath("/public/wechat/");
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        response.setStatus(302);
        response.sendRedirect(sb.toString());
    }

    /**
     *
     * 服务号的回调302到这个地址, 目的是方便前端页面的跳转
     *
     * @return
     */
    @RequestMapping(value = "/page/{type}", method = RequestMethod.GET)
    public String page(@PathVariable("type") int type, Model model, HttpServletRequest request, HttpServletResponse response)throws  Exception {
        String openId = WechatCookieUtils.getOpenIdByCookie(request.getCookies());
        if(openId == null){
            //如果没有cookie的话就去走认证的流程来获取cookie
            StringBuilder sb = new StringBuilder();
            sb.append(WechatConstants.domainName);
            sb.append("/public/wechat/menu/");
            sb.append(type);
            response.setStatus(HttpStatus.SC_MOVED_TEMPORARILY);
            response.sendRedirect(sb.toString());
            return null;
        }

        //如果没有绑定的话就进行绑定
        StudentInfo studentInfo = studentService.getStudentInfoByWechatId(openId);
        if(studentInfo == null){
            return "bind";
        }

        //找到了cookie并且绑定了, 就进去主页面
        if(type == 1){
            return "self";
        }
        return "";
    }


}
