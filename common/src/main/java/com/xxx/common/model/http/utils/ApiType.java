package com.xxx.common.model.http.utils;

/**
 * 网络请求 返回值type
 */
public class ApiType {

    public static final String HOME_LOCATION = "0";   //轮播图位置

    //钱包列表是否开启兑换
    public static final int OPEN_EXCHANGE_TYPE = 1;

    //钱包列表是否开启理财
    public static final int CLOSE_FINANC_TYPE = 0;
    public static final int OPEN_FINANC_TYPE = 1;


    //理财记录type
    public static final int DEPOSIT_RECORD_IN_TYPE = 0;
    public static final int DEPOSIT_RECORD_OUT_TYPE = 1;

    //成人 儿童
    public static final int ADD_PERSON_PERSON = 0;
    public static final int ADD_PERSON_CHILD = 1;

    //性别type
    public static final int ACCOUNT_SEX_MAN = 0;
    public static final int ACCOUNT_SEX_WOMAN = 1;


    //关系type
    public static final int RATIO_PARENT = 0;
    public static final int RATIO_GRAND_PARENT = 1;
    public static final int RATIO_BROTHER = 2;
    public static final int RATIO_OTHER = 3;

    //地址默认
    public static final int ADDRESS_DEFAULT = 1;


    //买入卖出
    public static final String COMMUNITY_BUY_TYPE = "BUY";
    public static final String COMMUNITY_SELL_TYPE = "SELL";


    //个人套餐  家庭套餐
    public static final int PERSONAL_MEAL_TYPE = 0;
    public static final int FAMILY_MEAL_TYPE = 1;

    //套餐项目列表
    public static final int SHOP_MEAL_PROJECT_1 = 1;    //基因身份证
    public static final int SHOP_MEAL_PROJECT_2 = 2;  //基因身份证
    public static final int SHOP_MEAL_PROJECT_3 = 3;  //防癌基因检测全套
    public static final int SHOP_MEAL_PROJECT_4 = 4;  //女性肌肤抗衰老基因检测
    public static final int SHOP_MEAL_PROJECT_5 = 5;  //酒精代谢能力基因检测
    public static final int SHOP_MEAL_PROJECT_6 = 6;  //儿童常见病基因检测套餐
    public static final int SHOP_MEAL_PROJECT_7 = 7;  //儿童天赋基因检测套餐


    //首页轮播  社群轮播
    public static final int BANNER_SHOP = 0;
    public static final int BANNER_COMMUNITY = 1;
}