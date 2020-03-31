package com.hanjinliang.androidstudy.customerviews.stepview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by HanJinLiang on 2017-11-17.
 */
public class StepView extends RecyclerView {
    ArrayList<StepViewBean> mDatas=new ArrayList<>();
    private int mLineFinishedColor= Color.parseColor("#2b2b2b");
    private int mLineUnfinishedColor= Color.parseColor("#afafaf");
    private int mLinePadding=dip2px(5);
    private int mDescribeTextColor= Color.parseColor("#2b2b2b");
    private int mDescribeTextSize=14;
    private int mDirection=Direction.horizontal;
    StepViewAdapter mAdapter;
    public static class Direction{
        //值必须和系统的保持一致
        public static final int horizontal=0;
        public static final int vertical=1;
    }

    public StepView(Context context) {
        this(context,null);
    }

    public StepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StepView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.StepView,defStyle,0);
        mLineFinishedColor=ta.getColor(R.styleable.StepView_LineFinishedColor,mLineFinishedColor);
        mLineUnfinishedColor=ta.getColor(R.styleable.StepView_LineUnfinishedColor,mLineUnfinishedColor);
        mLinePadding=ta.getInteger(R.styleable.StepView_LinePadding,mLinePadding);
        mDescribeTextColor=ta.getColor(R.styleable.StepView_DescribeTextColor,mDescribeTextColor);
        mDescribeTextSize=ta.getInteger(R.styleable.StepView_DescribeTextSize,mDescribeTextSize);
        mDirection=ta.getInteger(R.styleable.StepView_Direction,Direction.horizontal);
        ta.recycle();

        initView();
    }

    private void initView() {
        setLayoutManager(new LinearLayoutManager(getContext(),mDirection,false));
        mAdapter=new StepViewAdapter(getContext(),mDatas,mDirection);
        setAdapter(mAdapter);
    }

    public void initData(ArrayList<StepViewBean> datas){
        mDatas.clear();
        if(datas!=null){
            mDatas.addAll(datas);
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * dp转成px
     * @param dipValue
     * @return
     */
    private   int dip2px( float dipValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    /**
     * 适配器
     */
    public class StepViewAdapter extends RecyclerView.Adapter<StepViewAdapter.ViewHolder>{
        private Context mContext;
        ArrayList<StepViewBean> mDatas;
        private int mDirection;
        public StepViewAdapter(Context context, ArrayList<StepViewBean> data,int direction){
            mContext=context;
            mDatas=data;
            mDirection=direction;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(mContext).inflate(R.layout.view_stepview_horizontal,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            StepViewBean stepViewBean=mDatas.get(position);
            if(stepViewBean.isFinished()){
                holder.getStepLine().setBackgroundColor(mLineFinishedColor);
                holder.getStepIcon().setImageResource(R.drawable.round_step_finished);
            }else{
                holder.getStepLine().setBackgroundColor(mLineUnfinishedColor);
                holder.getStepIcon().setImageResource(R.drawable.round_step_unfinished);
            }
            holder.getStepDes().setText(stepViewBean.getDescribe());
            if(position==mDatas.size()-1){//最后一个隐藏
                holder.getStepLine().setVisibility(GONE);
            }else{
                holder.getStepLine().setVisibility(VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            if(mDatas==null){
                return 0;
            }
            return mDatas.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            ImageView stepIcon;
            TextView mStepDes;
            View stepLine;
            public ViewHolder(View itemView) {
                super(itemView);
                stepIcon= (ImageView) itemView.findViewById(R.id.stepIcon);
                mStepDes= (TextView) itemView.findViewById(R.id.step_des);
                stepLine=  itemView.findViewById(R.id.step_line);
            }

            public ImageView getStepIcon() {
                return stepIcon;
            }

            public TextView getStepDes() {
                return mStepDes;
            }

            public View getStepLine() {
                return stepLine;
            }
        }
    }
}
