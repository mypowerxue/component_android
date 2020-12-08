package com.xxx.common.model.utils;

import android.annotation.SuppressLint;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串工具类
 */
public class StringUtil {

    /**
     * 手机号脱敏
     */
    public static String getPhoneCode(String phone) {
        if (phone != null && phone.trim().length() == 11) {
            StringBuilder sb = new StringBuilder(phone.trim());
            sb.replace(3, 7, "****");
            return sb.toString();
        }
        return phone;
    }

    public static String getRundNumber(double number, int n, String pattern) {
        if (pattern == null || pattern.isEmpty()) pattern = "########0.";
        String str = "";
        for (int j = 0; j < n; j++) str += "0";
        pattern += str;
        int m = (int) Math.pow(10, n);
        number = (Math.round(number * m)) / (m * 1.0);
        return new DecimalFormat(pattern).format(number);
    }

    /**
     * 获取时间
     */
    public static String getSimpleDataFormatTime(String pattern, long time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date(time));
    }

    /**
     * 获取币
     */
    public static String getMoney(double money) {
        if (money == 0) {
            return "0";
        }
        return new BigDecimal(String.valueOf(money)).setScale(4, RoundingMode.DOWN).stripTrailingZeros().toPlainString();
    }

    /**
     * 获取美元
     */
    public static String getUS(double money) {
        if (money == 0) {
            return "0";
        }
        return new BigDecimal(String.valueOf(money)).setScale(2, RoundingMode.DOWN).stripTrailingZeros().toPlainString();
    }

}
