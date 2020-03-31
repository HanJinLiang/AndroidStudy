package com.hanjinliang.androidstudy.thirdLibs.MPChart;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.hanjinliang.androidstudy.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 测试MPChart大数据问题
 */
public class MPChartDemoActivity extends AppCompatActivity {
    LinearLayout layout_chart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpchart_demo);
        layout_chart= (LinearLayout) findViewById(R.id.layout_chart);
    }

    public void one(View view){
        layout_chart.removeAllViews();
        BarChart barChart=getMultBarChart(this);
        layout_chart.addView(barChart);
        ArrayList<WorkLoadBean> datas=new ArrayList<>();
        for(int i=0;i<1000;i++){
            datas.add(new WorkLoadBean("index"+i,10,5));
        }
        setMultBarChartData(barChart,datas,"fangl","cheshu");
    }

    public void two(View view){
        layout_chart.removeAllViews();
        BarChart barChart=getMultBarChart(this);
        layout_chart.addView(barChart);
        ArrayList<WorkLoadBean> datas=new ArrayList<>();
        for(int i=0;i<1000;i++){
            datas.add(new WorkLoadBean("index"+i,9,5));
        }
        setMultBarChartData(barChart,datas,"fangl","cheshu");
    }


    //获取2重柱状图控件
    public BarChart getMultBarChart(Context context) {
        if(context==null){//防止空指针
            return  null;
        }
        BarChart mChart = new BarChart(context);
        mChart.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        //mChart.setOnChartValueSelectedListener(this);
        mChart.getDescription().setEnabled(false);
        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawBarShadow(false);

        mChart.setDrawGridBackground(false);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setCenterAxisLabels(true);

        ////左边Y轴设置
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setGranularityEnabled(true);
        leftAxis.setGranularity(1f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        //右边Y轴设置
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(true);
        rightAxis.setAxisMinimum(0);
        leftAxis.setSpaceTop(35f);
        rightAxis.setGranularity(1f);
        rightAxis.setGranularityEnabled(true);
        rightAxis.disableAxisLineDashedLine();
        rightAxis.disableGridDashedLine();
        rightAxis.setDrawGridLines(false);


        mChart.setDoubleTapToZoomEnabled(false);
        //空数据显示
        mChart.setNoDataText("暂无数据");
        mChart.setScaleYEnabled(true);
        mChart.setHighlightFullBarEnabled(false);
        return mChart;
    }


    /**
     * 2重柱状图设置数据源
     * @param mChart
     * @param datas  数据源
     * @param leftLabel  左边Y轴Label
     * @param rightLabel  右边Y轴Label
     */
    public void setMultBarChartData(BarChart mChart, ArrayList<WorkLoadBean> datas, String leftLabel, String rightLabel) {
        if(datas==null||mChart==null){//防止空指针
            return  ;
        }
        //所占比例
        float groupSpace = 0.08f;
        float barSpace = 0.01f; // x4 DataSet
        float barWidth = 0.45f; // x4 DataSet
        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();

        for (int i = 0; i < datas.size(); i++) {
            yVals1.add(new BarEntry(i, datas.get(i).getFl()));
            yVals2.add(new BarEntry(i, datas.get(i).getCarNum()));
        }


        BarDataSet set1, set2;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) mChart.getData().getDataSetByIndex(1);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();

        } else {
            // create 4 DataSets
            set1 = new BarDataSet(yVals1, leftLabel);
            set1.setColor(Color.parseColor("#fbc200"));
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return new DecimalFormat("###,###,###,##0.0").format(value);
                }
            });
            //set1.setBarBorderWidth(1f);
            set2 = new BarDataSet(yVals2, rightLabel);
            set2.setColor(Color.parseColor("#1fbad6"));
            set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
            //set2.setBarBorderWidth(1f);
            set2.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return new DecimalFormat("###,###,###,##0.0").format(value);
                }
            });

            BarData data = new BarData(set1, set2);
            data.setHighlightEnabled(false);
            mChart.setData(data);
        }

        // specify the width each bar should have
        mChart.getBarData().setBarWidth(barWidth);
        // restrict the x-axis range

        //控制一个屏幕显示4组
        mChart.setVisibleXRangeMaximum(4);
        mChart.setVisibleXRangeMinimum(4);
        //控制分组
        mChart.getXAxis().setAxisMinimum(0);
        mChart.getXAxis().setAxisMaximum(mChart.getBarData().getGroupWidth(groupSpace, barSpace) * datas.size());

        mChart.getXAxis().setValueFormatter(new MultBarChartValueFormat(mChart, datas));
        mChart.setAutoScaleMinMaxEnabled(false);

        mChart.groupBars(0, groupSpace, barSpace);
        mChart.animateY(1000);
        mChart.moveViewToX(datas.size());
    }

    public class WorkLoadBean {

        private String name;

        private float fl;//方量

        private float carNum;//车数

        public WorkLoadBean(String name, float fl, float carNum) {
            this.name = name;
            this.fl = fl;
            this.carNum = carNum;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getFl() {
            return fl;
        }

        public void setFl(float fl) {
            this.fl = fl;
        }

        public float getCarNum() {
            return carNum;
        }

        public void setCarNum(float carNum) {
            this.carNum = carNum;
        }
    }
    public class MultBarChartValueFormat implements IAxisValueFormatter {

        BarChart mChart;

        ArrayList<WorkLoadBean> datas;

        public MultBarChartValueFormat(BarChart mChart, ArrayList<WorkLoadBean> datas) {
            this.mChart = mChart;
            this.datas = datas;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            int i = (int) value;
            if(i>=0&& i<datas.size() && datas.get(i)!=null){
                return datas.get(i).getName()+"";
            }
            return "";
        }
    }
}
