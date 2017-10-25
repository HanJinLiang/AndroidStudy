package com.hanjinliang.androidstudy.systemwidget.Sensor;

import android.app.Service;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * Created by HanJinLiang on 2017-09-26.
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder sfh;
    private Paint paint;
    private Thread th;

    private boolean flag;

    private Canvas canvas;

    private int screenW, screenH;
//声明一个传感器管理器

    private SensorManager sm;
    //声明一个传感器

    private Sensor sensor;
    //声明一个传感器监听器

    private SensorEventListener mySensorListener;
    //圆形的X,Y坐标

    private int arc_x, arc_y;
    //传感器的xyz值
    private float x = 0, y = 0, z = 0;

    /**
     *  * SurfaceView初始化函数
     *  
     */
    public MySurfaceView(Context context) {
        super(context);
        sfh = this.getHolder();
        sfh.addCallback(this);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        setFocusable(true);
        //获取传感器管理类实例
        sm = (SensorManager) context.getSystemService(Service.SENSOR_SERVICE);
        //实例一个重力传感器实例
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //实例传感器监听器
        mySensorListener = new SensorEventListener() {
            @Override
            //传感器获取值发生改变时在响应此函数
            public void onSensorChanged(SensorEvent event) {
                x = event.values[0];
                //x>0 说明当前手机左翻 x<0右翻
                y = event.values[1];
                //y>0 说明当前手机下翻 y<0上翻
                z = event.values[2];
                //z>0 手机屏幕朝上 z<0 手机屏幕朝下
                arc_x -= x;
                arc_y += y;
            }

            @Override
            //传感器的精度发生改变时响应此函数
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }

        };
        //为传感器注册监听器
        sm.registerListener(mySensorListener, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     *  * SurfaceView视图创建，响应此函数
     *  
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        screenW = this.getWidth();
        screenH = this.getHeight();
        flag = true;
        //实例线程
        th = new Thread(this);
        //启动线程
        th.start();
    }

    /**
     *  * 游戏绘图
     *  
     */

    public void myDraw() {
        try {
            canvas = sfh.lockCanvas();
            if (canvas != null) {
                canvas.drawColor(Color.BLACK);
                paint.setColor(Color.RED);
                canvas.drawArc(new RectF(arc_x, arc_y, arc_x + 50, arc_y + 50), 0, 360, true, paint);
                paint.setColor(Color.YELLOW);
                canvas.drawText("当前重力传感器的值:", arc_x - 50, arc_y - 30, paint);
                canvas.drawText("x=" + x + ",y=" + y + ",z=" + z, arc_x - 50, arc_y, paint);
                String temp_str = "tony提示： ";
                String temp_str2 = "";
                String temp_str3 = "";
                if (x < 1 && x > -1 && y < 1 && y > -1) {
                    temp_str += "当前手机处于水平放置的状态";
                    if (z > 0) {
                        temp_str2 += "并且屏幕朝上";
                    } else {
                        temp_str2 += "并且屏幕朝下,提示别躺着玩手机，对眼睛不好哟~";
                    }
                } else {
                    if (x > 1) {
                        temp_str2 += "当前手机处于向左翻的状态";
                    } else if (x < -1) {
                        temp_str2 += "当前手机处于向右翻的状态";
                    }
                    if (y > 1) {
                        temp_str2 += "当前手机处于向下翻的状态";
                    } else if (y < -1) {
                        temp_str2 += "当前手机处于向上翻的状态";
                    }
                    if (z > 0) {
                        temp_str3 += "并且屏幕朝上";
                    } else {
                        temp_str3 += "并且屏幕朝下,提示别躺着玩手机，对眼睛不好哟~";
                    }
                }
                paint.setTextSize(10);
                canvas.drawText(temp_str, 0, 50, paint);
                canvas.drawText(temp_str2, 0, 80, paint);
                canvas.drawText(temp_str3, 0, 110, paint);
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }

    /**
     *  * 触屏事件监听
     *  
     */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    /**
     *  * 按键事件监听
     *  
     */

    @Override


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    /**
     *  * 游戏逻辑
     *  
     */
    private void logic() {
    }

    @Override
    public void run() {
        while (flag) {
            long start = System.currentTimeMillis();
            myDraw();
            logic();
            long end = System.currentTimeMillis();
            try {
                if (end - start < 50) {
                    Thread.sleep(50 - (end - start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  * SurfaceView视图状态发生改变，响应此函数
     *  
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    /**
     *  * SurfaceView视图消亡时，响应此函数
     *  
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
    }
}