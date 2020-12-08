package com.xxx.myapplication;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.xxx.common.ui.utils.LocalManageUtil;
import com.xxx.login.LoginApp;

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    protected void attachBaseContext(Context base) {
        context = base;
        super.attachBaseContext(LocalManageUtil.setLocal(base));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //初始化模块
        LoginApp.init(context);
    }

    public static Context getContext() {
        return context;
    }

    //设置不跟随系统的字体变大而变大
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1) getResources();
        // 当切换横竖屏 重置语言
        LocalManageUtil.setLocal(getApplicationContext());
        super.onConfigurationChanged(newConfig);
    }

}
