package com.xxx.common.model.http.bean.base;

public class BaseBean<T> {

    /**
     * code : 0
     * message : 操作成功
     * data : null
     */
    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T content) {
        this.data = content;
    }

}
