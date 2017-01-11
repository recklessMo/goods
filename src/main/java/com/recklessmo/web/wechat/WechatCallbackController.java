package com.recklessmo.web.wechat;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import com.recklessmo.constant.WechatConstants;
import com.recklessmo.model.wechat.WechatMsg;
import com.recklessmo.util.wechat.WechatUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * wechar 回调接口  http & no security policy so far
 *
 */
@Controller
@RequestMapping("/public/wechat")
public class WechatCallbackController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WechatCallbackController.class);

    public static String TOKEN = "wechatyunxiaoyuan";


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
            data.add(TOKEN);
            data.add(timestamp);
            data.add(nonce);
            Collections.sort(data);
            String param = data.get(0) + data.get(1) + data.get(2);
            String encryptData = WechatUtils.getDigestData(param);
            return encryptData.equals(signature) ? echostr : "";
        }
        //否则就走正常的业务逻辑
        LOGGER.info("content" + content);
        WechatMsg wechatMsg = WechatUtils.parseWechatMsg(content);
        LOGGER.info(wechatMsg.toString());
        if (wechatMsg.getMsgType().equals("event")) {
            if (wechatMsg.getEvent().equals("subscribe")) {
                if (wechatMsg.getTicket() != null) {
                    //扫码关注通过,调用业务逻辑
                } else {
                }
            } else if (wechatMsg.getEvent().equals("unsubscribe")) {
                //解绑openId
            } else if (wechatMsg.getEvent().equals("SCAN")) {
                //用户关注之后再进行扫码
            }
        } else if (wechatMsg.getMsgType().equals("text") || wechatMsg.getMsgType().equals("image")
                || wechatMsg.getMsgType().equals("voice") || wechatMsg.getMsgType().equals("video") || wechatMsg.getMsgType().equals("shortvideo")) {
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
        sb.append("&redirect_uri=");
        if (menuid == 1) {

        }else if (menuid == 2) {

        }else if (menuid == 3){

        }else if (menuid == 4){
        }
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
    @RequestMapping(value = "/menucallback", method = RequestMethod.GET)
    public String clickMenuCallback(@RequestParam("code") String code, @RequestParam("state") int state, @RequestParam("orgId") int orgId, Model model) {
        //根据state来进行回调判断
        model.addAttribute("orgId", orgId);
        if (state == 1) {
            return "about";
        } else if (state == 2) {
            return "2";
        } else if (state == 3){
            return "3";
        } else if (state == 4){
            return "4";
        }
        return "";
    }

    /**
     *
     * 服务号的回调302到这个地址, 目的是方便前端页面的跳转
     *
     * @param code
     * @param state
     * @param orgId
     * @param model
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String page(@RequestParam("code") String code, @RequestParam("state") int state, @RequestParam("orgId") int orgId, Model model) {
        model.addAttribute("orgId", orgId);
        String openId = code.substring(1, code.length() - 1);
        LOGGER.info("openid: " + openId);
        return "";
    }


}
