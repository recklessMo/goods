package com.recklessmo.web.user;

import com.recklessmo.model.user.User;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.response.ResponseType;
import com.recklessmo.service.security.EduUserDetailService;
import com.recklessmo.service.user.UserService;
import com.recklessmo.web.webmodel.page.UserPage;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hpf on 6/16/16.
 */
@Controller
@RequestMapping("/v1/user")
public class UserController {


    @Resource
    private UserService userService;

    @Resource
    private UserDetailsService userDetailsService;

    /**
     *
     * 根据当前用户的权限获取系统菜单列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse list(@RequestBody UserPage page){
        List<User>  users = userService.getUserList(page);
        int count = userService.getUserListTotalCount(page);
        return new JsonResponse(ResponseType.RESPONSE_OK, users, count);
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse get(@RequestBody long id){
        User user = userService.getUser(id);
        return new JsonResponse(ResponseType.RESPONSE_OK, user, null);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse delete(@RequestBody long id){
        userService.delete(id);
        return new JsonResponse(ResponseType.RESPONSE_OK, null, null);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResponse add(@RequestBody User user){
        System.out.println(user.getUserName() + ", " + user.getName() + "," + user.getPhone() + "," + user.getPwd());
        try {
            userService.add(user);
        }catch(Exception e){
            e.printStackTrace();
            return new JsonResponse(ResponseType.RESPONSE_ADD_USER_REPEAT, null, null);
        }
        return new JsonResponse(ResponseType.RESPONSE_OK, null, null);
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResponse update(@RequestBody User user){
        try {
            //将权限进行去重
            String[] array = StringUtils.split(user.getAuthorities(), ",");
            Set<String> authSet = new HashSet<>();
            if(array != null) {
                for (String temp : array) {
                    authSet.add(temp.trim());
                }
            }
            user.setAuthorities(StringUtils.join(authSet, ","));
            userService.update(user);
            //删除缓存里面的用户信息, 目前这样只适合单机,多机器的时候,用户缓存最好放在redis里面
            ((EduUserDetailService)userDetailsService).reloadUserByUserName(user.getUserName());
        }catch(Exception e){
            return new JsonResponse(ResponseType.RESPONSE_UPDATE_USER_REPEAT, null, null);
        }
        return new JsonResponse(ResponseType.RESPONSE_OK, null, null);
    }

}
