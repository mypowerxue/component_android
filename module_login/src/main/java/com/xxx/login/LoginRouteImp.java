package com.xxx.login;

import android.app.Activity;

import com.xxx.common.route.ILoginModelService;
import com.xxx.login.ui.activity.LoginActivity;

public class LoginRouteImp implements ILoginModelService {

    @Override
    public void startLoginActivity(Activity activity) {
        LoginActivity.actionStart(activity);
    }

}
