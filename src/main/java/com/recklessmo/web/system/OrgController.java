package com.recklessmo.web.system;

import com.recklessmo.model.student.StudentAddInfo;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by hpf on 2/20/17.
 */
@Controller
public class OrgController {


    @ResponseBody
    @RequestMapping(value = "/v1/org/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse orgList(@RequestBody Page page){


        return new JsonResponse(200, null, null);
    }
}
