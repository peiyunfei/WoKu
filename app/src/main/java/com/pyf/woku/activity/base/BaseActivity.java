package com.pyf.woku.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * activity基类
 * <br/>
 * 作者：裴云飞
 * <br/
 * 时间：2017/9/16
 */
public abstract class BaseActivity extends AppCompatActivity {

    // 打日志的标记
    public String TAG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getComponentName().getShortClassName();
        initView();
        initData();
        initEvent();
    }

    /**
     * 初始化事件
     */
    private void initEvent() {

    }

    /**
     * 初始化数据
     */
    public void initData() {
    }

    /**
     * 初始化控件
     */
    protected abstract void initView();

}
