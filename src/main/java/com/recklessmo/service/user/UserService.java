package com.recklessmo.service.user;

import com.recklessmo.dao.user.UserDAO;
import com.recklessmo.model.user.User;
import com.recklessmo.web.webmodel.page.UserPage;
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
        return userDAO.getUserList(page);
    }

    public int getUserListTotalCount(UserPage page){ return userDAO.getUserListTotalCount(page);}

    public User getUser(long id){
        return userDAO.getUser(id);
    }

    public User getUserByUserName(String userName){
        return userDAO.getUserByUserName(userName);
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

    public void updatePwd(long id, String pwd){
        userDAO.updatePwd(id, pwd);
    }

    public boolean existUser(String userName){
        User user = getUserByUserName(userName);
        return user != null;
    }

    public void insertUserList(List<User> userList){
        userDAO.insertUserList(userList);
    }

}
