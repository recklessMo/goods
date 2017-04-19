package com.recklessmo.dao.reward;

import com.recklessmo.model.graduate.Graduate;
import com.recklessmo.model.reward.Reward;
import com.recklessmo.web.webmodel.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hpf on 8/29/16.
 */
public interface RewardDAO {

    List<Reward> listReward(Page page);
    void addReward(Reward reward);
    void updateReward(Reward reward);
    void deleteReward(@Param("id") long id);

}
