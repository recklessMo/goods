package com.recklessmo.web.webscoket;

import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.model.user.User;
import com.recklessmo.model.user.UserVO;
import com.recklessmo.response.JsonResponse;
import com.recklessmo.response.ResponseType;
import com.recklessmo.service.mail.MailService;
import com.recklessmo.service.security.EduUserDetailService;
import com.recklessmo.service.user.UserService;
import com.recklessmo.service.websocket.WebsocketService;
import com.recklessmo.util.ContextUtils;
import com.recklessmo.web.webmodel.page.UserPage;
import org.apache.commons.lang.StringUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by hpf on 6/16/16.
 */
@Controller
@RequestMapping("/websocket")
public class WebsocketController {

    @Resource
    private WebsocketService websocketService;

    @ResponseBody
    @RequestMapping(value = "/send", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResponse sendMessage(@RequestParam("id")long userId){
        websocketService.sendMsg(userId, "xxx", "yyy");
        return new JsonResponse(200, null, null);
    }

}
