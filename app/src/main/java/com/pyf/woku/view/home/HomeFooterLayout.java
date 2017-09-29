package com.pyf.woku.view.home;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pyf.woku.R;
import com.pyf.woku.bean.Home;
import com.pyf.woku.imageloader.ImageLoaderManager;

/**
 * ListView底部布局
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/18
 */
public class HomeFooterLayout extends LinearLayout {

    private Context mContext;
    private Home.DataBean.HeadBean.FooterBean mFooterBean;
    private ImageLoaderManager mImageLoader;

    private TextView mTvHomeFooterTitle;
    private TextView mTvHomeFooterInfo;
    private TextView mTvHomeFooterFrom;
    private ImageView mIvHomeFooterOne;
    private ImageView mIvHomeFooterTwo;

    public HomeFooterLayout(Context context, Home.DataBean.HeadBean.FooterBean footerBean) {
        this(context, null, footerBean);
    }

    public HomeFooterLayout(Context context, @Nullable AttributeSet attrs,
                            Home.DataBean.HeadBean.FooterBean footerBean) {
        super(context, attrs);
        mContext = context;
        mFooterBean = footerBean;
        mImageLoader = ImageLoaderManager.getInstance(context);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View rootView = inflater.inflate(R.layout.listview_home_footer_layout, this);
        mTvHomeFooterTitle = (TextView) rootView.findViewById(R.id.tv_home_footer_title);
        mTvHomeFooterFrom = (TextView) rootView.findViewById(R.id.tv_home_footer_from);
        mTvHomeFooterInfo = (TextView) rootView.findViewById(R.id.tv_home_footer_info);
        mIvHomeFooterOne = (ImageView) rootView.findViewById(R.id.iv_home_footer_one);
        mIvHomeFooterTwo = (ImageView) rootView.findViewById(R.id.iv_home_footer_two);
        mTvHomeFooterTitle.setText(mFooterBean.getTitle());
        mTvHomeFooterFrom.setText(mFooterBean.getFrom());
        mTvHomeFooterInfo.setText(mFooterBean.getInfo());
        mImageLoader.displayImage(mIvHomeFooterOne, mFooterBean.getImageOne());
        mImageLoader.displayImage(mIvHomeFooterTwo, mFooterBean.getImageTwo());
    }
}
