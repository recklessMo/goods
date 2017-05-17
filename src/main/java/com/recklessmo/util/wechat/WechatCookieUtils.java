package com.recklessmo.util.wechat;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.Cookie;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Random;


/**
 * Created by hpf on 7/13/16.
 */
public class WechatCookieUtils {

    public static String getOpenIdByCookie(Cookie[] cookies){
        String openId = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    openId = cookie.getValue().substring(1, cookie.getValue().length() - 1);
                }
            }
        }
        return openId;
    }


    public static void main(String[] args)throws Exception{

        String encoding = System.getProperty("file.encoding");
        Charset charset = Charset.defaultCharset();
        System.out.println(charset.name());
        System.out.println(encoding);

    }

}
