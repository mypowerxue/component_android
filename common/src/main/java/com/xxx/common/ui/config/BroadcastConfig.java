package com.xxx.common.ui.config;

import com.xxx.common.BuildConfig;

public class BroadcastConfig {

    public static final String ACTION_INIT = BuildConfig.APPLICATION_ID + "broadcast_init";  //初始化通知

    public static final String ACTION_CHAT_RECEIVE = BuildConfig.APPLICATION_ID + "broadcast_chat_receive";  //聊天接收的通知

    public static final String ACTION_CHAT_SEND = BuildConfig.APPLICATION_ID + "broadcast_chat_send";  //聊天发送的通知

    public static final String ACTION_CHAT_REFRESH = BuildConfig.APPLICATION_ID + "broadcast_chat_refresh";  //刷新接收聊天页面通知

}
