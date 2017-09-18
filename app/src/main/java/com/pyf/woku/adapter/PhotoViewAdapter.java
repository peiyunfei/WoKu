package com.pyf.woku.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pyf.woku.imageloader.ImageLoaderManager;

import java.util.List;

/**
 * 首页头部广告适配器
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/18
 */
public class PhotoViewAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> mAds;
    private boolean mIsMatch;
    private ImageLoaderManager mImageLoader;

    public PhotoViewAdapter(Context context, List<String> ads,
                            ImageLoaderManager imageLoader, boolean isMatch) {
        mContext = context;
        mAds = ads;
        mIsMatch = isMatch;
        mImageLoader = imageLoader;
    }

    @Override
    public int getCount() {
        return mAds.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String ad = mAds.get(position);
        ImageView image = new ImageView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        image.setLayoutParams(params);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        mImageLoader.displayImage(image, ad);
        container.addView(image);
        return image;
    }
}
