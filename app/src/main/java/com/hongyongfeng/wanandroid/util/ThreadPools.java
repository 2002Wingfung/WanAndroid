package com.hongyongfeng.wanandroid.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Wingfung Hung
 */
public class ThreadPools {
    /**
     * 新建了线程池
     */
    public static ThreadPoolExecutor es =new ThreadPoolExecutor(10,10,30, TimeUnit.MINUTES,new ArrayBlockingQueue<>(10),
            Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
    //public static ExecutorService es = Executors.newFixedThreadPool(10);
}
