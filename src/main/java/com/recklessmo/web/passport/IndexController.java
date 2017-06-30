package com.recklessmo.web.passport;

import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.model.system.Org;
import com.recklessmo.service.system.OrgService;
import com.recklessmo.util.ContextUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by hpf on 4/26/16.
 *
 * 登录和加载
 */
@Controller
public class IndexController {

    @Resource
    private OrgService orgService;

    @PreAuthorize("hasAnyAuthority('login')")
    @RequestMapping(value = "/")
    public String index(Model model){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        model.addAttribute("current", userDetails.getId());
        model.addAttribute("orgId", userDetails.getOrgId());
        Org org = orgService.getOrg(userDetails.getOrgId());
        model.addAttribute("orgName", org.getOrgName());
        model.addAttribute("name", userDetails.getName());
        return "index";
    }

    @RequestMapping(value = "/login")
    public String index_login(Model model){
        model.addAttribute("current", 0);
        model.addAttribute("orgId", 0);
        model.addAttribute("orgName", "立体校园云平台");
        return "index";
    }

}
