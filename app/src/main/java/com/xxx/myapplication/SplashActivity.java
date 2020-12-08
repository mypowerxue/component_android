package com.xxx.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xxx.myapplication.main.MainActivity;

import java.lang.ref.WeakReference;

public class SplashActivity extends AppCompatActivity {

    /**
     * 延迟时间
     */
    private MyHandler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置backGround
        final View decorView = getWindow().getDecorView();
        decorView.post(new Runnable() {
            @Override
            public void run() {
                double width = decorView.getWidth();
                double height = decorView.getHeight();
                if (height / width >= 1.8) {
                    decorView.setBackgroundResource(R.mipmap.splash_big);
                } else {
                    decorView.setBackgroundResource(R.mipmap.splash_small);
                }
            }
        });

        // 利用消息处理器实现延迟跳转到登录窗口
        mHandler = new MyHandler(this);
        mHandler.sendEmptyMessageAtTime(0, 3 * 1000);
    }

    private static class MyHandler extends Handler {

        //弱引用对象
        private final WeakReference<SplashActivity> mActivity;

        MyHandler(SplashActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                SplashActivity activity = mActivity.get();
                if (activity != null) {
                    activity.startActivity(new Intent(activity, MainActivity.class));
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeMessages(0);
            mHandler = null;
        }
    }
}
