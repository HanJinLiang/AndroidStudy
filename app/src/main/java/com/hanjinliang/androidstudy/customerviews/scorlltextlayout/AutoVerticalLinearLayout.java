package com.hanjinliang.androidstudy.customerviews.scorlltextlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;


/**
 * Created by Elvis on 2017/2/14.
 */
public class AutoVerticalLinearLayout extends LinearLayout {
	private Context mContext;
	private List<String> datas = new ArrayList<>();        //数字按照个十百千
	private List<VerticalTextView> views = new ArrayList<>();
	// 字体默认黑色
	private int verticalTextColor = getResources().getColor(android.R.color.black);
	// 字体大小(dp)
	private int mTextSize = 50;
	// 所有文本从滑入到滑出，以及停留的时间
	private long mAnimDurationTotal;

	public AutoVerticalLinearLayout(Context context) {
		this(context, null);
	}

	public AutoVerticalLinearLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public AutoVerticalLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
	}

	public void addData(String data) {
		// 将数字转换为char数组
		char[] chars = String.valueOf(data).toCharArray();
		datas.clear();
		for (char ch : chars) {
			// 直接将char转换为int 拿到的数据为ASCII码值
			datas.add(String.valueOf(ch));
		}
		// 第二次的数据等于第一次的数据
		addView();
	}

	private void addView() {
		// 显示数字位数 > 当前view数
		if (datas.size() > views.size()) {
			// 显示数字位数 > view数，进行追加
			int count = datas.size() - views.size();
			for (int i = 0; i < count; i++) {
				VerticalTextView tv = getVerticalTextView();
				addView(tv, 0);
				views.add(0, tv);
			}
		} else if (datas.size() < views.size()) {
			views.clear();
			removeAllViews();
			for (String data : datas) {
				VerticalTextView tv = getVerticalTextView();
				tv.setNum(data);
				addView(tv);
				views.add(tv);
			}
			return;
		}
		for (int i = 0; i < views.size(); i++) {
			views.get(i).setNum(datas.get(i));
		}
	}

	@NonNull
	private VerticalTextView getVerticalTextView() {
		VerticalTextView tv = new VerticalTextView(mContext);
		tv.setVerticalTextColor(verticalTextColor);
		tv.setTextSize(mTextSize);
		LayoutParams lp = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
		lp.gravity = Gravity.CENTER;
		tv.setLayoutParams(lp);
		//tv.setPadding(0,0,50,0);
		return tv;
	}

	/**
	 * 开始动画
	 */
	public void startAnim() {
		if (views.size() >= 3) {
			mAnimDurationTotal = 3000;
		} else {
			mAnimDurationTotal = 1000 * views.size();
		}
		for (int i = 0; i < views.size(); i++) {
			VerticalTextView v = views.get(i);
			v.setDurationTotal(mAnimDurationTotal);
			v.setTextStillTime();
			v.startAutoScroll();
		}
	}

	public void clear() {
		views.clear();
		removeAllViews();
	}

	public Animation getInAnim() {
		if (getChildAt(0) != null) {
			return ((VerticalTextView) getChildAt(0)).getInAnimation();
		}
		return null;
	}

	public Animation getOutAnim() {
		if (getChildAt(0) != null) {
			return ((VerticalTextView) getChildAt(0)).getOutAnimation();
		}
		return null;
	}

}
