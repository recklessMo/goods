package com.recklessmo.service.role;

import com.recklessmo.dao.exam.ExamDAO;
import com.recklessmo.dao.role.RoleDAO;
import com.recklessmo.model.exam.Exam;
import com.recklessmo.model.role.Role;
import com.recklessmo.web.webmodel.page.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * 2016/10/21
 *
 */
@Service
public class RoleService {

    @Resource
    private RoleDAO roleDAO;

    public List<Role> listRoles(){
        return roleDAO.listRoles();
    }

    public Role getRole(long id){
        return roleDAO.getRole(id);
    }

    public void create(Role role){
        roleDAO.create(role);
    }

    public void update(Role role){ roleDAO.update(role); }

    public void delete(long id){
        roleDAO.delete(id);
    }


}
