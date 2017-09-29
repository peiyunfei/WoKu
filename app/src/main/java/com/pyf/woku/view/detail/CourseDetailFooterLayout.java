package com.pyf.woku.view.detail;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.pyf.woku.R;
import com.pyf.woku.bean.CourseDetail.DataBean.FooterBean;
import com.pyf.woku.imageloader.ImageLoaderManager;
import com.pyf.woku.util.DateFormatHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/29
 */
public class CourseDetailFooterLayout extends LinearLayout {

    private Context mContext;
    private FooterBean mFooter;
    private ImageLoaderManager mImageLoader;

    private LinearLayout mLlLine;
    private LineChart lineView;
    private Resources resource;
    private float yAxisMax = -1.0f;
    private float yAxisMin = 100.0f;
    private float yAxisGap = 10f;
    private int yAxislabelNum = 5;

    private ImageView[] mIvImages = new ImageView[2];
    private TextView[] mTvNames = new TextView[2];
    private TextView[] mTvZans = new TextView[2];
    private TextView[] mTvPrices = new TextView[2];

    public CourseDetailFooterLayout(Context context, FooterBean footer) {
        this(context, null, footer);
    }

    public CourseDetailFooterLayout(Context context, @Nullable AttributeSet attrs,
                                    FooterBean footer) {
        super(context, attrs);
        mContext = context;
        mFooter = footer;
        mImageLoader = ImageLoaderManager.getInstance(context);
        initView();
        initData();
    }

    private void initData() {
        List<FooterBean.RecommandBean> recommends = mFooter.recommand;
        for (int i = 0; i < recommends.size(); i++) {
            mImageLoader.displayImage(mIvImages[i], recommends.get(i).imageUrl);
            mTvNames[i].setText(recommends.get(i).name);
            mTvZans[i].setText(recommends.get(i).zan);
            mTvPrices[i].setText(recommends.get(i).price);
        }
        addLineChartView();
    }

    /**
     * 获取最大最小值
     *
     * @param currentNum
     */
    private void initMaxMin(float currentNum) {
        if (currentNum >= yAxisMax) {
            yAxisMax = currentNum;
        }
        if (currentNum < yAxisMin) {
            yAxisMin = currentNum;
        }
    }

    /**
     * 绘制图表
     */
    private void addLineChartView() {
        lineView = new LineChart(mContext);
        lineView.setDescription("");
        lineView.setScaleEnabled(false);
        lineView.getAxisRight().setEnabled(true);
        lineView.setDrawGridBackground(false);
        lineView.setTouchEnabled(false);
        lineView.getLegend().setEnabled(false);
        lineView.setHardwareAccelerationEnabled(true);

        ArrayList<Entry> yRawData = new ArrayList<>();
        ArrayList<String> xRawDatas = new ArrayList<>();
        int index = 0;
        for (int i = mFooter.time.size() - 1; i >= 0; i--) {
            if (mFooter.time.get(i) != null) {
                FooterBean.TimeBean value = mFooter.time.get(i);
                yRawData.add(new Entry(Float.parseFloat(value.count), index));
                xRawDatas.add(DateFormatHelper.formatDate(value.dt
                        .concat("000"), DateFormatHelper.DateFormat.DATE_1));
                index++;
                initMaxMin(Float.parseFloat(value.count));
            }
        }
        /**
         * x轴样式设置
         */
        XAxis xAxis = lineView.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);// 设置x轴在底部显示
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setXOffset(0); // x轴间距
        xAxis.setTextColor(resource.getColor(R.color.color_999999));
        xAxis.setAxisLineColor(resource.getColor(R.color.color_dddddd));
        xAxis.setDrawGridLines(true);
        xAxis.setGridColor(resource.getColor(R.color.color_dddddd));
        /**
         * y轴样式设置
         */
        YAxis leftAxis = lineView.getAxisLeft();
        leftAxis.resetAxisMinValue();
        leftAxis.setLabelCount(yAxislabelNum, true);
        leftAxis.setDrawLimitLinesBehindData(true);
        leftAxis.setTextColor(resource.getColor(R.color.color_999999));
        leftAxis.setAxisLineColor(resource.getColor(R.color.color_dddddd));
        leftAxis.setDrawGridLines(true);
        leftAxis.setGridColor(resource.getColor(R.color.color_dddddd));
        leftAxis.setAxisMaxValue(yAxisMax + yAxisGap); // 设置Y轴最大值
        leftAxis.setAxisMinValue(yAxisMin - yAxisGap);// 设置Y轴最小值

        YAxis rightAxis = lineView.getAxisRight();
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisLineColor(resource.getColor(R.color.color_dddddd));


        /**
         * 曲线样式设置
         */
        LineDataSet set = new LineDataSet(yRawData, "");
        set.setDrawCubic(true);
        set.setCubicIntensity(0.2f);
        set.setLineWidth(2.0f);
        set.setColor(resource.getColor(R.color.color_fd4634));
        set.setCircleSize(3.0f);
        set.setCircleColor(resource.getColor(R.color.color_fd4634));
        set.setFillColor(resource.getColor(R.color.color_fd4634));
        set.setFillAlpha(128);
        set.setDrawFilled(true);
        set.setDrawValues(false);

        LineData data = new LineData(xRawDatas, set);
        lineView.setData(data);
        lineView.invalidate();

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mLlLine.addView(lineView, params);
    }

    private void initView() {
        LinearLayout rootView = (LinearLayout) LayoutInflater
                .from(mContext).inflate(R.layout.item_detail_footer, this);
        mLlLine = (LinearLayout) rootView.findViewById(R.id.ll_detail_footer_line);
        mIvImages[0] = (ImageView) rootView.findViewById(R.id.iv_detail_footer_one_image);
        mIvImages[1] = (ImageView) rootView.findViewById(R.id.iv_detail_footer_two_image);
        mTvNames[0] = (TextView) rootView.findViewById(R.id.tv_detail_footer_one_name);
        mTvNames[1] = (TextView) rootView.findViewById(R.id.tv_detail_footer_two_name);
        mTvZans[0] = (TextView) rootView.findViewById(R.id.tv_detail_footer_one_zan);
        mTvZans[1] = (TextView) rootView.findViewById(R.id.tv_detail_footer_two_zan);
        mTvPrices[0] = (TextView) rootView.findViewById(R.id.tv_detail_footer_one_price);
        mTvPrices[1] = (TextView) rootView.findViewById(R.id.tv_detail_footer_two_price);
    }
}
