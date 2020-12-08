package com.xxx.common.model.http.utils;

public class ApiError {

    /**
     * 服务器返回的错误码处理
     *
     * @param code 状态码
     */
    public static void ServiceCodeErrorFun(int code) {
        switch (code) {
            case ApiCode.CT_TOKEN_INVALID:
                startActivity();
                break;
            case ApiCode.UC_TOKEN_INVALID:
                startActivity();
                break;
        }
    }


    /**
     * Token失效跳转页面
     */
    public static void startActivity() {
//        MainActivity activity = (MainActivity) ActivityManager.getInstance().getActivity(MainActivity.class.getSimpleName());
//        Activity loginActivity = ActivityManager.getInstance().getActivity(LoginActivity.class.getSimpleName());
//        if (loginActivity == null) {
//            if (activity != null) {
//                SharedPreferencesUtil.getInstance().cleanAll();
//                LoginActivity.actionStart(activity);
//            }
//        }
    }
}
