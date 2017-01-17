package com.recklessmo.web.wechat;

import com.recklessmo.constant.WechatConstants;
import com.recklessmo.model.wechat.WechatCallbackMsg;
import com.recklessmo.model.wechat.WechatMessage;
import com.recklessmo.service.wechat.WechatBizService;
import com.recklessmo.service.wechat.WechatCallbackService;
import com.recklessmo.service.wechat.WechatNetworkService;
import com.recklessmo.util.wechat.WechatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * wechat
 * 回调接口  http & no security policy so far
 *
 * 用于处理微信回调的请求
 */
@Controller
@RequestMapping("/public/wechat")
public class WechatCallbackController {

    private static final Log LOGGER = LogFactory.getLog(WechatCallbackController.class);

    public static String TOKEN = "wechatyunxiaoyuan";

    @Resource
    private WechatNetworkService wechatNetworkService;

    @Resource
    private WechatCallbackService wechatCallbackService;

    @Resource
    private WechatBizService wechatBizService;

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
        WechatCallbackMsg wechatCallbackMsg = WechatUtils.parseWechatMsg(content);
        LOGGER.info(wechatCallbackMsg.toString());
        if (wechatCallbackMsg.getMsgType().equals("event")) {
            if (wechatCallbackMsg.getEvent().equals("subscribe")) {
                if (wechatCallbackMsg.getTicket() != null) {
                    //扫描指定二维码关注
                    //进行绑定

                } else {
                    //扫描公众号二维码关注. 暂时无法做任何事
                }
            } else if (wechatCallbackMsg.getEvent().equals("unsubscribe")) {
                //解绑openId
            } else if (wechatCallbackMsg.getEvent().equals("SCAN")) {
                //用户关注之后再进行扫码
                //直接更换绑定信息


            }
        } else if (wechatCallbackMsg.getMsgType().equals("text")){
            //文本消息
            WechatMessage wechatMessage = new WechatMessage();
            wechatMessage.setOrgId(0);
            wechatMessage.setType(2);//接收
            wechatMessage.setMessageType(1);
            wechatMessage.setMessage(wechatCallbackMsg.getContent());
            wechatMessage.setCreated(new Date());
            wechatMessage.setOpenId(wechatCallbackMsg.getFromUserName());
            wechatMessage.setOpenName("微信用户");
            wechatBizService.receiveMessage(wechatMessage);
        } else if (wechatCallbackMsg.getMsgType().equals("image")
                || wechatCallbackMsg.getMsgType().equals("voice") || wechatCallbackMsg.getMsgType().equals("video") || wechatCallbackMsg.getMsgType().equals("shortvideo")) {
            //暂时不支持这种类型的消息.
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
        sb.append(WechatUtils.getEncodedUrl("http://" + WechatConstants.domainName + "/public/wechat/callback?"));
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
        sb.append("http://" + WechatConstants.domainName + "/public/wechat/page?type=");
        sb.append(state);
        sb.append("&code=");
        sb.append(openId);
        response.setStatus(302);
        response.sendRedirect(sb.toString());
    }

    /**
     *
     * 服务号的回调302到这个地址, 目的是方便前端页面的跳转
     *
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String page(@RequestParam("code") String code, @RequestParam("type") int type,  Model model) {
        String openId = code;
        //通过type和openId来进入不同的页面
        if(type == 1){

        }else if(type == 2){

        }else if(type == 3){

        }
        return "";
    }


}
