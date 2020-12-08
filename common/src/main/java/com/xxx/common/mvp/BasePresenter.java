package com.xxx.common.mvp;

public interface BasePresenter<T extends BaseView> {

    //获取Presenter方法
    void attachView(T view);

    //关闭Presenter方法
    void detachView();

}

