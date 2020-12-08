package com.xxx.common.ui.base;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public abstract class BaseDialog extends AlertDialog {

    private View view;

    protected BaseDialog(Context context) {
        super(context);
        setCancelable(true); // 是否可以按“返回键”消失
        setCanceledOnTouchOutside(false); // 点击框以外的区域
    }

    //渲染布局
    private void showView() {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(getLayoutId(), null);
            //设置大小
            final Window window = getWindow();
            if (window != null) {
                final WindowManager.LayoutParams lp = window.getAttributes();
                window.setBackgroundDrawableResource(android.R.color.transparent);
                window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                window.getDecorView().post(new Runnable() {
                    @Override
                    public void run() {
                        double width = 0.8;
                        if (width != 0) {
                            lp.width = (int) (window.getDecorView().getWidth() * width);
                            window.setGravity(Gravity.CLIP_HORIZONTAL);
                            window.setAttributes(lp);
                        }
                    }
                });
            }
            setContentView(view);
            // 接着清除flags
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            initView();
            initOnClick();
            initData();
        }
    }

    @Override
    public void show() {
        super.show();   //展示
        showView();
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
