package com.xxx.common.model.config;

public class HttpConfig {

    /**
     * 服务器地址
     */
    public static final String BASE_URL = "https://huayin.land/";   //正式服务器地址
    //public static final String BASE_URL = "https://torchex.global";   //测试服务器地址

//    public static final String SOCKET_URL = "https://torchex.global";   //正式WebSocket地址
    public static final String SOCKET_URL = "http://192.168.31.96:8199/webSocket/";   //测试WebSocket地址


    /**
     * 网络请求基本参数配置
     */
    public static final long CACHE_TIME = 2 * 60 * 60; //缓存时间 毫秒
    public static final long HTTP_TIME_OUT = 10 * 1000; //网络请求超时时间配置 毫秒
    public static final String HTTP_CONVERSION = "conversion"; //转向Base标注
    public static final String INVITE_URL = "https://huayin.land/download/register.html?lang=cn&avatar=";   //WebSocket地址
    public static final String AGREEMENT_URL = "https://huayin.land/autograph/index.html";   //签名链接
//    public static final String AGREEMENT_URL = "http://192.168.31.120:8848/unicoin/download/autograph/index.html";   //签名链接

}
