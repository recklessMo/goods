package com.recklessmo.dao.role;

import com.recklessmo.model.role.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hpf on 10/21/16.
 */
public interface RoleDAO {

    List<Role> listRoles();
    Role getRole(@Param("id") long id);
    void update(Role role);
    void create(Role role);
    void delete(@Param("id") long id);

}
