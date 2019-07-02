package com.hanjinliang.androidstudy.customerviews.scorlltextlayout;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Elvis on 2017/2/13.
 */

public class VerticalTextView extends TextSwitcher implements ViewSwitcher.ViewFactory {
	private static final int FLAG_START_AUTO_SCROLL = 0;
	private static final int FLAG_STOP_AUTO_SCROLL = 1;
	private ArrayList<String> showList = new ArrayList<>();        // 实际滚动数据组

	private Context mContext;
	// 字体默认黑色
	private int verticalTextColor ;
	// 字体大小(px)
	private float mTextSize=50;

	// 所有文本从滑入到滑出，以及停留的时间
	private long mAnimDurationTotal = 3000;
	// 最终滚动结束，需要显示的数据
	private long currentNum = 0;    //当前的num
	private int currentId = -1;        //滚动下标
	private int scrollNum = 1;        //滚动数量
	private Handler handler;

	public VerticalTextView(Context context) {
		this(context, null);
		mContext = context;
	}

	public VerticalTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	public void setAnimTime() {
		if (getChildCount() < 2) {
			setFactory(this);
		}
		if (showList.size() > 0) {
			scrollNum = showList.size();
		}
		Animation in = new TranslateAnimation(0, 0, 100, 0);
		in.setDuration(mAnimDurationTotal / scrollNum);
		in.setInterpolator(new LinearInterpolator());
		Animation out = new TranslateAnimation(0, 0, 0, -100);
		out.setDuration(mAnimDurationTotal / scrollNum);
		out.setInterpolator(new LinearInterpolator());
		setInAnimation(in);
		setOutAnimation(out);
	}

	/**
	 * 间隔时间
	 */
	public void setTextStillTime() {
		if (handler != null) {
			return;
		}
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case FLAG_START_AUTO_SCROLL:
						if (showList.size() > 0) {
							currentId++;
							// 滚动一轮
							if (currentId >= showList.size()) {
								currentId = -1;        // 还原
								handler.removeMessages(FLAG_START_AUTO_SCROLL);
								return;
							}
							// 正常设置
							setText(showList.get(currentId));
							handler.sendEmptyMessageDelayed(FLAG_START_AUTO_SCROLL,
									mAnimDurationTotal / scrollNum);
						}
						break;
					case FLAG_STOP_AUTO_SCROLL:
						showList.clear();
						currentId = -1;        // 还原
						handler.removeMessages(FLAG_START_AUTO_SCROLL);
						break;
				}
			}
		};
	}

	/**
	 * 开始滚动
	 */
	public void startAutoScroll() {
		if (handler != null) {
			handler.sendEmptyMessage(FLAG_START_AUTO_SCROLL);
		}
	}

	/**
	 * 停止滚动
	 */
	public void stopAutoScroll() {
		if (handler != null) {
			handler.sendEmptyMessage(FLAG_STOP_AUTO_SCROLL);
		}
	}

	@Override
	public View makeView() {
		TextView t = new TextView(mContext);
		t.setMaxLines(1);
		t.setTextColor(verticalTextColor);
		t.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
		t.setGravity(Gravity.CENTER);
		return t;
	}

	// 设置最终的赋值
	public void setNum(String num) {
		//当前输入的内容是否为数字
		// 不是
		if (!isNumeric(num)) {
			showList.add(num);
			return;
		}
		// 是
		long showNum = Long.parseLong(num);
		showList.clear();
		currentId = -1;        // 还原
		//根据currentNum 与 num 算 得出showList
		if (showNum < currentNum) {
			long count = 11 - currentNum + showNum;
			// 第二次数据比之前数据小，叠加到最大，从0加到第二次数据
			for (int i = 0; i < count; i++) {
				showList.add(String.valueOf(currentNum));
				currentNum = (++currentNum) % 10;
			}
		} else {
			for (long i = currentNum; i <= showNum; i++) {
				showList.add(String.valueOf(i));
			}
		}
		//修改初始化
		currentNum = showNum;
	}

	// 设置所有文本滚动到停留的动画总时长
	public void setDurationTotal(long mAnimDurationTotal) {
		this.mAnimDurationTotal = mAnimDurationTotal;
		setAnimTime();
	}

	public void setVerticalTextColor(int verticalTextColor) {
		this.verticalTextColor = verticalTextColor;
	}

	public void setTextSize(int textSize) {
		this.mTextSize = textSize;
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
}
