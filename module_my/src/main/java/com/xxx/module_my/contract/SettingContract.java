package com.xxx.module_my.contract;

import com.xxx.common.mvp.BasePresenter;
import com.xxx.common.mvp.BaseView;

public interface SettingContract {

    interface IPresenter extends BasePresenter<IView> {

        void checkVersion();   //检测版本

    }

    interface IView extends BaseView {

        void showNeedUpdate(String url); //展示需要更新

    }
}
