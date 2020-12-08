package com.xxx.common.model.http.utils;

/**
 * 网络请求 需要特殊处理的返回值code
 */
public class ApiCode {

    public static final int RESPONSE_CODE_1 = 0;          //响应成功码
    public static final int RESPONSE_CODE_2 = 200;          //响应成功码
    public static final int SERVICE_ERROR_CODE = 500;          //服务器异常码


    /**
     * 特殊处理
     */
    public static final int UC_TOKEN_INVALID = 4000; //UC接口Token失效

    public static final int CT_TOKEN_INVALID = -1002; //CT接口Token失效

    public static final int PAY_NOT_SETTING = -1063; //未设置交易密码

}
