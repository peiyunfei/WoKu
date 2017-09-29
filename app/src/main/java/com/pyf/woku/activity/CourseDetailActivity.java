package com.pyf.woku.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.pyf.woku.R;
import com.pyf.woku.R2;
import com.pyf.woku.activity.base.BaseActivity;
import com.pyf.woku.adapter.CourseDetailAdapter;
import com.pyf.woku.bean.CourseDetail;
import com.pyf.woku.network.http.RequestCenter;
import com.pyf.woku.view.detail.CourseDetailFooterLayout;
import com.pyf.woku.view.detail.CourseDetailHeaderLayout;
import com.pyf.wokusdk.okhttp.listener.DisposeDataListener;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 课程详情界面
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/29
 */
public class CourseDetailActivity extends BaseActivity {

    @BindView(R2.id.iv_detail_loading)
    ImageView mIvDetailLoading;
    @BindView(R2.id.lv_course_detail)
    ListView mLvCourseDetail;
    @BindView(R2.id.ll_bottom)
    LinearLayout mLlBottom;

    private int mCourseId;
    private CourseDetailAdapter mAdapter;
    private CourseDetail mCourseDetail;
    private CourseDetailHeaderLayout mHeaderView;
    private CourseDetailFooterLayout mFooterView;

    @OnClick(R2.id.iv_detail_back)
    void onBackClick() {
        finish();
    }

    @Override
    protected int initView() {
        return R.layout.activity_course_detail;
    }

    @Override
    public void initData() {
        mIvDetailLoading.setVisibility(View.VISIBLE);
        AnimationDrawable anim = (AnimationDrawable) mIvDetailLoading.getDrawable();
        anim.start();
        mCourseId = getIntent().getIntExtra("course_id", 0);
        RequestCenter.courseDetail(mCourseId, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                mCourseDetail = (CourseDetail) responseObj;
                updateUI();
            }

            @Override
            public void onFailure(Object reasonObj) {
            }
        });
    }

    private void updateUI() {
        mLvCourseDetail.setVisibility(View.VISIBLE);
        mIvDetailLoading.setVisibility(View.GONE);
        mAdapter = new CourseDetailAdapter(CourseDetailActivity.this, mCourseDetail.data.body);
        mLvCourseDetail.setAdapter(mAdapter);
        if (mHeaderView != null) {
            mLvCourseDetail.removeHeaderView(mHeaderView);
        }
        mHeaderView = new CourseDetailHeaderLayout(this, mCourseDetail.data.head);
        mLvCourseDetail.addHeaderView(mHeaderView);
        if (mFooterView != null) {
            mLvCourseDetail.removeFooterView(mFooterView);
        }
        mFooterView = new CourseDetailFooterLayout(this, mCourseDetail.data.footer);
        mLvCourseDetail.addFooterView(mFooterView);
        mLlBottom.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initView();
        initData();
    }

}
