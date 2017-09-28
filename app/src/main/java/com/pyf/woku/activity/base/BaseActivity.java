package com.pyf.woku.activity.base;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.pyf.woku.constant.Constant;

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
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initData();
    }

    /**
     * 申请指定权限
     *
     * @param requestCode
     *         结果码
     * @param permissions
     *         指定权限
     */
    protected void requestPermissions(int requestCode, String... permissions) {
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(permissions, requestCode);
        }
    }

    /**
     * 判断是否有指定的权限
     *
     * @param permissions
     *         指定的权限
     * @return true 有指定的权限，false 没有指定的权限
     */
    protected boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constant.HARDWEAR_CAMERA_CODE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                break;
            case Constant.WRITE_READ_EXTERNAL_CODE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                break;
        }
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
