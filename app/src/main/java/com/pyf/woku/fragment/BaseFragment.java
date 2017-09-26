package com.pyf.woku.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pyf.woku.constant.Constant;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * fragment基类
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/16
 */
public abstract class BaseFragment extends Fragment {

    protected static final int REQUEST_QRCODE = 0x01;

    protected Context mContext;
    private Unbinder mUnBinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        if (setLayout() instanceof Integer) {
            rootView = inflater.inflate((Integer) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        }
        if (rootView != null) {
            // 注入
            mUnBinder = ButterKnife.bind(this, rootView);
            // 绑定
            onBindView(savedInstanceState, rootView);
        }
        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_QRCODE:
                if (resultCode == RESULT_OK) {
                    String scanResult = data.getStringExtra("SCAN_RESULT");
                    Toast.makeText(mContext, scanResult, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnBinder != null) {
            // 解绑
            mUnBinder.unbind();
        }
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
            if (ContextCompat.checkSelfPermission(mContext, permission)
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
                    openCamera();
                }
                break;
            case Constant.WRITE_READ_EXTERNAL_CODE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doWriteSdCard();
                }
                break;
        }
    }

    protected void openCamera() {
    }

    protected void doWriteSdCard() {
    }

    /**
     * 绑定视图，子类必须实现
     *
     * @param savedInstanceState
     *         用于保存fragment的状态
     * @param rootView
     *         根布局
     */
    protected abstract void onBindView(Bundle savedInstanceState, View rootView);

    /**
     * 初始化页面布局
     */
    protected abstract Object setLayout();

}
