package com.recklessmo.web.role;

import com.recklessmo.model.exam.Exam;
import com.recklessmo.model.role.Role;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.Exam.ExamService;
import com.recklessmo.service.role.RoleService;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

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
        return new JsonResponse(200, roleList, null);
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public JsonResponse getRole(@RequestBody long id){
        Role role = roleService.getRole(id);
        return new JsonResponse(200, role, null);
    }

    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public JsonResponse create(@RequestBody Role role){
        roleService.create(role);
        return new JsonResponse(200, null, null);
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
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
