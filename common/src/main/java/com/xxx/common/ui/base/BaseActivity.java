package com.xxx.common.ui.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.xxx.common.mvp.BasePresenter;
import com.xxx.common.mvp.BaseView;
import com.xxx.common.ui.LoadingDialog;
import com.xxx.common.ui.config.EventBusConfig;
import com.xxx.common.ui.config.UIConfig;
import com.xxx.common.ui.manager.ActivityManager;
import com.xxx.common.ui.utils.EditTextShakeHelper;
import com.xxx.common.ui.utils.LocalManageUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    private ImmersionBar mImmersionBar;
    private LoadingDialog mLoadingDialog;
    private EditTextShakeHelper editTextShakeHelper;
    public T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        //添加到AppManager中
        ActivityManager.getInstance().addActivity(this);


        //沉浸式状态栏
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();

        //初始化弹框
        mLoadingDialog = LoadingDialog.getInstance(this);

        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //修改状态栏字体颜色
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //初始化P层接口
        presenter = getPresenter();
        if (presenter != null) {
            presenter.attachView((BaseView) this);
        }

        //注册语言切换的EventBus
        if (UIConfig.IS_OPEN_CHECK_LANGUAGE) {
            //注册多语言切换的EventBus
            EventBus.getDefault().register(this);
        }

        //初始化控件错误抖动
        editTextShakeHelper = new EditTextShakeHelper(this);

        //初始化Bundle
        initBundle(getIntent());

        //初始化控件
        initView();

        //初始化数据
        initData();

        //初始化控件点击事件
        initOnClick();
    }

    //获取到Layout的ID
    protected abstract int getLayoutId();

    //初始化传递数据
    protected abstract void initBundle(Intent intent);

    //初始化控件Id
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    //初始化点击事件
    protected abstract void initOnClick();

    public void showLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.show();
        }
    }

    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.hide();
        }
    }

    public void showErrorView(View... views) {
        if (editTextShakeHelper != null) {
            editTextShakeHelper.shake(views);
        }
    }

    //拦截全局EventBus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(String eventFlag) {
        switch (eventFlag) {
            case EventBusConfig.EVENT_LANGUAGE_TAG:
                recreate();//刷新界面
                break;
        }
    }

    //获取Presenter方法
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private T getPresenter() {
        Type type = getClass().getGenericSuperclass();
        if (!type.equals(BaseActivity.class)) {
            Type[] arguments = ((ParameterizedType) type).getActualTypeArguments();
            Class<T> tClass = (Class<T>) arguments[0];
            try {
                return tClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void attachBaseContext(Context base) {
        if (UIConfig.IS_OPEN_CHECK_LANGUAGE) {
            super.attachBaseContext(LocalManageUtil.setLocal(base));
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                createConfigurationContext(newConfig);
            } else {
                res.updateConfiguration(newConfig, res.getDisplayMetrics());
            }
        }
        return res;
    }

    @Override
    public void finish() {
        super.finish();
        ActivityManager.getInstance().finishActivity(this);
    }

    @Override
    protected void onDestroy() {
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
            mImmersionBar = null;
        }
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
        if (editTextShakeHelper != null) {
            editTextShakeHelper = null;
        }
        //解除语言切换的EventBus
        if (UIConfig.IS_OPEN_CHECK_LANGUAGE) {
            //注册多语言切换的EventBus
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

}
