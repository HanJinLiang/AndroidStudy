package com.hanjinliang.androidstudy.data;



import java.util.ArrayList;

/**
 * 通用分类
 * @param <T>
 */
public class ListResult<T>  {
    ArrayList<T> list;

    public ArrayList<T> getList() {
        return list;
    }

    public void setList(ArrayList<T> list) {
        this.list = list;
    }
}
