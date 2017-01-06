package com.recklessmo.web.menu;

import com.recklessmo.manage.menu.MenuManager;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.util.ContextUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by hpf on 6/10/16.
 */
@Controller
public class MenuController {

    private static Log logger = LogFactory.getLog(MenuController.class);

    /**
     *
     * 根据当前用户的权限获取系统菜单列表
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('login')")
    @ResponseBody
    @RequestMapping(value = "/v1/system/menu", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse menu(){
        DefaultUserDetails defaultUserDetails = ContextUtils.getLoginUserDetail();
        return new JsonResponse(200, MenuManager.getInstance().getMenus(defaultUserDetails), null);
    }


}
