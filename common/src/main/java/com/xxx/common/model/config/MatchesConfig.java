package com.xxx.common.model.config;

/**
 * 正则
 */
public class MatchesConfig {

    public static final String MATCHES_PHONE = "^[1][0-9]\\d{9}$";  //手机号规则 6为数字字母
    public static final String MATCHES_PASSWORD = "^(?=.*\\d)(?=.*[a-zA-Z])[\\da-zA-Z]{8,20}$";  //密码规则 必须同时包含字母和数字  而且是8-20位
    public static final String MATCHES_SMS_CODE = "^\\d{6}$";//验证码规则
    public static final String MATCHES_PAY_PASSWORD = "^\\d{6}$";  //资金密码规则 6为数字字母
//    public static final String MATCHER_ID_CARD = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$"; //身份证正则
    public static final String MATCHER_ID_CARD = "^\\w{18}$"; //身份证正则

}
