package com.recklessmo.web.passport;

import com.recklessmo.service.passport.PassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import com.recklessmo.model.passport.*;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * the very first controller created by xiaohuihui !
 *
 * Created by hpf on 4/13/16.
 */
@Controller
public class LoginController {

    @Resource
    private PassportService passportService;

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @ResponseBody
    @RequestMapping(value = "/v1/account/login", method = RequestMethod.GET)
    public List<User> loginAjax(){
        return passportService.getUserList();
    }


}

