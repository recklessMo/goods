package com.recklessmo.service.passport;

import com.recklessmo.dao.passport.UserDAO;
import com.recklessmo.model.passport.User;
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
        return userDAO.getAllUser();
    }

}
