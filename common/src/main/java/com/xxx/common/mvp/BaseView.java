package com.xxx.common.mvp;

public interface BaseView {

    //展示加载进度方法
    void showLoading();

    //关闭加载进度方法
    void hideLoading();

    //展示失败
    void showError(int errorCode, String errorMessage);

}

