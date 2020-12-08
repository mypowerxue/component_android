package com.xxx.common.ui.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

/**
 * 倒计时工具类
 */
public class DownTimeUtil {

    private static final int TIME_MSG = 1;
    private static final int END_MSG = 2;

    private int nowTime;

    private boolean isClose;

    private Callback callBack;

    public DownTimeUtil(Callback callback) {
        this.callBack = callback;
    }

    private DownTimeUtil() {

    }

    public interface Callback {

        void run(int nowTime);

        void end();

    }

    private Callback callback;

    /**
     * 获取实例
     *
     * @return
     */
    public static DownTimeUtil getInstance(Callback callback) {
        return new DownTimeUtil(callback);
    }

    /**
     * 开启倒计时
     *
     * @param time
     */
    public void openDownTime(final int time) {
        nowTime = time + 1;
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = time; i > 0; i--) {
                    try {
                        if (isClose) {
                            handler.removeMessages(TIME_MSG);
                            return;
                        }
                        nowTime--;
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(TIME_MSG);
                }
                if (isClose) {
                    handler.removeMessages(END_MSG);
                    return;
                }
                handler.sendEmptyMessage(END_MSG);
            }
        }).start();
    }


    /**
     * 关闭倒计时
     */
    public void closeDownTime() {
        isClose = true;
        this.callBack = null;
        handler.removeMessages(TIME_MSG);
    }

    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME_MSG:
                    if (callback != null) {
                        callback.run(nowTime);
                    }
                    break;
                case END_MSG:
                    if (callback != null) {
                        callback.end();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

}
