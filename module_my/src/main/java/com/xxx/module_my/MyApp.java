package com.xxx.module_my;

import android.annotation.SuppressLint;
import android.content.Context;

import com.xxx.common.route.RouteFactory;

public class MyApp {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void init(Context c) {
        context = c;
        RouteFactory.getInstance().setMyModelService(new MyRouteImp());
    }
}
