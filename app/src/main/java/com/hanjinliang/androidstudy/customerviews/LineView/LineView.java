package com.hanjinliang.androidstudy.customerviews.LineView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.blankj.utilcode.util.LogUtils;
import com.hanjinliang.androidstudy.R;
import com.hanjinliang.androidstudy.customerviews.CheckableLinearLayout.MyAdapter;

import java.util.ArrayList;

/**
 * Created by HanJinLiang on 2017-07-21.
 */

public class LineView extends View {
    Context mContext;
    Paint mPaintCoord;//坐标画笔
    Paint mXPaint;//X轴值
    Paint mPointPaint;//点 空心
    Paint mPathPaint;//线画笔
    Paint mPathFillPaint;//折线填充
    Paint mSelectedPaint;//选中颜色

    private int mScreenCount;//每行显示的个数 实际显示n+1个
    private float mXItemWidth;//X轴每一个宽度
    private float mPadding;//距离屏幕边距Padding
    private float mWidth,mHeight;//控件宽高
    //最大值
    private int mMax;//默认最大值为100

    //数据源
    private ArrayList<WeightData> mData=new ArrayList<WeightData>();

    //所有数据对应的点集合
    private ArrayList<PointF> mDataPointFs=new ArrayList<PointF>();
    //这线路径
    private Path mPath=new Path();
    //折线路径填充
    private Path mFillPath=new Path();
    //线的颜色
    private int mLineColor;

    //点的半径
    private float mPointRadius;
    //是否是填充
    private boolean isFill;
    //是否画XY坐标线
    private boolean isDrawXY;

    //X轴字体大小
    private float mXTextSize;
    //X轴字体颜色
    private int mXTextColor;


    public LineView(Context context) {
        this(context,null);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //关闭硬件加速
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setFocusable(true);
        setClickable(true);
        mContext=context;

        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.LineView,defStyleAttr,0);
        mPadding=ta.getDimension(R.styleable.LineView_XYPadding, dip2px(10));
        mPointRadius=ta.getDimension(R.styleable.LineView_PointRadius, dip2px(5));
        mLineColor=ta.getColor(R.styleable.LineView_LineColor,Color.parseColor("#f56e4f"));
        isFill=ta.getBoolean(R.styleable.LineView_isFill,true);
        isDrawXY=ta.getBoolean(R.styleable.LineView_isDrawXYLine,true);
        mScreenCount=ta.getInteger(R.styleable.LineView_ScreenCount,7);//一屏幕显示个数 默认就是显示7个
        mScreenCount--;//每行显示的个数 实际显示n+1个 所以减去1个

