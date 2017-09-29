package com.pyf.woku.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pyf.woku.R;
import com.pyf.woku.activity.PhotoViewActivity;
import com.pyf.woku.bean.Home;
import com.pyf.woku.imageloader.ImageLoaderManager;
import com.pyf.woku.util.Util;
import com.pyf.wokusdk.core.video.VideoContext;
import com.pyf.wokusdk.util.Utils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 首页适配器
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/17
 */
public class HomeAdapter extends BaseAdapter {

    private static final int CARD_COUNT = 4;
    // 视频
    private static final int VIDEO_TYPE = 0;
    // 多图
    private static final int MULTIPART_TYPE = 1;
    // 单图
    private static final int SINGLE_TYPE = 2;
    // 轮播图
    private static final int BANNER_TYPE = 3;

    private Context mContext;
    private List<Home.DataBean.ListBean> list;
    private ImageLoaderManager mImageLoader;
    private VideoContext mVideoContext;

    public HomeAdapter(Context context, List<Home.DataBean.ListBean> list) {
        mContext = context;
        this.list = list;
        mImageLoader = ImageLoaderManager.getInstance(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return CARD_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        Home.DataBean.ListBean listBean = list.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            // 初始化不同类型的布局文件
            convertView = initView(convertView, type, holder, listBean);
            // 初始化不同类型相同的布局文件
            convertView = initCommonView(convertView, type, holder);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        bindData(type, holder, listBean);
        return convertView;
    }

    private void bindData(int type, ViewHolder holder, final Home.DataBean.ListBean listBean) {
        switch (type) {
            case SINGLE_TYPE:
                bindData(holder, listBean);
                // 设置单图特有的属性
                mImageLoader.displayImage(holder.mIvHomePhoto, listBean.getUrl().get(0));
                setBigImageListener(holder.mIvHomePhoto, listBean);
                break;
            case MULTIPART_TYPE:
                bindData(holder, listBean);
                // 设置多图特有的属性
                for (String url : listBean.getUrl()) {
                    holder.mLlHomeMultipart.addView(createImageView(url));
                }
                setBigImageListener(holder.mLlHomeMultipart, listBean);
                break;
            case VIDEO_TYPE:
                bindData(holder, listBean);
                break;
            case BANNER_TYPE:
                // 设置轮播图特有的属性
                List<Home.DataBean.ListBean> data = Util.handleData(listBean);
                holder.mVpHomeBanner.setAdapter(new BannerPagerAdapter(mContext, data, mImageLoader));
                holder.mVpHomeBanner.setPageMargin(Utils.dip2px(mContext, 12));
                // 无限轮播
                holder.mVpHomeBanner.setCurrentItem(data.size() * 100);
                break;
        }
    }

    private void setBigImageListener(View v, final Home.DataBean.ListBean listBean) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listBean.getType() != 0) {
                    Intent intent = new Intent(mContext, PhotoViewActivity.class);
                    intent.putStringArrayListExtra(PhotoViewActivity.PHOTO_LIST,
                            (ArrayList<String>) listBean.getUrl());
                    mContext.startActivity(intent);
                }
            }
        });
    }

    private View createImageView(String url) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                Utils.dip2px(mContext, 200), LinearLayout.LayoutParams.MATCH_PARENT);
        params.leftMargin = Utils.dip2px(mContext, 5);
        imageView.setLayoutParams(params);
        mImageLoader.displayImage(imageView, url);
        return imageView;
    }

    /**
     * 绑定多图和单图共有的数据
     */
    private void bindData(ViewHolder holder, Home.DataBean.ListBean listBean) {
        mImageLoader.displayImage(holder.mCivHomeLogo, listBean.getLogo());
        holder.mTvHomeFrom.setText(listBean.getFrom());
        holder.mTvHomeTitle.setText(listBean.getTitle());
        holder.mTvHomeInfo.setText(listBean.getInfo().concat("天前"));
        holder.mTvHomePrice.setText(listBean.getPrice());
        holder.mTvHomeZan.setText("点赞".concat(listBean.getZan()));
    }

    private View initCommonView(View convertView, int type, ViewHolder holder) {
        switch (type) {
            case SINGLE_TYPE: // 单图
            case MULTIPART_TYPE: // 多图
            case VIDEO_TYPE: // 视频
                holder.mCivHomeLogo = (CircleImageView) convertView.findViewById(R.id.civ_home_logo);
                holder.mTvHomeFrom = (TextView) convertView.findViewById(R.id.tv_home_from);
                holder.mTvHomeTitle = (TextView) convertView.findViewById(R.id.tv_home_title);
                holder.mTvHomeInfo = (TextView) convertView.findViewById(R.id.tv_home_info);
                holder.mTvHomePrice = (TextView) convertView.findViewById(R.id.tv_home_price);
                holder.mTvHomeZan = (TextView) convertView.findViewById(R.id.tv_home_zan);
                break;
            default:
                break;
        }
        return convertView;
    }

    private View initView(View convertView, int type,
                          ViewHolder holder, Home.DataBean.ListBean listBean) {
        switch (type) {
            case SINGLE_TYPE: // 单图
                convertView = View.inflate(mContext, R.layout.item_home_single, null);
                holder.mIvHomePhoto = (ImageView) convertView.findViewById(R.id.iv_home_photo);
                break;
            case MULTIPART_TYPE: // 多图
                convertView = View.inflate(mContext, R.layout.item_home_multipart, null);
                holder.mLlHomeMultipart = (LinearLayout) convertView.findViewById(R.id.ll_home_multipart);
                break;
            case VIDEO_TYPE: // 视频
                convertView = View.inflate(mContext, R.layout.item_home_video, null);
                holder.mRlVideoLayout = (RelativeLayout) convertView.findViewById(R.id.rl_video_layout);
                mVideoContext = new VideoContext(holder.mRlVideoLayout,
                        new Gson().toJson(listBean), null);
                break;
            case BANNER_TYPE: // 轮播图
                convertView = View.inflate(mContext, R.layout.item_home_banner, null);
                holder.mVpHomeBanner = (ViewPager) convertView.findViewById(R.id.vp_home_banner);
                break;
        }
        return convertView;
    }

    public void updateAdInScrollView() {
        if (mVideoContext != null) {
            mVideoContext.updateAdInScrollView();
        }
    }

    static class ViewHolder {
        // 单图和多图类型共有的属性
        private CircleImageView mCivHomeLogo;
        private TextView mTvHomeTitle;
        private TextView mTvHomePrice;
        private TextView mTvHomeInfo;
        private TextView mTvHomeFrom;
        private TextView mTvHomeZan;

        // 单图独有的属性
        private ImageView mIvHomePhoto;

        // 多图独有的属性
        private LinearLayout mLlHomeMultipart;

        // 轮播图
        private ViewPager mVpHomeBanner;

        // 视频
        private RelativeLayout mRlVideoLayout;
    }
}
