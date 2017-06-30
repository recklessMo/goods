package com.recklessmo.dao.message;

import com.recklessmo.model.message.InformMessage;
import com.recklessmo.web.webmodel.page.InformListPage;
import com.recklessmo.web.webmodel.page.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.velocity.util.introspection.Info;

import java.util.List;

/**
 * Created by hpf on 2/10/17.
 */
public interface InformMessageDAO {

    void addInformMessage(InformMessage message);
    List<InformMessage> getInformMessageList(Page page);
    int getInformMessageCount(Page page);
    void deleteInformMessage(@Param("orgId")long orgId, @Param("id")long id);

    List<InformMessage> getInformMessageListByGrade(InformListPage page);
    InformMessage getInformMessageById(@Param("orgId")long orgId, @Param("id")long id);
}
