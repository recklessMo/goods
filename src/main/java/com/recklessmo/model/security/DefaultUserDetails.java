package com.recklessmo.model.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Created by hpf on 6/12/16.
 */
public class DefaultUserDetails implements UserDetails{

    private long id;
    private long orgId;
    private String userName;
    private String pwd;
    private String name;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialNonExpired;
    private boolean enabled;
    private Collection<GrantedAuthority> authorityList;
    private List<String> roles;


    public DefaultUserDetails(long id, long orgId, String userName, String name,  String pwd, boolean accountNonExpired, boolean accountNonLocked, boolean credentialNonExpired, boolean enabled, Collection<GrantedAuthority> authorityList, List<String> roles){
        this.id = id;
        this.orgId = orgId;
        this.userName = userName;
        this.name = name;
        this.pwd = pwd;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialNonExpired = credentialNonExpired;
        this.enabled = enabled;
        this.authorityList = authorityList;
        this.roles = roles;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public String getPassword() {
        return pwd;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public List<String> getRoles() {
        return roles;
    }

}
