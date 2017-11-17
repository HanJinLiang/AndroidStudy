package com.hanjinliang.androidstudy.customerviews.stepview;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;

public class StepViewActivity extends AppCompatActivity {
    CustomStepView mStepView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_view);
        mStepView= (CustomStepView) findViewById(R.id.stepView);

        ArrayList<StepViewBean> datas=new ArrayList<>();
        datas.add(new StepViewBean("出厂了吗", ContextCompat.getDrawable(this,R.drawable.round_step_finished),true));
        datas.add(new StepViewBean("到达", ContextCompat.getDrawable(this,R.drawable.round_step_finished),true));
        datas.add(new StepViewBean("浇筑", ContextCompat.getDrawable(this,R.drawable.round_step_finished),true));
        datas.add(new StepViewBean("签收", ContextCompat.getDrawable(this,R.drawable.round_step_unfinished),false));
        datas.add(new StepViewBean("回厂", ContextCompat.getDrawable(this,R.drawable.round_step_unfinished),false));
        datas.add(new StepViewBean("排队", ContextCompat.getDrawable(this,R.drawable.round_step_unfinished),false));

        mStepView.initData(datas);
    }
}
