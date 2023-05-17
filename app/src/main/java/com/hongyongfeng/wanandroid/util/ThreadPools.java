package com.hongyongfeng.wanandroid.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 86186
 */
public class ThreadPools {
    public ThreadPoolExecutor es =new ThreadPoolExecutor(5,10,30, TimeUnit.MINUTES,new ArrayBlockingQueue<>(10),
            Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
    //public static ExecutorService es = Executors.newFixedThreadPool(10);
}
