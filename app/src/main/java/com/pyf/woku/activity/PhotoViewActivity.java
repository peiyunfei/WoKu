package com.pyf.woku.activity;

import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.pyf.woku.R;
import com.pyf.woku.activity.base.BaseActivity;
import com.pyf.woku.adapter.PhotoViewAdapter;
import com.pyf.woku.imageloader.ImageLoaderManager;
import com.pyf.woku.util.Util;
import com.pyf.wokusdk.util.Utils;

import java.util.List;

import butterknife.BindView;

/**
 * 浏览大图的界面
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/26
 */
public class PhotoViewActivity extends BaseActivity {

    public static final String PHOTO_LIST = "ptoto_list";

    @BindView(R.id.tv_indicator)
    TextView mTvIndicator;
    @BindView(R.id.iv_share)
    ImageView mIvShare;
    @BindView(R.id.vp_photo_view)
    ViewPager mVpPhotoView;

    private int mCurPosition;
    private List<String> mPhotos;
    private PhotoViewAdapter mAdapter;

    @Override
    protected int initView() {
        return R.layout.activity_photo_view;
    }

    @Override
    public void initData() {
        mPhotos = getIntent().getStringArrayListExtra(PHOTO_LIST);
        ImageLoaderManager imageLoader = ImageLoaderManager.getInstance(this);
        mTvIndicator.setText("1/".concat(mPhotos.size() + ""));
        mAdapter = new PhotoViewAdapter(this, mPhotos, imageLoader, false);
        mVpPhotoView.setAdapter(mAdapter);
        mVpPhotoView.setPageMargin(Utils.dip2px(this, 10));
        mVpPhotoView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTvIndicator.setText(String.valueOf(position + 1).concat("/" + mPhotos.size()));
                mCurPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Util.hideSoftInputMethod(this, mTvIndicator);
    }

}
