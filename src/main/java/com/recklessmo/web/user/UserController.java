package com.recklessmo.web.user;

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
 * Created by hpf on 6/16/16.
 */
@Controller
@RequestMapping("/v1/user")
public class UserController {


    @Resource
    private UserService userService;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private MailService mailService;

    /**
     *
     * 根据当前用户的权限获取系统菜单列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse list(@RequestBody UserPage page){
        DefaultUserDetails defaultUserDetails = ContextUtils.getLoginUserDetail();
        page.setOrgId(defaultUserDetails.getOrgId());
        List<User>  users = userService.getUserList(page);
        List<UserVO> userVOs = new LinkedList<>();
        users.stream().forEach(user -> {
            UserVO userVO = new UserVO();
            userVO.setId(user.getId());
            userVO.setCreated(user.getCreated());
            userVO.setEmail(user.getEmail());
            userVO.setGender(user.getGender());
            userVO.setJob(user.getJob());
            userVO.setName(user.getName());
            userVO.setPhone(user.getPhone());
            userVO.setPwd("*******");
            userVO.setUserName(user.getUserName());
            String[] str = StringUtils.split(user.getAuthorities(), ",");
            List<Integer> roleList = new LinkedList<>();
            for(String temp : str){
                roleList.add(Integer.parseInt(temp));
            }
            userVO.setAuthorities(roleList);
            userVOs.add(userVO);
        });
        int count = userService.getUserListTotalCount(page);
        return new JsonResponse(ResponseType.RESPONSE_OK, userVOs, count);
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
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        User user = userService.getUser(id);
        //只允许删除本诊所的数据
        if(user.getOrgId() == userDetails.getOrgId()) {
            userService.delete(id);
        }
        //清楚cache
        ((EduUserDetailService)userDetailsService).reloadUserByUserName(user.getUserName());
        return new JsonResponse(ResponseType.RESPONSE_OK, null, null);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResponse add(@RequestBody UserVO user){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        try {
            user.getAuthorities().sort((o1,o2)->o1.compareTo(o2));
            User userModel = new User();
            userModel.setOrgId(userDetails.getOrgId());
            userModel.setAuthorities(StringUtils.join(user.getAuthorities(), ","));
            userModel.setUserName(user.getUserName());
            userModel.setPhone(user.getPhone());
            userModel.setCreated(user.getCreated());
            userModel.setEmail(user.getEmail());
            userModel.setGender(user.getGender());
            userModel.setId(user.getId());
            userModel.setJob(user.getJob());
            userModel.setName(user.getName());
            //todo 目前密码是明文存储的, 后续需要改成加密存储的
            userModel.setPwd(user.getPwd());
            userService.add(userModel);
        }catch(Exception e){
            e.printStackTrace();
            return new JsonResponse(ResponseType.RESPONSE_ADD_USER_REPEAT, null, null);
        }
        return new JsonResponse(ResponseType.RESPONSE_OK, null, null);
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResponse update(@RequestBody UserVO user){
        try {
            //将权限进行去重和排序
            Iterator<Integer> it = user.getAuthorities().iterator();
            while(it.hasNext()){
                Integer temp = it.next();
                if(temp == null){
                    it.remove();
                }
            }
            user.getAuthorities().sort((o1,o2)->o1.compareTo(o2));
            User userModel = new User();
            userModel.setAuthorities(StringUtils.join(user.getAuthorities(), ","));
            userModel.setUserName(user.getUserName());
            userModel.setPhone(user.getPhone());
            userModel.setCreated(user.getCreated());
            userModel.setEmail(user.getEmail());
            userModel.setGender(user.getGender());
            userModel.setId(user.getId());
            userModel.setJob(user.getJob());
            userModel.setName(user.getName());
            userService.update(userModel);
            //删除缓存里面的用户信息, 目前这样只适合单机,多机器的时候,用户缓存最好放在redis里面
            ((EduUserDetailService)userDetailsService).reloadUserByUserName(user.getUserName());
        }catch(Exception e){
            e.printStackTrace();
            return new JsonResponse(ResponseType.RESPONSE_UPDATE_USER_REPEAT, null, null);
        }
        return new JsonResponse(ResponseType.RESPONSE_OK, null, null);
    }

    @ResponseBody
    @RequestMapping(value = "/pwd/update", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResponse updatePwd(@RequestParam("id")long id, @RequestParam("pwd")String pwd){
        try {
            userService.updatePwd(id, pwd);
        }catch(Exception e){
            e.printStackTrace();
            return new JsonResponse(ResponseType.RESPONSE_UPDATE_USER_REPEAT, null, null);
        }
        return new JsonResponse(ResponseType.RESPONSE_OK, null, null);
    }

}
