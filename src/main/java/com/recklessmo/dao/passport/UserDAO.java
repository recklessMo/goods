package com.recklessmo.dao.passport;

import com.recklessmo.model.passport.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hpf on 4/13/16.
 */
public interface UserDAO {

    void addUser(User user);
    void updateUser(User user);
    void deleteUser(long id);
    List<User> getAllUser();
    User getUser(long id);

    User getUserByUserName(@Param("userName") String userName);

}
