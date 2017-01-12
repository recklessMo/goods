package com.recklessmo.constant;


/**
 *
 */
public class WechatConstants {

    /*for wechat*/
    public static String APPID = "wx6c4976042567bf58";
    public static String APPSECRET = "e1d8589c975c5627e4dc45d9b24b54ce";
    public static String domainName = "115.28.49.103";
    public static long GAP = 1000 * 3600;
    public static long lastUpdateTime = 0;





    /*message for user*/
    public static String BIND_SUCCESS = "您好，感谢您的关注；您可以发送文字、图片、视频咨询医生，或者点击右下角查看诊疗档案。";
    public static String SELF_BIND_SUCCESS = "绑定成功! 您现在可以发送文字、图片、视频咨询医生，或者点击右下角查看诊疗档案。";
    public static String UN_BIND_MESSAGE = "您好，感谢您的关注；因为您未绑定就诊记录，所以咨询医生以及查看档案功能无法使用，如需使用，请到相应诊所绑定就诊记录或者点击'我的档案'进行自助绑定。";
    public static String OUT_WORK_TIME = "您好，诊所已经收到您的消息，不过现在医生休息啦；我们会在9:00工作时间之后给您回复。如果您有紧急的问题，建议到就近的医院急诊处理。";

    /*message for admin*/
    public static String SUB_SUCCESS = "关注成功, 您可以点击右下角菜单绑定诊所!";


    public static int WORK_HOUR_END = 21;
    public static int WORK_HOUR_START = 9;

    public static String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    public static String GET_NET_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    public static String GET_QR_CODE_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";

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


    /**
     * 支付返回状态
     */
    public static int PAY_SUCCESS = 0;
    public static int PAY_FAIL = 1;
    public static int PAY_ING = 2;


    public static int reverseStatusOk = 9999;
    public static String reverseOK = "订单支付失败并且已经撤销, 用户将不能再支付, 已经支付完成的钱将会自动退款!";

    public static int reverseStatusFail = 9998;
    public static String reverseFail = "订单支付失败但是撤销失败, 如果用户已经支付请联系技术人员进行退款!";

}
