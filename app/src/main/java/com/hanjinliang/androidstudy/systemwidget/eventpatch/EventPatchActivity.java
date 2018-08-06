package com.hanjinliang.androidstudy.systemwidget.eventpatch;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;

public class EventPatchActivity extends AppCompatActivity {
    ViewPager mViewPager;
    ArrayList<View> mViews=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_patch);
        mViewPager=  findViewById(R.id.viewpager);

        for(int i=0;i<5;i++){
            if(i==3){
                HorizontalScrollView horizontalScrollView=new HorizontalScrollView(this);
                horizontalScrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500));

                TextView view=new TextView(this);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 100));
                view.setText("7月24日下午3时55分许，在华山长空栈道，一男子从容解开保险绳，稍作停顿，然后纵身一跃跳下悬崖，两旁正在通过栈道的游客目瞪口呆。3天后，华山景区搜救队寻获该男子遗体，警方也介入调查。"+i);
                view.setBackgroundColor(Color.RED);
                horizontalScrollView.addView(view);

                mViews.add(horizontalScrollView);
             continue;
            }
            TextView view=new TextView(this);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            view.setText("index===="+i);
            view.setBackgroundColor(i%2==0?Color.BLUE:Color.GRAY);
            mViews.add(view);
        }
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mViews.get(position));
                return mViews.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViews.get(position));
            }
        });
    }

}
