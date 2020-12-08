package com.xxx.module_my.presenter;

import com.xxx.common.model.http.Api;
import com.xxx.common.model.http.ApiCallback;
import com.xxx.common.model.http.bean.AppVersionBean;
import com.xxx.common.model.log.LogConst;
import com.xxx.common.model.log.LogUtil;
import com.xxx.common.model.utils.SystemUtil;
import com.xxx.module_my.MyApp;
import com.xxx.module_my.contract.SettingContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SettingPresenter implements SettingContract.IPresenter {

    private SettingContract.IView mView;

    @Override
    public void checkVersion() {
        Api.getInstance().checkVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<AppVersionBean>() {

                    @Override
                    public void onSuccess(AppVersionBean bean) {
                        if (mView != null) {
                            if (bean != null) {
                                String newVersion = bean.getVersion();
                                String nowVersion = SystemUtil.getVersionName(MyApp.getContext());
                                if (!nowVersion.equals(newVersion)) {
                                    mView.showNeedUpdate(bean.getDownloadUrl());
                                }
                                LogUtil.showLog(LogConst.CHECK_VERSION_TAG, "当前App版本" + nowVersion);
                                LogUtil.showLog(LogConst.CHECK_VERSION_TAG, "最新App版本" + newVersion);
                            }
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
    public void attachView(SettingContract.IView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

}
