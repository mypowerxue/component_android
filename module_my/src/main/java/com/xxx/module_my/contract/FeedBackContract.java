package com.xxx.module_my.contract;

import com.xxx.common.mvp.BasePresenter;
import com.xxx.common.mvp.BaseView;

public interface FeedBackContract {

    interface IPresenter extends BasePresenter<IView> {

        void submit(String content);   //提交

    }

    interface IView extends BaseView {

        void showSuccess(); //成功

    }
}
