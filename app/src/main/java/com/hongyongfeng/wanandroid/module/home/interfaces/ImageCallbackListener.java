package com.hongyongfeng.wanandroid.module.home.interfaces;

import android.graphics.Bitmap;

import java.util.List;

/**
 * 图片回调接口
 * @author Wingfung Hung
 */
public interface ImageCallbackListener {
    /**
     * 回传bitmap集合错误
     * @param e 异常
     */
    void onError(Exception e);

    /**
     * 回传bitmap集合完成
     * @param bitmapList Bitmap集合
     */
    void onBitmapFinish(List<Bitmap> bitmapList);
}
