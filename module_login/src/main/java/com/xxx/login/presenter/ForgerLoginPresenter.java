package com.xxx.login.presenter;

import com.xxx.common.model.http.Api;
import com.xxx.common.model.http.ApiCallback;
import com.xxx.common.ui.config.UIConfig;
import com.xxx.common.ui.utils.DownTimeUtil;
import com.xxx.login.contract.ForgerLoginContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ForgerLoginPresenter implements ForgerLoginContract.IPresenter {

    private ForgerLoginContract.IView mView;
    private DownTimeUtil mDownTimeUtil;

    @Override
    public void forgerPsw(final String account, final String smsCode, String password, String mode) {
        Api.getInstance().forgetPsw(account, smsCode, password, mode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>() {

                    @Override
                    public void onSuccess(Object bean) {
                        if (mView != null) {
                            mView.showSuccess(account, smsCode);
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
    public void sendSMSCode(String phone) {
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

        Api.getInstance().sendForgetSMSCode(phone, "null", "null", "null")
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
    public void attachView(ForgerLoginContract.IView view) {
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
