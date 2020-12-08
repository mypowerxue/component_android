package com.xxx.common.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xxx.common.R;


/**
 * 加载中的Dialog
 */
public class LoadingDialog extends AlertDialog {

    private View view;

    public static LoadingDialog getInstance(Context context) {
        return new LoadingDialog(context);
    }

    private LoadingDialog(Context context) {
        super(context);
    }

    //渲染布局
    private void initView() {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.window_loading, null);
            //设置大小
            final Window window = getWindow();
            if (window != null) {
                final WindowManager.LayoutParams lp = window.getAttributes();
                window.setBackgroundDrawableResource(android.R.color.transparent);
                window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                window.getDecorView().post(new Runnable() {
                    @Override
                    public void run() {
                        double width = 0.45;
                        if (width != 0) {
                            lp.width = (int) (window.getDecorView().getWidth() * width);
                            lp.height = lp.width;
                            window.setGravity(Gravity.CLIP_HORIZONTAL);
                            window.setAttributes(lp);
                        }
                    }
                });
            }
        }
        setContentView(view);
    }

    @Override
    public void show() {
        super.show();   //展示
        initView();
    }
}