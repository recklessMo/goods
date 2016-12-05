package com.recklessmo.web.user;

import com.recklessmo.model.user.User;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.response.ResponseType;
import com.recklessmo.service.user.UserService;
import com.recklessmo.web.webmodel.page.UserPage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

            userService.update(user);
        }catch(Exception e){
            return new JsonResponse(ResponseType.RESPONSE_UPDATE_USER_REPEAT, null, null);
        }
        return new JsonResponse(ResponseType.RESPONSE_OK, null, null);
    }

}
