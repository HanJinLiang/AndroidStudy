package com.hanjinliang.androidstudy.customerviews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hanjinliang.androidstudy.R;
import com.hanjinliang.androidstudy.customerviews.ArcMenu.ArcMenuActivity;
import com.hanjinliang.androidstudy.customerviews.CheckableLinearLayout.CheckableLayoutActivity;
import com.hanjinliang.androidstudy.customerviews.CustomerProgressBar.CustomerProgressBarActivity;
import com.hanjinliang.androidstudy.customerviews.CustomerRefreshLayout.CustomerRefreshActivity;
import com.hanjinliang.androidstudy.customerviews.DashLine.DashLineActivity;
import com.hanjinliang.androidstudy.customerviews.EraserView.EraserViewActivity;
import com.hanjinliang.androidstudy.customerviews.EraserView.GuagualeViewActivity;
import com.hanjinliang.androidstudy.customerviews.HealthIndexView.HealthIndexViewActivity;
import com.hanjinliang.androidstudy.customerviews.LineView.LineViewActivity;
import com.hanjinliang.androidstudy.customerviews.LoadingView.CircleLoadingViewActivity;
import com.hanjinliang.androidstudy.customerviews.NetsScoreView.NetsScoreViewActivity;
import com.hanjinliang.androidstudy.customerviews.NinePhotoView.NinePhotoViewActivity;
import com.hanjinliang.androidstudy.customerviews.NumView.NumViewActivity;
import com.hanjinliang.androidstudy.customerviews.PieChart.PieChartActivity;
import com.hanjinliang.androidstudy.customerviews.Region.RegionCircleActivity;
import com.hanjinliang.androidstudy.customerviews.Scroller.ScrollerTestActivity;
import com.hanjinliang.androidstudy.customerviews.SearchView.SearchViewActivity;
import com.hanjinliang.androidstudy.customerviews.SelectLayout.SelectLayoutActivity;
import com.hanjinliang.androidstudy.customerviews.StatusLayout.StatusLayoutActivity;
import com.hanjinliang.androidstudy.customerviews.StatusScrollView.StatusScrollViewActivity;
import com.hanjinliang.androidstudy.customerviews.TimeTextView.TimeTextViewActivity;
import com.hanjinliang.androidstudy.customerviews.WeightView.WeightProgressViewActivity;
import com.hanjinliang.androidstudy.customerviews.banner.BannerActivity;
import com.hanjinliang.androidstudy.customerviews.basestudy.BaseStudyActivity;
import com.hanjinliang.androidstudy.customerviews.basestudy.patheffect.PathEffectActivity;
import com.hanjinliang.androidstudy.customerviews.calendar.CalendarDemoActivity;
import com.hanjinliang.androidstudy.customerviews.circleimage.CircleImageViewActivity;
import com.hanjinliang.androidstudy.customerviews.curve.CurveActivity;
import com.hanjinliang.androidstudy.customerviews.curve.CurveView;
import com.hanjinliang.androidstudy.customerviews.favorlayout.FavorLayoutActivity;
import com.hanjinliang.androidstudy.customerviews.flowlayout.FlowLayoutActivity;
import com.hanjinliang.androidstudy.customerviews.horizontalselectedview.HorizontalSelectedViewActivity;
import com.hanjinliang.androidstudy.customerviews.linerecyclerview.RecyclerLineViewActivity;
import com.hanjinliang.androidstudy.customerviews.radarview.RadarViewActivity;
import com.hanjinliang.androidstudy.customerviews.rotate.RotateActivity;
import com.hanjinliang.androidstudy.customerviews.scorlltextlayout.UpDownTextViewActivity;
import com.hanjinliang.androidstudy.customerviews.stepview.StepViewActivity;
import com.hanjinliang.androidstudy.customerviews.timeselect.DateTimePickerDemoActivity;
import com.hanjinliang.androidstudy.customerviews.waveline.WaveLineActivity;
import com.hanjinliang.androidstudy.customerviews.wheelpicker.WheelPickerDemoActivity;
import com.hanjinliang.androidstudy.systemwidget.appbarlayout.AppBarLayoutActivity;
import com.hanjinliang.androidstudy.systemwidget.appbarlayout.ToolBarActivity;
import com.hanjinliang.androidstudy.systemwidget.coordinatorlayout.CoordinatorLayoutActivity;

/**
 * 自定义view学习
 */
