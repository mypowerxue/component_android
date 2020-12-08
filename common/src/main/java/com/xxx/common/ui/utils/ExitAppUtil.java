package com.xxx.common.ui.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.xxx.common.R;
import com.xxx.common.ui.manager.ActivityManager;

public class ExitAppUtil {

    private final MyHandler mHandler;

    private static ExitAppUtil exitAppUtil;

    private ExitAppUtil() {
        mHandler = new MyHandler();
    }

    public static ExitAppUtil getInstance() {
        if (exitAppUtil == null) {
            synchronized (ExitAppUtil.class) {
                if (exitAppUtil == null) {
                    exitAppUtil = new ExitAppUtil();
                }
            }
        }
        return exitAppUtil;
    }

    //二次点击
    private static class MyHandler extends Handler {

        private boolean backCount = false;

        //此处不可使用 弱引用对象
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                backCount = false;
            }
        }

        boolean isBackCount() {
            return backCount;
        }

        void setBackCount() {
            this.backCount = true;
        }
    }

    public void onBackPressed(Context context) {
        if (mHandler.isBackCount()) {
            mHandler.removeMessages(1);
            ActivityManager.getInstance().AppExit();
            return;
        }
        mHandler.setBackCount();
        mHandler.sendEmptyMessageDelayed(1, 1500);
        ToastUtil.showToast(context, R.string.second_exit_app);
    }
}