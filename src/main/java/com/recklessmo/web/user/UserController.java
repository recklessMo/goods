package com.recklessmo.web.user;

import com.recklessmo.manage.menu.MenuManager;
import com.recklessmo.model.passport.IdModel;
import com.recklessmo.model.passport.User;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.user.UserService;
import com.recklessmo.util.ContextUtils;
import org.springframework.security.access.prepost.PreAuthorize;
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

    /**
     *
     * 根据当前用户的权限获取系统菜单列表
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @ResponseBody
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse list(){
        List<User>  users = userService.getUserList();
//        try {
//            Thread.sleep(5000);
//        }catch(Exception e){
//
//        }
        return new JsonResponse(200, users, 11);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @ResponseBody
    @RequestMapping(value = "/get", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse get(@RequestBody long id){
        User user = userService.getUser(id);
//        try {
//            Thread.sleep(5000);
//        }catch(Exception e){
//
//        }
        return new JsonResponse(200, user, null);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse delete(@RequestBody long id){
        userService.delete(id);
//        try {
//            Thread.sleep(5000);
//        }catch(Exception e){
//
//        }
        return new JsonResponse(200, null, null);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @ResponseBody
    @RequestMapping(value = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResponse add(@RequestBody User user){
        userService.add(user);
        return new JsonResponse(200, null, null);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @ResponseBody
    @RequestMapping(value = "/update", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResponse update(@RequestBody User user){
        userService.update(user);
//        try {
//            Thread.sleep(5000);
//        }catch(Exception e){
//
//        }
        return new JsonResponse(200, null, null);
    }


}
