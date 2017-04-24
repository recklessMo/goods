package com.recklessmo.service.websocket;

import com.recklessmo.dao.user.UserDAO;
import com.recklessmo.model.user.User;
import com.recklessmo.web.webmodel.page.UserPage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hpf on 4/13/16.
 */
@Service
public class WebsocketService {

    @Resource
    private SimpMessagingTemplate messagingTemplate;

    /**
     *
     * 通过这个来通知对应的人 via websocket
     *
     * @param userId
     * @param type
     * @param data
     */
    public void sendMsg(long userId, String type, Object data){
        Map<String, Object> payLoad = new HashMap<>();
        payLoad.put("type", type);
        payLoad.put("data", data);
        messagingTemplate.convertAndSend("/websocket/notify/" + userId, payLoad);
    }

}
