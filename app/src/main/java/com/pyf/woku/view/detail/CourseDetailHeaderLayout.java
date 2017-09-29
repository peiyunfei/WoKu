package com.pyf.woku.view.detail;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pyf.woku.R;
import com.pyf.woku.bean.CourseDetail.DataBean.HeadBean;
import com.pyf.woku.imageloader.ImageLoaderManager;
import com.pyf.wokusdk.core.video.VideoContext;
import com.pyf.wokusdk.util.Utils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/29
 */
public class CourseDetailHeaderLayout extends LinearLayout {

    private Context mContext;
    private HeadBean mHead;
    private ImageLoaderManager mImageLoader;

    private CircleImageView mCivDetailHeaderLogo;
    private TextView mTvDetailHeaderName;
    private TextView mTvDetailHeaderDayTime;
    private TextView mTvDetailHeaderOldPrice;
    private TextView mTvDetailHeaderNewPrice;
    private TextView mTvDetailHeaderText;
    private TextView mTvDetailHeaderFrom;
    private TextView mTvDetailHeaderZan;
    private TextView mTvDetailHeaderScan;
    private TextView mTvDetailHeaderHotComment;
    private RelativeLayout mRlVideoView;
    private LinearLayout mLlPhotoView;

    public CourseDetailHeaderLayout(Context context, HeadBean head) {
        this(context, null, head);
    }

    public CourseDetailHeaderLayout(Context context, @Nullable AttributeSet attrs, HeadBean head) {
        super(context, attrs);
        mContext = context;
        mHead = head;
        mImageLoader = ImageLoaderManager.getInstance(context);
        initView();
        initData();
    }

    private void initData() {
        mImageLoader.displayImage(mCivDetailHeaderLogo, mHead.logo);
        mTvDetailHeaderName.setText(mHead.name);
        mTvDetailHeaderDayTime.setText(mHead.dayTime);
        mTvDetailHeaderOldPrice.setText(mHead.oldPrice);
        mTvDetailHeaderNewPrice.setText(mHead.newPrice);
        mTvDetailHeaderText.setText(mHead.text);
        mTvDetailHeaderFrom.setText(mHead.from);
        mTvDetailHeaderZan.setText(mHead.zan);
        mTvDetailHeaderScan.setText(mHead.scan);
        mTvDetailHeaderHotComment.setText(mHead.hotComment);
        for (int i = 0; i < mHead.photoUrls.size(); i++) {
            mLlPhotoView.addView(createImage(mHead.photoUrls.get(i)));
        }
        if (!TextUtils.isEmpty(mHead.video.resource)) {
            new VideoContext(mRlVideoView,
                    new Gson().toJson(mHead.video), null);
        }
    }

    private View createImage(String url) {
        ImageView image = new ImageView(mContext);
        LinearLayout.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                Utils.dip2px(mContext, 150));
        lp.topMargin = Utils.dip2px(mContext, 10);
        image.setLayoutParams(lp);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        mImageLoader.displayImage(image, url);
        return image;
    }

    private void initView() {
        LinearLayout rootView = (LinearLayout) LayoutInflater.from(mContext)
                .inflate(R.layout.item_detail_header, this);
        mRlVideoView = (RelativeLayout) rootView.findViewById(R.id.rl_video_view);
        mLlPhotoView = (LinearLayout) rootView.findViewById(R.id.rl_photo_view);
        mCivDetailHeaderLogo = (CircleImageView) rootView.findViewById(R.id.civ_detail_header_logo);
        mTvDetailHeaderName = (TextView) rootView.findViewById(R.id.tv_detail_header_name);
        mTvDetailHeaderDayTime = (TextView) rootView.findViewById(R.id.tv_detail_header_day_time);
        mTvDetailHeaderOldPrice = (TextView) rootView.findViewById(R.id.tv_detail_header_old_price);
        mTvDetailHeaderNewPrice = (TextView) rootView.findViewById(R.id.tv_detail_header_new_price);
        mTvDetailHeaderText = (TextView) rootView.findViewById(R.id.tv_detail_header_text);
        mTvDetailHeaderFrom = (TextView) rootView.findViewById(R.id.tv_detail_header_from);
        mTvDetailHeaderZan = (TextView) rootView.findViewById(R.id.tv_detail_header_zan);
        mTvDetailHeaderScan = (TextView) rootView.findViewById(R.id.tv_detail_header_scan);
        mTvDetailHeaderHotComment = (TextView) rootView.findViewById(R.id.tv_detail_header_hot_comment);
    }
}
