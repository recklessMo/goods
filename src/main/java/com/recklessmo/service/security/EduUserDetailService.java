package com.recklessmo.service.security;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.recklessmo.dao.user.UserDAO;
import com.recklessmo.model.security.DefaultUserDetails;
import com.recklessmo.model.user.User;
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
public class EduUserDetailService implements UserDetailsService{

    @Resource
    private UserDAO userDAO;


    /**
     *
     * 只适用于单机加载用户数据, 当多台机器的时候可以采用redis等进行公共存储.
     *
     * 当用户权限被修改的时候, 直接刷新redis就行
     *
     * loading cache 很适合用于需要定期加载的项目
     *
     */
    private LoadingCache<String, Object> userCache = CacheBuilder.newBuilder().build(new CacheLoader<String, Object>() {
        public Object load(String userName) throws Exception{
            User user = userDAO.getUserByUserName(userName);
            if(user == null){
                throw new UsernameNotFoundException("user name not exist");
            }
            List<GrantedAuthority> authorities = new LinkedList<>();
            authorities.add(new SimpleGrantedAuthority("login"));
            String[] authorityList = user.getAuthorities().split(",");
            for(String auth : authorityList) {
                if (auth.trim().length() > 0) {
                    authorities.add(new SimpleGrantedAuthority(auth.trim()));
                }
            }
            return new DefaultUserDetails(user.getId(), user.getOrgId(), userName, user.getName(),  user.getPwd(), true, true, true, true, authorities, null);
        }
    });

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
        try {
            return (UserDetails) userCache.get(userName);
        }catch(Exception e){
            throw new UsernameNotFoundException("not exist");
        }
    }


    /**
     * 重新加载用户的权限列表, 在更新之后进行调用
     */
    public void reloadUserByUserName(String userName){
        userCache.invalidate(userName);
    }

}
