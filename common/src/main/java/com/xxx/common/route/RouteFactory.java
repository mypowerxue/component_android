package com.xxx.common.route;

/**
 * 路由工厂
 */
public class RouteFactory {

    private static RouteFactory instance;//单例模式

    private RouteFactory() {

    }

    public static RouteFactory getInstance() {
        if (instance == null) {
            synchronized (RouteFactory.class) {
                if (instance == null)
                    instance = new RouteFactory();
            }
        }
        return instance;
    }

    //登录模块
    private ILoginModelService mILoginModelService;

    public ILoginModelService getLoginModelService() {
        return mILoginModelService;
    }

    public void setLoginModelService(ILoginModelService mILoginModelService) {
        this.mILoginModelService = mILoginModelService;
    }

    //我的模块
    private IMyModelService mIMyModelService;

    public IMyModelService getMyModelService() {
        return mIMyModelService;
    }

    public void setMyModelService(IMyModelService mIMyModelService) {
        this.mIMyModelService = mIMyModelService;
    }
}
