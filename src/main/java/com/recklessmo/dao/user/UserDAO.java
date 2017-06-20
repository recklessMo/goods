package com.recklessmo.dao.user;

import com.recklessmo.model.user.User;
import com.recklessmo.web.webmodel.page.UserPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hpf on 4/13/16.
 */
public interface UserDAO {

    void addUser(User user);
    void updateUser(User user);
    void deleteUser(long id);
    List<User> getUserList(UserPage page);
    int getUserListTotalCount(UserPage page);
    User getUser(long id);
    User getUserByUserName(@Param("userName") String userName);
    void updatePwd(@Param("id")long id, @Param("pwd")String pwd);
    void insertUserList(@Param("userList")List<User> userList);

}
