package com.recklessmo.model.wechat;

/**
 *
 * 微信回调消息的封装接口, 通过解析xml来获取结果
 */
public class WechatCallbackMsg {

    private String ToUserName;
    private String FromUserName;
    private long CreateTime;
    //通过下面两个字段确定唯一的类型
    private String MsgType;
    private String Event;
    //这两个字段确定是否是直接关注还是扫码关注
    private String EventKey;
    private String Ticket;
    //下面这些属于聊天消息
    private String Content;
    private long MsgId;
    private String PicUrl;
    private String MediaId;
    private String Format;
    private String ThumbMediaId;


    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }

    public String getTicket() {
        return Ticket;
    }

    public void setTicket(String ticket) {
        Ticket = ticket;
    }

    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }


    public long getMsgId() {
        return MsgId;
    }

    public void setMsgId(long msgId) {
        MsgId = msgId;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("WechatMsg: MsgType:");
        sb.append(MsgType);
        if(Event != null) {
            sb.append(",");
            sb.append("Event:");
            sb.append(Event);
        }
        if(EventKey != null) {
            sb.append(",");
            sb.append("EventKey:");
            sb.append(EventKey);
        }
        if(FromUserName != null) {
            sb.append(",");
            sb.append("FromUserName:");
            sb.append(FromUserName);
        }
        if(ToUserName != null) {
            sb.append(",");
            sb.append("ToUserName:");
            sb.append(ToUserName);
        }
        if(Ticket != null) {
            sb.append(",");
            sb.append("Ticket:");
            sb.append(Ticket);
        }
        if(Content != null){
            sb.append(",");
            sb.append("Content:");
            sb.append(Content);
        }
        if(MsgId != 0){
            sb.append(",");
            sb.append("MsgId:");
            sb.append(MsgId);
        }
        if(MediaId != null){
            sb.append(",");
            sb.append("MediaId:");
            sb.append(MediaId);
        }
        if(ThumbMediaId != null) {
            sb.append(",");
            sb.append("ThumbMediaId:");
            sb.append(ThumbMediaId);
        }
        if(PicUrl != null){
            sb.append(",");
            sb.append("PicUrl:");
            sb.append(PicUrl);
        }
        if(Format != null){
            sb.append(",");
            sb.append("Format:");
            sb.append(Format);
        }
        if(CreateTime != 0) {
            sb.append(",");
            sb.append("CreateTime:");
            sb.append(CreateTime);
        }
        sb.append(".");
        return sb.toString();
    }
}
