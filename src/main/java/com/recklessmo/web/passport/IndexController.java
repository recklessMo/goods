package com.recklessmo.web.passport;

import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.util.ContextUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hpf on 4/26/16.
 *
 * 登录和加载
 */
@Controller
public class IndexController {

    @PreAuthorize("hasAnyAuthority('login')")
    @RequestMapping(value = "/")
    public String index(Model model){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        model.addAttribute("current", userDetails.getId());
        model.addAttribute("orgId", userDetails.getOrgId());
        return "index";
    }

    @RequestMapping(value = "/login")
    public String index_login(Model model){
        model.addAttribute("current", 0);
        model.addAttribute("orgId", 0);
        return "index";
    }

}
