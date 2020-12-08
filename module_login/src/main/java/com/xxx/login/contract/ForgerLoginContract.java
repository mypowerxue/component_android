package com.xxx.login.contract;

import com.xxx.common.mvp.BasePresenter;
import com.xxx.common.mvp.BaseView;

public interface ForgerLoginContract {

    interface IPresenter extends BasePresenter<IView> {

        void forgerPsw(String account, String smsCode, String password, String mode);   //忘记密码

        void sendSMSCode(String phone); //发送短信验证码
    }

    interface IView extends BaseView {

        void showSuccess(String account, String password); //展示忘记密码成功

        void showDownTime(String nowTime); //展示倒计时

        void closeDownTime(); //关闭倒计时
    }
}
