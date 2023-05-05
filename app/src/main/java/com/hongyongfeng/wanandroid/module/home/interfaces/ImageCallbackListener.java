package com.hongyongfeng.wanandroid.module.home.interfaces;

import android.graphics.Bitmap;

import java.util.List;

public interface ImageCallbackListener {
    void onError(Exception e);
    void onBitmapFinish(List<Bitmap> bitmapList);
}
