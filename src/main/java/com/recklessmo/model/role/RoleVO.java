package com.recklessmo.model.role;

/**
 * Created by yyq on 17/6/21.
 */
import javax.xml.soap.Detail;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 4/18/16.
 */
public class RoleVO {


    private long id;
    private String roleName;
    private String roleDetail;
    private List<Integer> permissions = new LinkedList<>();
    private Date created;
    private Date updated;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }


    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


    public String getRoleDetail() {
        return roleDetail;
    }
    public void setRoleDetail(String Detail) {
        this.roleDetail = roleDetail;
    }


    public List<Integer> getAuthorities() {
        return permissions;
    }
    public void setAuthorities(List<Integer> authorities) {
        this.permissions = authorities;
    }


    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }
    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}




