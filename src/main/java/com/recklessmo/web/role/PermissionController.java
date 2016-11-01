package com.recklessmo.web.role;

import com.recklessmo.manage.menu.MenuManager;
import com.recklessmo.model.role.Permissions;
import com.recklessmo.response.JsonResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

/**
 * Created by hpf on 10/21/16.
 */
@Controller
@RequestMapping("/v1/permission")
public class PermissionController {

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JsonResponse listPermissions(){
        List<Permissions> permissionsList = MenuManager.getInstance().getPermissions();
        return new JsonResponse(200, permissionsList, null);
    }


}
