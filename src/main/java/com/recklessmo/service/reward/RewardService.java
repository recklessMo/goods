package com.recklessmo.service.reward;

import com.recklessmo.dao.graduate.GraduateDAO;
import com.recklessmo.dao.reward.RewardDAO;
import com.recklessmo.model.graduate.Graduate;
import com.recklessmo.model.reward.Reward;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
@Service
public class RewardService {

    @Resource
    private RewardDAO rewardDAO;

    public List<Reward> getRewardList(Page page){
        return rewardDAO.listReward(page);
    }

    public void addReward(Reward reward){
        rewardDAO.addReward(reward);
    }

    public void updateReward(Reward reward){
        rewardDAO.updateReward(reward);
    }

    public void deleteReward(long id){
        rewardDAO.deleteReward(id);
    }

}
