package com.xxx.myapplication.main;

import com.xxx.common.mvp.BasePresenter;
import com.xxx.common.mvp.BaseView;

public interface MainContract {

    interface IPresenter extends BasePresenter<IView> {

        void checkVersion();   //检测App版本

    }

    interface IView extends BaseView {

        void showNeedUpdate(String url); //展示需要更新

    }

}
