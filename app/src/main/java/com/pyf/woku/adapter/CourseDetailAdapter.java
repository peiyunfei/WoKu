package com.pyf.woku.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pyf.woku.R;
import com.pyf.woku.bean.CourseDetail;
import com.pyf.woku.imageloader.ImageLoaderManager;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 课程详情适配器
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/29
 */
public class CourseDetailAdapter extends BaseAdapter {
    private Context mContext;
    private List<CourseDetail.DataBean.BodyBean> mData;
    private ImageLoaderManager mImageLoader;

    public CourseDetailAdapter(Context context, List<CourseDetail.DataBean.BodyBean> data) {
        mContext = context;
        mData = data;
        mImageLoader = ImageLoaderManager.getInstance(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_course_detail, null);
            holder.mCivDetailLogo = (CircleImageView) convertView.findViewById(R.id.civ_detail_logo);
            holder.mTvDetailName = (TextView) convertView.findViewById(R.id.tv_detail_name);
            holder.mTvDetailText = (TextView) convertView.findViewById(R.id.tv_detail_text);
            holder.mTvDetailOwner = (TextView) convertView.findViewById(R.id.tv_detail_owner);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CourseDetail.DataBean.BodyBean bean = mData.get(position);
        if (bean.type == 0) {
            holder.mTvDetailOwner.setVisibility(View.VISIBLE);
        } else {
            holder.mTvDetailOwner.setVisibility(View.GONE);
        }
        holder.mTvDetailName.setText(bean.name);
        holder.mTvDetailText.setText(bean.text);
        mImageLoader.displayImage(holder.mCivDetailLogo, bean.logo);
        return convertView;
    }

    static class ViewHolder {
        CircleImageView mCivDetailLogo;
        TextView mTvDetailName;
        TextView mTvDetailText;
        TextView mTvDetailOwner;
    }
}
