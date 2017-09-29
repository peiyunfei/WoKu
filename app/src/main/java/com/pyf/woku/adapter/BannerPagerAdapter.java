package com.pyf.woku.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pyf.woku.R;
import com.pyf.woku.activity.PhotoViewActivity;
import com.pyf.woku.bean.Home;
import com.pyf.woku.imageloader.ImageLoaderManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图适配器
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/18
 */
public class BannerPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<Home.DataBean.ListBean> list;
    private ImageLoaderManager mImageLoader;

    public BannerPagerAdapter(Context context, List<Home.DataBean.ListBean> list,
                              ImageLoaderManager imageLoader) {
        mContext = context;
        this.list = list;
        mImageLoader = imageLoader;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
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
        final Home.DataBean.ListBean bean = list.get(position % list.size());
        // 初始化控件
        View rootView = View.inflate(mContext, R.layout.item_home_view_pager_banner, null);
        TextView tvHomeBannerTitle = (TextView) rootView.findViewById(R.id.tv_home_banner_title);
        TextView tvHomeBannerInfo = (TextView) rootView.findViewById(R.id.tv_home_banner_info);
        TextView tvHomeBannerPrice = (TextView) rootView.findViewById(R.id.tv_home_banner_price);
        TextView tvHomeBannerText = (TextView) rootView.findViewById(R.id.tv_home_banner_text);
        ImageView[] images = new ImageView[3];
        images[0] = (ImageView) rootView.findViewById(R.id.iv_home_banner_one);
        images[1] = (ImageView) rootView.findViewById(R.id.iv_home_banner_two);
        images[2] = (ImageView) rootView.findViewById(R.id.iv_home_banner_three);

        // 绑定数据
        tvHomeBannerInfo.setText(bean.getInfo());
        tvHomeBannerTitle.setText(bean.getTitle());
        tvHomeBannerPrice.setText(bean.getPrice());
        tvHomeBannerText.setText(bean.getText());
        for (int i = 0; i < images.length; i++) {
            mImageLoader.displayImage(images[i], bean.getUrl().get(i));
        }
        container.addView(rootView);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PhotoViewActivity.class);
                intent.putStringArrayListExtra(PhotoViewActivity.PHOTO_LIST,
                        (ArrayList<String>) bean.getUrl());
                mContext.startActivity(intent);
            }
        });
        return rootView;
    }
}
