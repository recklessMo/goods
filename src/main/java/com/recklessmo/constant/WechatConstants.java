package com.recklessmo.constant;


/**
 *
 */
public class WechatConstants {

    public static String TOKEN = "wechatyunxiaoyuan";

    /*for wechat*/
    public static String APPID = "wx6c4976042567bf58";
    public static String APPSECRET = "e1d8589c975c5627e4dc45d9b24b54ce";
    public static String domainName = "http://www.school-cloud.cn";
    public static long GAP = 1000 * 3600;

    /*message for user*/
    public static String WELCOME_DEFAULT = "[系统消息]您好, 感谢您的关注!";
    public static String UNBIND_RETURN = "[系统消息]您好，您还未进行绑定，系统无法处理您的消息! 请点击菜单栏进行绑定！";
    public static String BIND_SUCCESS = "[系统消息]您好，感谢您的关注；您可以发送文字咨询老师，或者点击菜单查看学生信息和成绩!。";
    public static String UNSUPPORTED_MESSAGE = "[系统消息]您好，目前只支持文本消息！";


    public static String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    public static String GET_NET_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    public static String GET_QR_CODE_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%s";

    public static String GET_HEAD_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";

    public static String SEND_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";

    public static String GET_MEDIA_NONVIDEO_URL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";

    public static String GET_MEDIA_VIDEO_URL = "http://api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";

    public static String GET_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?";

    public static String GET_MEDIA_LIST = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=";

    public static String SEND_TEMPLATE_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

    public static String GET_TEMPLATE_ID_URL = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=";

    //以下是几个API的路径：
    //1）被扫支付API
    public static String PAY_API = "https://api.mch.weixin.qq.com/pay/micropay";
    //2）被扫支付查询API
    public static String PAY_QUERY_API = "https://api.mch.weixin.qq.com/pay/orderquery";
    //3）退款API
    public static String REFUND_API = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    //4）退款查询API
    public static String REFUND_QUERY_API = "https://api.mch.weixin.qq.com/pay/refundquery";
    //5）撤销API
    public static String REVERSE_API = "https://api.mch.weixin.qq.com/secapi/pay/reverse";
    //6）下载对账单API
    public static String DOWNLOAD_BILL_API = "https://api.mch.weixin.qq.com/pay/downloadbill";
    //7) 统计上报API
    public static String REPORT_API = "https://api.mch.weixin.qq.com/payitil/report";


}
