package com.recklessmo.util.wechat;

import com.recklessmo.model.wechat.WechatCallbackMsg;
import com.recklessmo.model.wechat.WechatMessage;
import org.apache.commons.codec.digest.DigestUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
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
 *
 * 构造常用的消息
 *
 */
public class WechatMessageUtils {


    /**
     * 构造自动回复消息
     * @return
     */
    public static WechatMessage makeAutoReplyMessage(){
        WechatMessage wechatMessage = new WechatMessage();
        return wechatMessage;
    }

    /**
     * 构造不支持消息的回复
     * @return
     */
    public static WechatMessage makeNoSupportMessage(){
        WechatMessage wechatMessage = new WechatMessage();
        return wechatMessage;
    }

    /**
     *构造不在上班时间的回复
     * @return
     */
    public static WechatMessage makeNotOnWorkMessage(){
        WechatMessage wechatMessage = new WechatMessage();
        return wechatMessage;
    }




    public static void main(String[] args)throws Exception{

        String encoding = System.getProperty("file.encoding");
        Charset charset = Charset.defaultCharset();
        System.out.println(charset.name());
        System.out.println(encoding);

    }

}
