package com.recklessmo.util;

import com.recklessmo.model.security.DefaultUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by hpf on 4/13/16.
 */
public class ContextUtils {

    public static DefaultUserDetails getLoginUserDetail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            return null;
        }
        Object object = authentication.getPrincipal();
        if(object instanceof  DefaultUserDetails){
            return (DefaultUserDetails)object;
        }
        return null;
    }

}