        mXTextSize=ta.getDimension(R.styleable.LineView_XTextSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,12,context.getResources().getDisplayMetrics()));
        mXTextColor=ta.getColor(R.styleable.LineView_XTextColor,Color.parseColor("#f56e4f"));
        mMax=ta.getInteger(R.styleable.LineView_MaxValue,100);
        ta.recycle();

        //坐标画笔
        mPaintCoord=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCoord.setColor(Color.GRAY);

        //坐标画笔
        mXPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mXPaint.setColor(mXTextColor);
        mXPaint.setTextAlign(Paint.Align.CENTER);
        mXPaint.setTextSize(mXTextSize);

        //圆点值画笔
        mPointPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint.setStyle(Paint.Style.STROKE);
        mPointPaint.setStrokeWidth(dip2px(1));
        mPointPaint.setColor(mLineColor);

        //曲线画笔
        mPathPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setStrokeWidth(dip2px(1));
        mPathPaint.setColor(mLineColor);

        //填充曲线
        mPathFillPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPathFillPaint.setStyle(Paint.Style.FILL);
        mPathFillPaint.setColor(mLineColor);

        //选中状态画笔
        mSelectedPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mSelectedPaint.setStyle(Paint.Style.FILL);
        mSelectedPaint.setColor(Color.GREEN);
    }

    /**
     * 分析数据
     */
    private void parseData() {
        mDataPointFs.clear();
        for(int i=0;i<mData.size();i++){
            float height=(mHeight-mPadding-mRect.height())/mMax*mData.get(i).getValue();
            mDataPointFs.add(new PointF(i*mXItemWidth,-height));
        }

        mPath.reset();
        for(int i=0;i<mDataPointFs.size();i++){
            if(i==0){
                mPath.moveTo(mDataPointFs.get(i).x,mDataPointFs.get(i).y);
            }else{
                mPath.lineTo(mDataPointFs.get(i).x,mDataPointFs.get(i).y);
            }
        }

        if(mDataPointFs==null||mDataPointFs.size()<=0){//防止为空
            return ;
        }
        mFillPath.reset();
        mFillPath.moveTo(mDataPointFs.get(0).x,mDataPointFs.get(0).y);
        mFillPath.addPath(mPath);
        mFillPath.lineTo(mDataPointFs.get(mDataPointFs.size()-1).x,0);
        mFillPath.lineTo(mDataPointFs.get(0).x,0);
        mFillPath.close();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LogUtils.e("onMeasure");
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);

        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        int width;
        if(widthMode==MeasureSpec.EXACTLY){//MatchParent
            width=widthSize;
        }else{//不确定的  给屏幕的宽度
            width=dip2px(getScreenWidth());//默认值
        }

        int height;
        if(heightMode==MeasureSpec.EXACTLY){//MatchParent
            height=heightSize;
        }else{
            height=dip2px(300);//默认值高度
        }
        setMeasuredDimension(width,height);
    }
    //测量拿到X轴文字高度矩形
    private Rect mRect=new Rect();
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        LogUtils.e("onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w;
        mHeight=h;

        mXItemWidth=(mWidth-mPadding*2)/mScreenCount;
        // FIXME 随便测量拿到X轴文字高度  不够精确
        mXPaint.getTextBounds("text测试",0,"text测试".length(),mRect);
        mPathFillPaint.setShader(new LinearGradient(0,-mHeight,0,0,mLineColor,Color.TRANSPARENT, Shader.TileMode.CLAMP));
        parseData();
    }
    Path mTempPath=new Path();
    @Override
    protected void onDraw(Canvas canvas) {
        LogUtils.e("onDraw");
        //平移到原点
        canvas.translate(mPadding,mHeight-mPadding-mRect.height());
        if(isDrawXY) {
            //X轴
            canvas.drawLine(0, 0, mWidth - mPadding, 0, mPaintCoord);
            //Y轴
            canvas.drawLine(0, 0, 0, -(mHeight - mPadding), mPaintCoord);
        }
        //x轴描述值
        for(int i=0;i<mData.size();i++){
            canvas.drawText(mData.get(i).getDesc(),mXItemWidth*i+mOffset,mRect.height(),mXPaint);
        }
        //画曲线
        mPath.offset(mOffset,0,mTempPath);
        canvas.drawPath(mTempPath, mPathPaint);
        if(isFill) {
            //画曲线
            mFillPath.offset(mOffset,0,mTempPath);
            canvas.drawPath(mTempPath, mPathFillPaint);
        }



        //x轴点的值
        for (int i = 0; i < mDataPointFs.size(); i++) {
            canvas.drawCircle(mDataPointFs.get(i).x+mOffset, mDataPointFs.get(i).y, mPointRadius, mPointPaint);
            mSelectedPaint.setColor(Color.WHITE);
            canvas.drawCircle(mDataPointFs.get(i).x+mOffset, mDataPointFs.get(i).y, mPointRadius-mPointPaint.getStrokeWidth()/2, mSelectedPaint);

            if(mSelectedPoint==i){
                mSelectedPaint.setColor(mLineColor);
                canvas.drawCircle(mDataPointFs.get(i).x+mOffset, mDataPointFs.get(i).y, mPointRadius, mSelectedPaint);
                mSelectedPaint.setColor(Color.parseColor("#80f56e4f"));
                canvas.drawCircle(mDataPointFs.get(i).x+mOffset, mDataPointFs.get(i).y, mPointRadius*2, mSelectedPaint);
            }
        }
    }

    /**
     * dp转成px
     * @param dipValue
     * @return
     */
    private   int dip2px( float dipValue) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 获取屏幕的宽度（单位：px）
     *
     * @return 屏幕宽px
     */
    private   int getScreenWidth() {
        WindowManager windowManager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(dm);// 给白纸设置宽高
        return dm.widthPixels;
    }
    float mOffset;
    float downX=0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mDataPointFs==null||mDataPointFs.size()<=0){//防止为空
            return false;
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX=event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX=event.getX();
                if(Math.abs(moveX-downX)<5){//滑动超过一定距离
                    break;
                }
                mOffset+=moveX-downX;
                //滑动的边界控制
                //左滑动
                if(moveX<downX&&(mDataPointFs.get(mDataPointFs.size()-1).x+mOffset)<=mWidth-mPadding*2){
                    if(mData.size()<=mScreenCount){
                        mOffset=0;
                    }else{
                        mOffset=-(mData.size()-mScreenCount-1)*mXItemWidth;
                    }
                }
                //右滑动
                if(moveX>downX&&(mDataPointFs.get(0).x+mOffset)>=0){
                    mOffset=0;
                }
                downX=moveX;
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                //滑动到左右的监听
                if(mOnLineViewListener!=null){
                    if(mOffset==0){//滑动到左边
                        mOnLineViewListener.onScrollMaxLeft();
                    }else if(mOffset==-(mData.size()-mScreenCount-1)*mXItemWidth){//滑动到右边
                        mOnLineViewListener.onScrollMaxRight();
                    }
                }
                checkClickPoint(event.getX(),event.getY());
                postInvalidate();
                break;
        }
        return super.onTouchEvent(event);
    }
    private int mSelectedPoint=-1;
    private void checkClickPoint(float upX, float upY) {
        upX=upX-mPadding;
        upY=upY-(mHeight-mPadding-mRect.height());
        parseData();

        for(int i=0;i<mDataPointFs.size();i++){
            //mPointRadius*2是为了扩大点击事件的范围
            if(Math.abs(upX-(mDataPointFs.get(i).x+mOffset))<mPointRadius*2&&Math.abs(upY-mDataPointFs.get(i).y)<mPointRadius*2){
                mSelectedPoint=i;
                if(mOnLineViewListener!=null){
                    mOnLineViewListener.onPointClickListener(i);
                }
                return ;
            }
        }
    }

    /**
     * 事件监听
     */
    OnLineViewListener mOnLineViewListener;

    public interface OnLineViewListener{
        /**
         * 点击监听
         * @param index
         */
        public void onPointClickListener(int index);

        /**
         * 滑动到最右边
         */
        public void onScrollMaxRight();
        /**
         * 滑动到最左边
         */
        public void onScrollMaxLeft();
    }

    //------------------------公开的一些方法-------------------------------------

    public void setOnPointClickListener(OnLineViewListener onPointClickListener) {
        mOnLineViewListener = onPointClickListener;
    }
    /**
     * 设置数据源
     * @param data
     */
    public void setData(ArrayList<WeightData> data) {
        mData.clear();
        mData.addAll(data);
        mOffset=0;
        parseData();
        postInvalidate();
    }

    /**
     * 删除数据源
     * @param position
     */
    public void removeData(int position) {
        if(mData==null||position<0||position>=mData.size()){
            return;
        }
        if(position==mSelectedPoint){//删除的如果是当前选中的 选中的index重置
            mSelectedPoint=-1;
        }else if(position>mSelectedPoint){//删除的在选中的后面 选中位置不变

        }else{//删除的在选中的前面 选中位置不变
            mSelectedPoint--;//删除一个选中个数索引减一
        }
        mData.remove(position);
        parseData();
        postInvalidate();
    }


    /**
     * 添加数据源
     * @param data
     */
    public void addMoreData(ArrayList<WeightData> data) {
        parseData();
        postInvalidate();
    }


    /**
     * 设置一个屏幕显示的个数
     * @param screenCount
     */
    public void setScreenCount(int screenCount) {
        mScreenCount = screenCount;
    }

    /**
     * 设置XY轴的Padding
     * @param padding
     */
    public void setPadding(float padding) {
        mPadding = padding;
    }

    /**
     * 设置数据源的最大值
     * @param max
     */
    public void setMax(int max) {
        mMax = max;
    }

    /**
     * 是都填充
     * @param isFill
     */
    public void setFill(boolean isFill) {
        this.isFill = isFill;
    }

    /**
     * 是否绘制XY轴
     * @param drawXY
     */
    public void setDrawXY(boolean drawXY) {
        isDrawXY = drawXY;
    }

    /**
     * 字体大小
     * @param XTextSize
     */
    public void setXTextSize(float XTextSize) {
        mXTextSize = XTextSize;
    }

    /**
     * 字体颜色
     * @param XTextColor
     */
    public void setXTextColor(int XTextColor) {
        mXTextColor = XTextColor;
    }

    /**
     * 设置选中的点
     * @param selectedPoint
     */
    public void setSelectedPoint(int selectedPoint) {
        mSelectedPoint = selectedPoint;
    }

    public int getSelectedPoint() {
        return mSelectedPoint;
    }
}
