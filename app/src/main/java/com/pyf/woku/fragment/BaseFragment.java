package com.pyf.woku.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * fragment基类
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/16
 */
public abstract class BaseFragment extends Fragment {

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
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnBinder != null) {
            // 解绑
            mUnBinder.unbind();
        }
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
