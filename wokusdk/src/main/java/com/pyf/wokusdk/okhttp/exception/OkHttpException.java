package com.pyf.wokusdk.okhttp.exception;

/**
 * 异常处理
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/17
 */
public class OkHttpException extends Exception {

    // 异常码
    private int code;
    // 异常信息
    private Object msg;

    public OkHttpException() {
    }

    public OkHttpException(int code, Object msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public Object getMsg() {
        return msg;
    }
}
