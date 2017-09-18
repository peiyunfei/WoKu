package com.pyf.woku.fragment.main;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pyf.woku.R;
import com.pyf.woku.R2;
import com.pyf.woku.adapter.HomeAdapter;
import com.pyf.woku.bean.Home;
import com.pyf.woku.fragment.BaseFragment;
import com.pyf.woku.network.http.HttpConstants;
import com.pyf.woku.view.HomeHeaderLayout;
import com.pyf.wokusdk.okhttp.CommonOkHttpClient;
import com.pyf.wokusdk.okhttp.listener.DisposeDataHandle;
import com.pyf.wokusdk.okhttp.listener.DisposeDataListener;
import com.pyf.wokusdk.okhttp.request.CommonRequest;

import java.util.List;

import butterknife.BindView;
import okhttp3.Request;

/**
 * 首页
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/16
 */
public class HomeFragment extends BaseFragment {

    @BindView(R2.id.image_home_scan)
    TextView mImageHomeScan;
    @BindView(R2.id.tv_home_search)
    TextView mTvHomeSearch;
    @BindView(R2.id.image_home_category)
    TextView mImageHomeCategory;
    @BindView(R2.id.loading_view)
    ImageView mLoadingView;
    @BindView(R2.id.lv_home)
    ListView mLvHome;

    private Home mHome;
    private HomeAdapter mAdapter;

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        AnimationDrawable anim = (AnimationDrawable) mLoadingView.getDrawable();
        anim.start();
        // 创建get请求
        Request get = CommonRequest.createGetRequest(HttpConstants.HOME, null);
        CommonOkHttpClient
                .request(get, new DisposeDataHandle(new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        mHome = (Home) responseObj;
                        showSuccessView();
                    }

                    @Override
                    public void onFailure(Object reasonObj) {
                        showErrorView();
                    }
                }, Home.class));
    }

    private void showSuccessView() {
        List<Home.DataBean.ListBean> list = mHome.getData().getList();
        Home.DataBean.HeadBean head = mHome.getData().getHead();
        if (list != null && list.size() > 0) {
            mLoadingView.setVisibility(View.GONE);
            mLvHome.setVisibility(View.VISIBLE);
            // 添加头布局
            mLvHome.addHeaderView(new HomeHeaderLayout(mContext, head));
            mAdapter = new HomeAdapter(mContext, list);
            mLvHome.setAdapter(mAdapter);
        } else {
            showErrorView();
        }
    }

    private void showErrorView() {

    }

    @Override
    protected Object setLayout() {
        return R.layout.fragment_home;
    }

}
