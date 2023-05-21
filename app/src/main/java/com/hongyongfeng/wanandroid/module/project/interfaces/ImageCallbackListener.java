package com.hongyongfeng.wanandroid.module.project.interfaces;

import android.graphics.Bitmap;

/**
 * @author Wingfung Hung
 */
public interface ImageCallbackListener {
    /**
     * 回调图片异常
     * @param e 异常
     */
    void onError(Exception e);

    /**
     * 加载完图片回调接口
     * @param bitmap Bitmap图片
     */
    void onBitmapFinish(Bitmap bitmap);
}
