package com.recklessmo.util.wechat;

import com.recklessmo.model.wechat.WechatMsg;
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
import java.util.*;


/**
 * Created by hpf on 7/13/16.
 */
public class WechatUtils {

    public static String getDigestData(String src){
        return DigestUtils.sha1Hex(src);
    }


    /**
     *
     * 微信回调解析
     *
     * @param str
     * @return
     */
    public static WechatMsg parseWechatMsg(String str) {
        WechatMsg wechatMsg = new WechatMsg();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            StringReader sr = new StringReader(str);
            InputSource is = new InputSource(sr);
            Document document = db.parse(is);
            Element root = document.getDocumentElement();
            NodeList from = root.getElementsByTagName("FromUserName");
            NodeList to = root.getElementsByTagName("ToUserName");
            NodeList msgType = root.getElementsByTagName("MsgType");
            NodeList event = root.getElementsByTagName("Event");
            NodeList eventKey = root.getElementsByTagName("EventKey");
            NodeList ticket = root.getElementsByTagName("Ticket");
            NodeList content = root.getElementsByTagName("Content");
            NodeList picUrl = root.getElementsByTagName("PicUrl");
            NodeList format = root.getElementsByTagName("Format");
            NodeList mediaId = root.getElementsByTagName("MediaId");
            NodeList thumbMediaId = root.getElementsByTagName("ThumbMediaId");
            NodeList msgId = root.getElementsByTagName("MsgId");
            NodeList time = root.getElementsByTagName("CreateTime");
            if (from.getLength() != 0) {
                wechatMsg.setFromUserName(from.item(0).getTextContent());
            }
            if (to.getLength()!= 0) {
                wechatMsg.setToUserName(to.item(0).getTextContent());
            }
            if (msgType.getLength()!= 0) {
                wechatMsg.setMsgType(msgType.item(0).getTextContent());
            }
            if (event.getLength()!= 0) {
                wechatMsg.setEvent(event.item(0).getTextContent());
            }
            if (eventKey.getLength()!= 0) {
                wechatMsg.setEventKey(eventKey.item(0).getTextContent());
            }
            if (ticket.getLength()!= 0) {
                wechatMsg.setTicket(ticket.item(0).getTextContent());
            }
            if (content.getLength()!= 0) {
                wechatMsg.setContent(content.item(0).getTextContent());
            }
            if (picUrl.getLength()!= 0) {
                wechatMsg.setPicUrl(picUrl.item(0).getTextContent());
            }
            if (mediaId.getLength()!= 0) {
                wechatMsg.setMediaId(mediaId.item(0).getTextContent());
            }
            if (thumbMediaId.getLength()!= 0) {
                wechatMsg.setThumbMediaId(thumbMediaId.item(0).getTextContent());
            }
            if (msgId.getLength()!= 0) {
                wechatMsg.setMsgId(Long.parseLong(msgId.item(0).getTextContent()));
            }
            if (format.getLength()!= 0) {
                wechatMsg.setFormat(format.item(0).getTextContent());
            }
            if (time.getLength()!= 0) {
                wechatMsg.setCreateTime(Long.parseLong(time.item(0).getTextContent()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wechatMsg;
    }

    public static String getEncodedUrl(String msg) throws Exception{
        return URLEncoder.encode(msg, "UTF-8");
    }


    /**
     *
     * 获取随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length){
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String getSign(Object o, String secret) throws Exception{
        ArrayList<String> list = new ArrayList<>();
        Class cls = o.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.get(o) != null && f.get(o) != "") {
                list.add(f.getName() + "=" + f.get(o) + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + secret;
        result = MD5.MD5Encode(result).toUpperCase();
        return result;
    }

    public static InputStream getStringStream(String sInputString) {
        ByteArrayInputStream tInputStringStream = null;
        if (sInputString != null && !sInputString.trim().equals("")) {
            tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
        }
        return tInputStringStream;
    }

    public static String getIp() throws Exception{
        String localIp = null;// 本地IP，如果没有配置外网IP则返回它
        String netIp = null;// 外网IP
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            InetAddress ip = null;
            boolean finded = false;// 是否找到外网IP
            while (netInterfaces.hasMoreElements() && !finded) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = address.nextElement();
                    if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                        netIp = ip.getHostAddress();
                        finded = true;
                        break;
                    } else if (ip.isSiteLocalAddress()
                            && !ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                        localIp = ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if (netIp != null && !"".equals(netIp)) {
            return netIp;
        } else {
            return localIp;
        }
    }



    public static void main(String[] args)throws Exception{

        String encoding = System.getProperty("file.encoding");
        Charset charset = Charset.defaultCharset();
        System.out.println(charset.name());
        System.out.println(encoding);

    }

}
