package com.pyf.woku.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

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
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getComponentName().getShortClassName();
        setContentView(initView());
        mUnBinder = ButterKnife.bind(this);
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnBinder != null) {
            // 解绑
            mUnBinder.unbind();
        }
    }

    /**
     * 初始化数据
     */
    public void initData() {
    }

    /**
     * 初始化控件
     */
    protected abstract int initView();

}
