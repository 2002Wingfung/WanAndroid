package com.hongyongfeng.wanandroid.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * 全局获取Context实例
 * @author Wingfung Hung
 */
public class MyApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    /**
     * 全局获取Context实例
     * @return 返回Context实例
     */
    public static Context getContext(){
        return context;
    }
}
