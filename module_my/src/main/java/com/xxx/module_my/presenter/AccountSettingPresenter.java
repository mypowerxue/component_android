package com.xxx.module_my.presenter;

import android.content.Context;

import com.xxx.common.model.GlobalData;
import com.xxx.common.model.http.Api;
import com.xxx.common.model.http.ApiCallback;
import com.xxx.common.model.http.bean.LoginBean;
import com.xxx.common.model.sp.SharedConst;
import com.xxx.common.model.sp.SharedPreferencesUtil;
import com.xxx.common.model.utils.MD5Util;
import com.xxx.common.model.utils.SystemUtil;
import com.xxx.common.ui.config.UIConfig;
import com.xxx.common.ui.utils.DownTimeUtil;
import com.xxx.login.LoginApp;
import com.xxx.login.contract.LoginContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AccountSettingPresenter implements LoginContract.IPresenter {

    private LoginContract.IView mView;
    private DownTimeUtil mDownTimeUtil;

    @Override
    public void login(final String account, String password) {
        //access-token
        GlobalData.accessToken = MD5Util.getMD5(SystemUtil.getSerialNumber() + SystemUtil.getUUID());

        Api.getInstance().login(account, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<LoginBean>() {

                    @Override
                    public void onSuccess(LoginBean bean) {
                        if (mView != null) {
                            Context context = LoginApp.getContext();
                            SharedPreferencesUtil util = SharedPreferencesUtil.getInstance(context);

                            GlobalData.xToken = bean.getToken();
                            GlobalData.userId = bean.getId();
                            GlobalData.userName = bean.getUsername();
                            GlobalData.userPhone = account;
                            GlobalData.inviteCode = bean.getPromotionCode();
                            //账号信息
                            LoginBean.CountryBean country = bean.getCountry();
                            if (country != null) {
                                GlobalData.countyCity = country.getZhName();
                                GlobalData.countyCode = country.getAreaCode();
                            }

                            util.saveBoolean(SharedConst.IS_LOGIN, true);
                            util.saveEncryptString(context, SharedConst.ENCRYPT_VALUE_TOKEN_1, GlobalData.xToken);
                            util.saveEncryptString(context, SharedConst.ENCRYPT_VALUE_TOKEN_2, GlobalData.accessToken);
                            util.saveInt(SharedConst.VALUE_USER_ID, GlobalData.userId);
                            util.saveString(SharedConst.VALUE_USER_NAME, GlobalData.userName);
                            util.saveString(SharedConst.VALUE_USER_PHONE, GlobalData.userPhone);
                            util.saveString(SharedConst.VALUE_INVITE_CODE, GlobalData.inviteCode);
                            util.saveString(SharedConst.VALUE_COUNTY_CITY, GlobalData.countyCity);
                            util.saveString(SharedConst.VALUE_COUNTY_CODE, GlobalData.countyCode);

                            mView.showLoginSuccess();
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        if (mView != null) {
                            mView.showError(errorCode, errorMessage);
                        }
                    }

                    @Override
                    protected void onStart() {
                        if (mView != null) {
                            mView.showLoading();
                        }
                    }

                    @Override
                    public void onEnd() {
                        if (mView != null) {
                            mView.hideLoading();
                        }
                    }
                });
    }

    @Override
    public void register(final String account, String phone, String smsCode, final String password, String country, String promotion) {
        Api.getInstance().register(account, phone, smsCode, password, country, promotion)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>() {

                    @Override
                    public void onSuccess(Object bean) {
                        if (mView != null) {
                            mView.showRegisterSuccess(account, password);
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        if (mView != null) {
                            mView.showError(errorCode, errorMessage);
                        }
                    }

                    @Override
                    protected void onStart() {
                        if (mView != null) {
                            mView.showLoading();
                        }
                    }

                    @Override
                    public void onEnd() {
                        if (mView != null) {
                            mView.hideLoading();
                        }
                    }
                });
    }

    @Override
    public void sendSMSCode(String phone, String country) {
        if (mDownTimeUtil != null) {
            return;
        }
        mDownTimeUtil = DownTimeUtil.getInstance(new DownTimeUtil.Callback() {
            @Override
            public void run(int nowTime) {
                if (mView != null) {
                    mView.showDownTime(nowTime + "s");
                }
            }

            @Override
            public void end() {
                if (mView != null) {
                    mView.closeDownTime();
                }
                mDownTimeUtil = null;
            }
        });

        Api.getInstance().sendSMSCode(phone, country)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>() {

                    @Override
                    public void onSuccess(Object bean) {
                        if (mView != null) {
                            mDownTimeUtil.openDownTime(UIConfig.SMS_CODE_DOWN_TIME);
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        if (mView != null) {
                            mView.showError(errorCode, errorMessage);
                        }
                    }

                    @Override
                    protected void onStart() {
                        if (mView != null) {
                            mView.showLoading();
                        }
                    }

                    @Override
                    public void onEnd() {
                        if (mView != null) {
                            mView.hideLoading();
                        }
                    }
                });
    }

    @Override
    public void attachView(LoginContract.IView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        if (this.mDownTimeUtil != null) {
            this.mDownTimeUtil.closeDownTime();
            this.mDownTimeUtil = null;
        }
    }

}
