package com.hanjinliang.androidstudy.customerviews.TimeTextView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.hanjinliang.androidstudy.R;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class TimeTextViewActivity extends AppCompatActivity {
    TimeTextView timeTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_text_view);
         timeTextView=findViewById(R.id.timeTextView);
        timeTextView.startCount((System.currentTimeMillis()-60*60)+"");
        ListView lv_time=findViewById(R.id.lv_time);
        lv_time.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 100;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder;
                if(convertView==null){
                    convertView= LayoutInflater.from(TimeTextViewActivity.this).inflate(R.layout.time_count_list_item,parent,false);
                    viewHolder=new ViewHolder(convertView);
                    convertView.setTag(viewHolder);
                }else{
                    viewHolder= (ViewHolder) convertView.getTag();
                }
                viewHolder.getTextView().startCount(System.currentTimeMillis()/1000l,(System.currentTimeMillis()/1000l+(new Random().nextInt(60*60*24))));
                return convertView;
            }
        });
    }
    public class ViewHolder{
        View mView;
        TimeCountView mTextView;
        public ViewHolder(View view){
            mView=view;
        }
        public TimeCountView getTextView() {
            if(mTextView==null){
                mTextView= (TimeCountView) mView.findViewById(R.id.item_timeTextView);
            }
            return mTextView;
        }


    }
}
