package com.xxx.common.ui.base;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.xxx.common.R;

public abstract class BasePopup extends PopupWindow {

    private Activity mContext;
    private View view;

    protected BasePopup(Activity activity) {
        super(activity);
        this.mContext = activity;
    }

    //渲染布局
    private void showView() {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(getLayoutId(), null);
            Window window = mContext.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.alpha = 0.6f;
            window.setAttributes(params);

            setAnimationStyle(R.style.showPopupAnimation);

            setBackgroundDrawable(new BitmapDrawable());    //边距BUG解决
            setOutsideTouchable(true);
            setContentView(view);
            setWidth(WindowManager.LayoutParams.MATCH_PARENT);
            setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            setFocusable(true);

            this.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss() {
                    Window window = mContext.getWindow();
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.alpha = 1f;
                    window.setAttributes(params);
                }
            });
            initView();
            initOnClick();
            initData();
        }
    }

    public void show() {
        showView();
        showAtLocation(mContext.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    //获取到Layout的ID
    protected abstract int getLayoutId();

    //初始化控件Id
    protected abstract void initView();

    //初始化点击事件
    protected abstract void initOnClick();

    //初始化数据
    protected abstract void initData();

}
