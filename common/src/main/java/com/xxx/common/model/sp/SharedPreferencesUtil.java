package com.xxx.common.model.sp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.xxx.common.BuildConfig;

@SuppressLint("NewApi")
public class SharedPreferencesUtil {

    private static SharedPreferencesUtil sharedPreferencesUtils;

    private SharedPreferences sharedPreferences;

    private SharedPreferencesUtil(Context context) {
        String name = BuildConfig.APPLICATION_ID + "_sp";
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesUtil getInstance(Context context) {
        if (sharedPreferencesUtils == null) {
            synchronized (SharedPreferencesUtil.class) {
                if (sharedPreferencesUtils == null) {
                    sharedPreferencesUtils = new SharedPreferencesUtil(context);
                }
            }
        }
        return sharedPreferencesUtils;
    }


    /**
     * 保存
     */
    public void saveEncryptString(Context context, String tag, String content) {
        saveString(tag, SpEncryption.getInstance().encryptString(context, content, BuildConfig.APPLICATION_ID));
    }


    public void saveString(String tag, String content) {
        if (tag != null && content != null) {
            sharedPreferences.edit().putString(tag, content).apply();
        }
    }

    public void saveInt(String tag, int content) {
        if (tag != null) {
            sharedPreferences.edit().putInt(tag, content).apply();
        }
    }

    public void saveBoolean(String tag, Boolean boo) {
        if (tag != null && boo != null) {
            sharedPreferences.edit().putBoolean(tag, boo).apply();
        }
    }

    /**
     * 获取
     */
    public String getDecryptionString(Context context, String tag) {
        return SpEncryption.getInstance().decryptString(context, getString(tag), BuildConfig.APPLICATION_ID);
    }

    public String getString(String tag) {
        if (tag != null) {
            return sharedPreferences.getString(tag, "");
        }
        return "";
    }

    public int getInt(String tag) {
        if (tag != null) {
            return sharedPreferences.getInt(tag, 0);
        }
        return 0;
    }

    public Boolean getBoolean(String tag) {
        return tag != null && sharedPreferences.getBoolean(tag, false);
    }

    /**
     * 清除
     */
    public void cleanAll() {
        sharedPreferences.edit().clear().apply();
    }
}
