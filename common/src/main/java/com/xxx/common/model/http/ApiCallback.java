package com.xxx.common.model.http;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.xxx.common.model.GlobalData;
import com.xxx.common.model.http.bean.base.BaseBean;
import com.xxx.common.model.http.utils.ApiCode;
import com.xxx.common.model.http.utils.ApiError;
import com.xxx.common.model.log.LogConst;
import com.xxx.common.model.log.LogUtil;
import com.xxx.common.ui.utils.LocalManageUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public abstract class ApiCallback<T> implements Observer<BaseBean<T>> {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();    //防止内存泄漏

    //必须重写 请求成功的方法
    public abstract void onSuccess(T bean);

    //必须重写 请求失败的方法
    public abstract void onError(int errorCode, String errorMessage);

    //必须重写 开始请求
    protected abstract void onStart();

    //必须重写 结束请求
    public abstract void onEnd();

    @Override
    public void onNext(BaseBean<T> bean) {
        if (bean != null) {
            int code = bean.getCode();
            //使用code值做判断
            if (code == ApiCode.RESPONSE_CODE_1 || code == ApiCode.RESPONSE_CODE_2) {
                T data = bean.getData();
                onSuccess(data);
            } else {
                //根据后台返回的code值做特殊异常处理
                ApiError.ServiceCodeErrorFun(code);
                //回调错误信息
                String errorMessage = bean.getMessage() == null ? "unknown error" : bean.getMessage();
                onError(code, errorMessage);
            }
        } else {
            //解析失败
            String errorMessage = "JsonFormat error";
            onError(ApiCode.SERVICE_ERROR_CODE, errorMessage);
        }
    }

    @Override
    public void onError(Throwable e) {
        //是否是中文
        boolean isChina = GlobalData.language == null || GlobalData.language.equals("") || LocalManageUtil.LANGUAGE_CN.equals(GlobalData.language);

        String errorMessage;
        int code = ApiCode.SERVICE_ERROR_CODE;
        if (e instanceof HttpException) {
            ResponseBody errorBody = ((HttpException) e).response().errorBody();
            if (errorBody != null) {
                try {
                    String string = errorBody.string();
                    BaseBean bean = new Gson().fromJson(string, BaseBean.class);
                    code = bean.getCode();
                    ApiError.ServiceCodeErrorFun(code);
                    errorMessage = bean.getMessage();
                } catch (Exception e1) {
                    errorMessage = isChina ? "解析异常" : "JsonFormat error";
                }
            } else {
                errorMessage = isChina ? "解析异常" : "JsonFormat error";
            }
        } else if (e instanceof UnknownHostException) {    //首先判断地址是否正确
            errorMessage = isChina ? "服务器地址错误" : "Server address error";
        } else if (e instanceof SocketTimeoutException || e instanceof ConnectException) {  //再判断是否是链接超时
            errorMessage = isChina ? "网络状态不好，请重试" : "Network Timeout";
        } else {
            errorMessage = e == null ? isChina ? "未知错误" : "unknown error" : e.getMessage();
        }
        LogUtil.showLog(LogConst.HTTP_ERROR_TAG, errorMessage);
        onError(code, errorMessage);
        onComplete();
    }

    @Override
    public void onSubscribe(Disposable d) {
        //开始请求
        onStart();
        if (compositeDisposable != null) {
            compositeDisposable.add(d);
        }
    }

    @Override
    public void onComplete() {
        //请求结束
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            compositeDisposable = null;
        }
        onEnd();
    }

}