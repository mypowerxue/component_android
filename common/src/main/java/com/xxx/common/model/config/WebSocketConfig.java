package com.xxx.common.model.config;

public class WebSocketConfig {

    //参数key
    public static final String TYPE = "type";     //状态
    public static final String MESSAGE = "message";     //内容
    public static final String DATA = "data";     //对象

    //链接状态
    public static final int OPEN_STATUS = 1;      //开启
    public static final int MESSAGE_STATUS = 2;   //消息
    public static final int CLOSE_STATUS = 3;     //关闭
    public static final int ERROR_STATUS = 4;     //错误

    //消息类型
    public static final int SEND_SINGLE_TYPE = 1;      //发送单聊
    public static final int SEND_GROUP_TYPE = 2;   //发送群聊
    public static final int SEND_NOTICE_TYPE = 3;     //发送通知

    //内容类型
    public static final int MESSAGE_TEXT_TYPE = 1;      //发送文字
    public static final int MESSAGE_IMAGE_TYPE = 2;   //发送图片
    public static final int MESSAGE_VOICE_TYPE = 3;     //发送语音

    //查阅状态
    public static final int ALREADY_STATUS = 1;      //已读
    public static final int UNREAD_STATUS = 2;      //未读

}
