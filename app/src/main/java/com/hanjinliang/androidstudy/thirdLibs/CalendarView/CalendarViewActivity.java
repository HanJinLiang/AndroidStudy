package com.hanjinliang.androidstudy.thirdLibs.CalendarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.haibin.calendarview.SimpleCalendarLayout;
import com.hanjinliang.androidstudy.R;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CalendarView calendarView;
    TextView tvCurrentMonth;
    SimpleCalendarLayout calendarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

        calendarLayout=findViewById(R.id.calendarLayout);
        findViewById(R.id.expand).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarLayout.toggleStatus();
            }
        });
        recyclerView=findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TodoAdapter todoAdapter=new TodoAdapter();
        recyclerView.setAdapter(todoAdapter);
        todoAdapter.addData(Arrays.asList("1","1","1","1","1","1","1","1","1","1","1","1","1","1"));
        initCalendar();
    }
    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }

    private void initCalendar() {
        calendarView=findViewById(R.id.calendarView);
        tvCurrentMonth=findViewById(R.id.tvCurrentMonth);
        int year = calendarView.getCurYear();
        int month = calendarView.getCurMonth();

        Map<String, Calendar> map = new HashMap<>();
        map.put(getSchemeCalendar(year, month, 3, 0xFF40db25, "假").toString(),
                getSchemeCalendar(year, month, 3, 0xFF40db25, "假"));
        map.put(getSchemeCalendar(year, month, 13, 0xFFedc56d, "记").toString(),
                getSchemeCalendar(year, month, 13, 0xFFedc56d, "记"));
        map.put(getSchemeCalendar(year, month, 15, 0xFFaacc44, "假").toString(),
                getSchemeCalendar(year, month, 15, 0xFFaacc44, "假"));


        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        calendarView.setSchemeDate(map);

         tvCurrentMonth.setText(year + "年" +month + "月");
        calendarLayout.setSimpleCallback(new SimpleCalendarLayout.SimpleCallback() {
            @Override
            public void onWeekChange(List<Calendar> weekCalendars) {
                tvCurrentMonth.setText(weekCalendars.get(0).getYear()+ "年" + weekCalendars.get(0).getMonth() + "月");
            }

            @Override
            public void onMonthChange(int year, int month) {
                tvCurrentMonth.setText(year+ "年" + month + "月");
            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
                tvCurrentMonth.setText(calendar.getYear()+ "年" + calendar.getMonth() + "月");
                if(isClick){
                    ToastUtils.showLong("选择："+ TimeUtils.millis2String(calendar.getTimeInMillis()));
                }
            }

            @Override
            public void onCalendarOutOfRange(Calendar calendar) {

            }
        });

    }
}