package com.hongyongfeng.wanandroid.util;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 86186
 */
public class ThreadPools {
    public static ExecutorService es = Executors.newFixedThreadPool(5);

}
