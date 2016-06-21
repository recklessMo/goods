package com.recklessmo.service.passport;

import com.recklessmo.dao.passport.UserDAO;
import com.recklessmo.model.passport.User;
import com.recklessmo.webmodel.user.UserPage;
import org.springframework.stereotype.Service;
import java.util.List;

import javax.annotation.Resource;

/**
 * Created by hpf on 4/13/16.
 */
@Service
public class PassportService {

    @Resource
    private UserDAO userDAO;


    public List<User> getUserList(){
        UserPage page = new UserPage();
        return userDAO.getAllUser(page);
    }

}
