package com.hanjinliang.androidstudy.javabase.fanxing;

import java.io.Serializable;

/**
 * Created by HanJinLiang on 2017-07-12.
 */

public class ComparaleUtil  {
    public static <T extends Comparable&Serializable> T getMinest(T... array){
        T small=array[0];
        for(T t:array){
            if(t.comparable(small)){
                small=t;
            }
        }
        return small;
    }
}
