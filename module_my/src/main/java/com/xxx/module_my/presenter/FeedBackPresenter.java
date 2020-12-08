package com.xxx.module_my.presenter;

import com.xxx.common.model.http.Api;
import com.xxx.common.model.http.ApiCallback;
import com.xxx.module_my.contract.FeedBackContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FeedBackPresenter implements FeedBackContract.IPresenter {

    private FeedBackContract.IView mView;

    @Override
    public void submit(String content) {
        Api.getInstance().submitFeedback(content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>() {

                    @Override
                    public void onSuccess(Object bean) {
                        if (mView != null) {
                            mView.showSuccess();
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
    public void attachView(FeedBackContract.IView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

}
