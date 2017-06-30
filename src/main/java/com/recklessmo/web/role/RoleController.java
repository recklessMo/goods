package com.recklessmo.web.role;

import com.recklessmo.model.role.Role;
import com.recklessmo.model.role.RoleVO;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.role.RoleService;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
//import java.util.List;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.model.user.User;
import com.recklessmo.model.user.UserVO;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.response.ResponseType;
import com.recklessmo.service.mail.MailService;
import com.recklessmo.service.security.EduUserDetailService;
import com.recklessmo.service.user.UserService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.web.webmodel.page.UserPage;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by hpf on 8/29/16.
 */
@RequestMapping("/v1/role")
@Controller
public class RoleController {

    @Resource
    private RoleService roleService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JsonResponse listRole(@RequestBody Page page){
        List<Role> roleList = roleService.listRoles();
        List<RoleVO> roleVOs = new LinkedList<>();
        roleList.stream().forEach(role -> {
            RoleVO roleVO = new RoleVO();
            roleVO.setId(role.getId());
            roleVO.setCreated(role.getCreated());
            roleVO.setRoleName(role.getName());
            String[] str = StringUtils.split(role.getPermissions(), ",");
            List<Integer> roles = new LinkedList<>();
            for(String temp : str){
                roles.add(Integer.parseInt(temp));
            }
            roleVO.setAuthorities(roles);
            roleVOs.add(roleVO);
        });
        //int count = roleService.getUserListTotalCount(page);
        return new JsonResponse(200, roleList, null);
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public JsonResponse getRole(@RequestBody long id){
        Role role = roleService.getRole(id);
        return new JsonResponse(200, role, null);
    }

    @ResponseBody
    @RequestMapping(value = "/create",  method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResponse create(@RequestBody Role role){
        try {
            //role.getAuthorities().sort((o1,o2)->o1.compareTo(o2));
            role.setCreated(new Date());
            roleService.create(role);
        }catch(Exception e){
            e.printStackTrace();
            //return new JsonResponse(200, null, null);
           return new JsonResponse(ResponseType.RESPONSE_ADD_USER_REPEAT, null, null);
        }
        return new JsonResponse(200, null, null);
    }
    
    @ResponseBody
    @RequestMapping(value = "/update", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResponse update(@RequestBody Role role){
        roleService.update(role);
        return new JsonResponse(200, null, null);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public JsonResponse delete(@RequestBody long id){
        roleService.delete(id);
        return new JsonResponse(200, null, null);
    }


}
