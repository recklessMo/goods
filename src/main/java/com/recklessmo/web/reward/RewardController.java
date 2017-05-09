package com.recklessmo.web.reward;

import com.recklessmo.model.reward.Reward;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.model.student.StudentInfo;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.service.reward.RewardService;
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
@RequestMapping("/v1/reward")
@Controller
public class RewardController {

    @Resource
    private RewardService rewardService;

    @Resource
    private StudentService studentService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JsonResponse listReward(@RequestBody Page page){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        page.setOrgId(userDetails.getOrgId());
        List<Reward> rewardList = rewardService.getRewardList(page);
        return new JsonResponse(200, rewardList, null);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JsonResponse addReward(@RequestBody Reward reward){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        reward.setOrgId(userDetails.getOrgId());
        reward.setOpId(userDetails.getId());
        reward.setOpName(userDetails.getUsername());
        reward.setCreated(new Date());
        StudentInfo studentInfo = studentService.getStudentInfoBySid(userDetails.getOrgId(), reward.getSid());
        reward.setName(studentInfo.getName());
        rewardService.addReward(reward);
        return new JsonResponse(200, null, null);
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JsonResponse updateReward(@RequestBody Reward reward){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        reward.setOrgId(userDetails.getOrgId());
        rewardService.updateReward(reward);
        return new JsonResponse(200, null, null);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public JsonResponse deleteReward(@RequestBody long id){
        DefaultUserDetails userDetails = ContextUtils.getLoginUserDetail();
        rewardService.deleteReward(id);
        return new JsonResponse(200, null, null);
    }


}
