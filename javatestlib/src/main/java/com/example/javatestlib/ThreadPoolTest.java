package com.example.javatestlib;

import com.sun.jmx.snmp.tasks.ThreadService;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Description: java类作用描述
 * @Author: Monkey
 * @CreateDate: 2021/4/13 14:49
 */
public class ThreadPoolTest {
    public static void main(String[] args){
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 10, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

        for(int i=0;i<10;i++){
            poolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("name="+Thread.currentThread().getName());
                }
            });
        }

    }
}
