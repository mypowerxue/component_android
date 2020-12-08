package com.xxx.login.presenter;

import com.xxx.common.model.http.Api;
import com.xxx.common.model.http.ApiCallback;
import com.xxx.common.model.http.bean.base.CountyBean;
import com.xxx.login.contract.SelectCountyContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SelectCountyPresenter implements SelectCountyContract.IPresenter {

    private SelectCountyContract.IView mView;

    @Override
    public void loadData() {
        Api.getInstance().getCounty()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<List<CountyBean>>() {

                    @Override
                    public void onSuccess(List<CountyBean> list) {
                        if (mView != null) {
                            if (list == null || list.size() == 0) {
                                mView.showNotData();
                            } else {
                                mView.showData(list);
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
    public void attachView(SelectCountyContract.IView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

}
