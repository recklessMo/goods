package com.recklessmo.dao.message;

import com.recklessmo.model.message.InformMessage;
import com.recklessmo.web.webmodel.page.Page;

import java.util.List;

/**
 * Created by hpf on 2/10/17.
 */
public interface InformMessageDAO {

    void addInformMessage(InformMessage message);
    List<InformMessage> getInformMessageList(Page page);
    int getInformMessageCount(Page page);

}
