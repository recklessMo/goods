package com.recklessmo.web.wechat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 7/12/16.
 */
@Controller
@RequestMapping("/public/wechat")
public class WechatCallbackController {

    public static String TOKEN = "wechat&ruoshui@2016";

    /**
     *
     * 注意编码
     *
     * @return
     */
    @RequestMapping(value = "/auth", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
    @ResponseBody
    public String auth(@RequestParam("signature")String signature, @RequestParam("timestamp")String timestamp, @RequestParam("nonce")String nonce){
        List<String> data = new LinkedList<String>();
        data.add(signature);
        data.add(timestamp);
        data.add(nonce);
        //排序
        Collections.sort(data);
        //加密

        return "all你好你好";
    }



}
