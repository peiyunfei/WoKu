package com.pyf.woku.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pyf.woku.R;
import com.pyf.woku.adapter.PhotoViewAdapter;
import com.pyf.woku.bean.Home;
import com.pyf.woku.imageloader.ImageLoaderManager;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

/**
 * ListView的头布局
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/18
 */
public class HomeHeaderLayout extends LinearLayout {

    private Context mContext;
    private Home.DataBean.HeadBean mHead;
    private ImageLoaderManager mImageLoader;

    // UI
    private AutoScrollViewPager mAsvpHomeBanner;
    private CirclePageIndicator mCpiIndicator;
    private TextView mTvHomeHeadNew;
    private ImageView[] mIvHomeHeads = new ImageView[4];
    private LinearLayout MLlHomeFooter;

    public HomeHeaderLayout(Context context, Home.DataBean.HeadBean head) {
        this(context, null, head);
    }

    public HomeHeaderLayout(Context context, @Nullable AttributeSet attrs, Home.DataBean.HeadBean head) {
        super(context, attrs);
        mContext = context;
        mHead = head;
        mImageLoader = ImageLoaderManager.getInstance(mContext);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View rootView = inflater.inflate(R.layout.listview_home_head_layout, this);
        mAsvpHomeBanner = (AutoScrollViewPager) rootView.findViewById(R.id.asvp_home_banner);
        mCpiIndicator = (CirclePageIndicator) rootView.findViewById(R.id.cpi_indicator);
        mTvHomeHeadNew = (TextView) rootView.findViewById(R.id.tv_home_head_new);
        MLlHomeFooter = (LinearLayout) rootView.findViewById(R.id.ll_home_footer);
        mIvHomeHeads[0] = (ImageView) rootView.findViewById(R.id.iv_home_head_one);
        mIvHomeHeads[1] = (ImageView) rootView.findViewById(R.id.iv_home_head_two);
        mIvHomeHeads[2] = (ImageView) rootView.findViewById(R.id.iv_home_head_three);
        mIvHomeHeads[3] = (ImageView) rootView.findViewById(R.id.iv_home_head_four);
        // 设置中间图片
        for (int i = 0; i < mIvHomeHeads.length; i++) {
            mImageLoader.displayImage(mIvHomeHeads[i], mHead.getMiddle().get(i));
        }
        mTvHomeHeadNew.setText("今日最新");
        // 轮播图
        mAsvpHomeBanner.setAdapter(new PhotoViewAdapter(mContext, mHead.getAds(), mImageLoader, true));
        mAsvpHomeBanner.startAutoScroll(3000);
        mCpiIndicator.setViewPager(mAsvpHomeBanner);
        // 添加底部布局
        for (Home.DataBean.HeadBean.FooterBean footerBean : mHead.getFooter()) {
            MLlHomeFooter.addView(new HomeFooterLayout(mContext, footerBean));
        }
    }

}
