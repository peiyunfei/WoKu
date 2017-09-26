package com.pyf.woku.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pyf.woku.imageloader.ImageLoaderManager;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * 首页头部广告适配器
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/18
 */
public class PhotoViewAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> mData;
    private boolean mIsMatch;
    private ImageLoaderManager mImageLoader;

    /**
     * @param context
     *         上下文
     * @param data
     *         数据源
     * @param imageLoader
     *         图片加载器
     * @param isMatch
     *         true 显示轮播图，false 显示大图
     */
    public PhotoViewAdapter(Context context, List<String> data,
                            ImageLoaderManager imageLoader, boolean isMatch) {
        mContext = context;
        mData = data;
        mIsMatch = isMatch;
        mImageLoader = imageLoader;
    }

    @Override
    public int getCount() {
        return mData.size();
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
        String data = mData.get(position);
        ImageView image = null;
        if (mIsMatch) {
            image = new ImageView(mContext);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
        } else {
            image = new PhotoView(mContext);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        image.setLayoutParams(params);
        mImageLoader.displayImage(image, data);
        container.addView(image);
        return image;
    }
}
