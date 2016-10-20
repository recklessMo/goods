package com.recklessmo.service.security;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.recklessmo.dao.user.UserDAO;
import com.recklessmo.model.user.User;
import com.recklessmo.model.security.DefaultUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 6/11/16.
 *
 * provide user service
 *
 */
@Service
public class UserDetailService implements UserDetailsService{

    @Resource
    private UserDAO userDAO;


    /**
     * loading cache 很适合用于需要定期加载的项目
     */
    private LoadingCache<String, Object> userCache = CacheBuilder.newBuilder().build(new CacheLoader<String, Object>() {
        public Object load(String userName) throws Exception{

            User user = userDAO.getUserByUserName(userName);
            if(user == null){
                throw new UsernameNotFoundException("user name not exist");
            }

            List<GrantedAuthority> authorities = new LinkedList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            List<String> roles = new LinkedList<String>();
            roles.add("ROLE_USER");

            return new DefaultUserDetails(user.getId(), userName, user.getPwd(), true, true, true, true, authorities, roles);
        }
    });

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        try {
            UserDetails userDetails = (UserDetails) userCache.get(username);
            return userDetails;
        }catch(Exception e){
            throw new UsernameNotFoundException("not exist");
        }
    }


}
