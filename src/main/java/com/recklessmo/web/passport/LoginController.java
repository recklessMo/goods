package com.recklessmo.web.passport;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * the very first controller created by xiaohuihui !
 *
 * Created by hpf on 4/13/16.
 */
@Controller
public class LoginController {

    @ResponseBody
    @RequestMapping(value = "/v1/account/login", method = RequestMethod.GET)
    public String login(){
        return "{id:987, name:\"xxx\"}";
    }

}

