package com.recklessmo.web.system;

import com.recklessmo.model.system.Org;
import com.recklessmo.model.user.User;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.system.OrgService;
import com.recklessmo.service.user.UserService;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by hpf on 2/20/17.
 */
@Controller
public class OrgController {

    @Resource
    private UserService userService;

    @Resource
    private OrgService orgService;

    /**
     * 机构list
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/v1/org/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse orgList(@RequestBody Page page){
        List<Org>  orgList = orgService.listOrg(page);
        int count = orgService.listOrgCount(page);
        return new JsonResponse(200, orgList, count);
    }

    /**
     * 增加新的机构
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/v1/org/add", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse addOrg(@RequestBody Org org){
        boolean exist = userService.existUser(org.getUserName());
        if(exist){
            return new JsonResponse(301, "账户名已经存在！", null);
        }
        Date now = new Date();
        long maxOrgId = orgService.getMaxOrgId();
        long groupId = maxOrgId / 100;
        long orgId = (groupId + 1) * 100 + 1;
        org.setOrgId(orgId);
        org.setCreated(now);
        orgService.addOrg(org);
        User user = new User();
        user.setName(org.getAdminName());
        user.setPhone(org.getAdminPhone());
        user.setOrgId(orgId);
        user.setCreated(now);
        user.setUserName(org.getUserName());
        user.setPwd(org.getPwd());
        //初始化只增加一个账号设置权限
        user.setAuthorities("801");
        userService.add(user);
        return new JsonResponse(200, null, null);
    }



}
