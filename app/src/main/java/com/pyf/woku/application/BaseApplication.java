package com.pyf.woku.application;

import android.app.Application;

/**
 * 应用程序的入口
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/16
 */
public class BaseApplication extends Application {

    // 全局的唯一实例
    private static BaseApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }
}