public class CustomerViewStudyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerview_study);
    }



    /**
     * StatusScrollView
     * @param view
     */
    public void StatusScrollView(View view){
        startActivity(new Intent(this, StatusScrollViewActivity.class));
    }

    /**
     * WheelPicker
     * @param view
     */
    public void ScrollerTest(View view){
        startActivity(new Intent(this, ScrollerTestActivity.class));
    }

    /**
     * WheelPicker
     * @param view
     */
    public void WheelPicker(View view){
        startActivity(new Intent(this, WheelPickerDemoActivity.class));
    }

    /**
     * 自定义日历学习
     * @param view
     */
    public void LoadingView(View view){
        startActivity(new Intent(this, CircleLoadingViewActivity.class));
    }
    /**
     * 自定义日历学习
     * @param view
     */
    public void CalendarView(View view){
        startActivity(new Intent(this, CalendarDemoActivity.class));
    }
    /**
     * HorizontalSelectedView学习
     * @param view
     */
    public void HorizontalSelectedView(View view){
        startActivity(new Intent(this, HorizontalSelectedViewActivity.class));
    }

    /**
     * CustomerProgressBar学习
     * @param view
     */
    public void CustomerProgressBar(View view){
        startActivity(new Intent(this, CustomerProgressBarActivity.class));
    }

    /**
     * Curve学习
     * @param view
     */
    public void CurveView(View view){
        startActivity(new Intent(this, CurveActivity.class));
    }
    /**
     * EraserView学习  橡皮擦效果
     * @param view
     */
    public void EraserView(View view){
        startActivity(new Intent(this, EraserViewActivity.class));
    }
    /**
     * GuagualeView学习  刮刮乐效果
     * @param view
     */
    public void  GuagualeView(View view){
        startActivity(new Intent(this,  GuagualeViewActivity.class));
    }

    /**
     * CircleImageView学习  刮刮乐效果
     * @param view
     */
    public void  CircleImageView(View view){
        startActivity(new Intent(this,  CircleImageViewActivity.class));
    }

    /**
     * RadarView学习  l雷达
     * @param view
     */
    public void  RadarView(View view){
        startActivity(new Intent(this,  RadarViewActivity.class));
    }

    /**
     * PathEffect学习
     * @param view
     */
    public void  PathEffect(View view){
        startActivity(new Intent(this,  PathEffectActivity.class));
    }

    /**
     * FavorLayout学习
     * @param view
     */
    public void  FavorLayout(View view){
        startActivity(new Intent(this,  FavorLayoutActivity.class));
    }

    /**
     * NinePhotoView学习
     * @param view
     */
    public void  NinePhotoView(View view){
        startActivity(new Intent(this,  NinePhotoViewActivity.class));
    }
    /**
     * NetsScoreView学习
     * @param view
     */
    public void  NetsScoreView(View view){
        startActivity(new Intent(this,  NetsScoreViewActivity.class));
    }

    /**
     * 自定义View基础知识学习
     * @param view
     */
    public void  BaseStudy(View view){
        startActivity(new Intent(this,  BaseStudyActivity.class));
    }

    /**
     * 自定义View  搜索
     * @param view
     */
    public void  SearchView(View view){
        startActivity(new Intent(this,  SearchViewActivity.class));
    }

    /**
     * 自定义View  搜索
     * @param view
     */
    public void  RegionCircle(View view){
        startActivity(new Intent(this,  RegionCircleActivity.class));
    }


    /**
     *CheckableLayout  ListView多选
     * @param view
     */
    public void  CheckableLayout(View view){
        startActivity(new Intent(this,  CheckableLayoutActivity.class));
    }

    /**
     *WaveLine  波浪线
     * @param view
     */
    public void  WaveLine(View view){
        startActivity(new Intent(this,  WaveLineActivity.class));
    }

    /**
     *AcrMenu  扇形菜单
     * @param view
     */
    public void  AcrMenu(View view){
        startActivity(new Intent(this,  ArcMenuActivity.class));
    }

    /**
     *HealthIndexView
     * @param view
     */
    public void  HealthIndexView(View view){
        startActivity(new Intent(this,  HealthIndexViewActivity.class));
    }

    /**
     *LineView
     * @param view
     */
    public void  LineView(View view){
        startActivity(new Intent(this,  LineViewActivity.class));
    }

    /**
     *FlowLayout
     * @param view
     */
    public void  FlowLayout(View view){
        startActivity(new Intent(this,  FlowLayoutActivity.class));
    }



    /**
     *FlowLayout
     * @param view
     */
    public void  WeightProgressView(View view){
        startActivity(new Intent(this,  WeightProgressViewActivity.class));
    }

    /**
     *FlowLayout
     * @param view
     */
    public void  SelectLayout(View view){
        startActivity(new Intent(this,  SelectLayoutActivity.class));
    }

    /**
     *StepView
     * @param view
     */
    public void  StepView(View view){
        startActivity(new Intent(this,  StepViewActivity.class));
    }

    /**
     *StepView
     * @param view
     */
    public void  NumView(View view){
        startActivity(new Intent(this,  NumViewActivity.class));
    }

    /**
     *PieChart
     * @param view
     */
    public void  PieChart(View view){
        startActivity(new Intent(this,  PieChartActivity.class));
    }

    /**
     *RecyclerViewBanner
     * @param view
     */
    public void  RecyclerViewBanner(View view){
        startActivity(new Intent(this,  BannerActivity.class));
    }
    /**
     *DashLineView  虚线View
     * @param view
     */
    public void  DashLineView(View view){
        startActivity(new Intent(this,  DashLineActivity.class));
    }
    /**
     *DashLineView  虚线View
     * @param view
     */
    public void  DateTimePickerDemo(View view){
        startActivity(new Intent(this,  DateTimePickerDemoActivity.class));
    }

    /**
     *TimeTextView  计时效果的TextView
     * @param view
     */
    public void  TimeTextView(View view){
        startActivity(new Intent(this,  TimeTextViewActivity.class));
    }


    /**
     *TimeTextView  计时效果的TextView
     * @param view
     */
    public void  RecycerLineView(View view){
        startActivity(new Intent(this,  RecyclerLineViewActivity.class));
    }
    public void  UpDownTextViewActivity(View view){
        startActivity(new Intent(this,  UpDownTextViewActivity.class));
    }
    public void  CustomerRefreshLayout(View view){
        startActivity(new Intent(this,  CustomerRefreshActivity.class));
    }



    public void View3dRotate(View view){
        startActivity(new Intent(this, RotateActivity.class));
    }

    public void StatusLayoutActivity(View view){
        startActivity(new Intent(this,StatusLayoutActivity.class));
    }
}
