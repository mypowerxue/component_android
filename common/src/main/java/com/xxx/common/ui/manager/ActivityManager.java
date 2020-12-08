package com.xxx.common.ui.manager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;

import java.util.List;
import java.util.Stack;

/**
 * Activity管理类
 */
public class ActivityManager {

    private static Stack<Activity> activityStack;
    private static ActivityManager instance;

    private ActivityManager() {
        activityStack = new Stack<>();
    }

    /**
     * 单一实例
     */
    public static ActivityManager getInstance() {
        if (instance == null) {
            synchronized (ActivityManager.class) {
                if (instance == null) {
                    instance = new ActivityManager();
                }
            }
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * 结束当前Activity
     */
    public void finishActivity(Activity activity) {
        activityStack.remove(activity);
    }

    /**
     * 获取到指定Activity
     */
    public Activity getActivity(String activityName) {
        for (int i = 0; i < activityStack.size(); i++) {
            Activity activity = activityStack.get(i);
            if (activity != null) {
                if (activity.getClass().getSimpleName().equals(activityName)) {
                    return activity;
                }
            }
        }
        return null;
    }

    /**
     * 判断某个Activity 界面是否在前台
     */
    public boolean isForeground(String className) {
        Activity activity = getActivity(className);
        if (activity == null || TextUtils.isEmpty(className)) {
            return false;
        }

        android.app.ActivityManager am = (android.app.ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<android.app.ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
            if (list != null && list.size() > 0) {
                ComponentName cpn = list.get(0).topActivity;
                return className.equals(cpn.getClassName());
            }
        }
        return false;
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            int size = activityStack.size();
            for (int i = 0; i < size; i++) {
                Activity activity = activityStack.get(0);
                if (null != activity) {
                    activity.finish();
                }
            }
            activityStack.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
