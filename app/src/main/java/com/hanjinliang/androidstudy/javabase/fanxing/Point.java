package com.hanjinliang.androidstudy.javabase.fanxing;

/**
 * Created by HanJinLiang on 2017-07-12.
 *
 * 泛型类学习
 */

public class Point<T> {
    private T x,y;
    public Point(){}

    public Point(T x,T y){
        this.x=x;
        this.y=y;
    }

    public T getX() {
        return x;
    }

    public void setX(T x) {
        this.x = x;
    }

    public T getY() {
        return y;
    }

    public void setY(T y) {
        this.y = y;
    }

}
