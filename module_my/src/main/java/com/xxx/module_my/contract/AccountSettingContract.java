package com.xxx.module_my.contract;

import com.xxx.common.mvp.BasePresenter;
import com.xxx.common.mvp.BaseView;

public interface AccountSettingContract {

    interface IPresenter extends BasePresenter<IView> {

        void login(String account, String password);   //登录

        void register(String account, String phone, String smsCode,
                      String password, String country, String promotion);  //注册

        void sendSMSCode(String phone, String country); //发送短信验证码

    }

    interface IView extends BaseView {

        void showLoginSuccess(); //展示登录成功

        void showRegisterSuccess(String account, String password); //展示注册成功

        void showDownTime(String nowTime); //展示倒计时

        void closeDownTime(); //关闭倒计时

    }
}
