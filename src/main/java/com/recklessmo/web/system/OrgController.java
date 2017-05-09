package com.recklessmo.web.system;

import com.recklessmo.model.system.Org;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.system.OrgService;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hpf on 2/20/17.
 */
@Controller
public class OrgController {


    @Resource
    private OrgService orgService;

    @ResponseBody
    @RequestMapping(value = "/v1/org/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse orgList(@RequestBody Page page){
        List<Org>  orgList = orgService.listOrg(page);
        int count = orgService.listOrgCount(page);
        return new JsonResponse(200, orgList, count);
    }

}
