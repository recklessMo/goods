package com.recklessmo.web.oplog;

import com.recklessmo.model.graduate.Graduate;
import com.recklessmo.model.oplog.OpLog;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.graduate.GraduateService;
import com.recklessmo.service.oplog.OpLogService;
import com.recklessmo.service.student.StudentService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
@RequestMapping("/v1/oplog")
@Controller
public class OpLogController {

    @Resource
    private OpLogService opLogService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JsonResponse listGraduate(@RequestBody Page page){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        page.setOrgId(userDetails.getOrgId());
        List<OpLog> opLogList = opLogService.getOpLogList(page);
        int count = opLogService.getOpLogListCount(page);
        return new JsonResponse(200, opLogList, count);
    }

}
