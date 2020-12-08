package com.xxx.login.contract;

import com.xxx.common.model.http.bean.base.CountyBean;
import com.xxx.common.mvp.BasePresenter;
import com.xxx.common.mvp.BaseView;

import java.util.List;

public interface SelectCountyContract {

    interface IPresenter extends BasePresenter<IView> {

        void loadData();   //加载数据

    }

    interface IView extends BaseView {

        void showData(List<CountyBean> list); //展示登录成功

        void showNotData(); //展示没有数据

    }
}
