package com.recklessmo.service.user;

import com.recklessmo.dao.passport.UserDAO;
import com.recklessmo.model.passport.User;
import com.recklessmo.webmodel.user.UserPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hpf on 4/13/16.
 */
@Service
public class UserService {

    @Resource
    private UserDAO userDAO;


    public List<User> getUserList(UserPage page){
        return userDAO.getAllUser(page);
    }

    public int getTotalCount(UserPage page){ return userDAO.getCount(page);}

    public User getUser(long id){
        return userDAO.getUser(id);
    }

    public void delete(long id){
        userDAO.deleteUser(id);
    }

    public void add(User user){
            userDAO.addUser(user);
    }

    public void update(User user){
        userDAO.updateUser(user);
    }

}
