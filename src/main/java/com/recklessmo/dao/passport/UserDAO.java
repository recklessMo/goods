package com.recklessmo.dao.passport;

import com.recklessmo.model.passport.User;
import java.util.List;

/**
 * Created by hpf on 4/13/16.
 */
public interface UserDAO {

    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(long id);
    List<User> getAllUser();

}
