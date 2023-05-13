package com.hongyongfeng.wanandroid.module.project.interfaces;

import android.graphics.Bitmap;

import java.util.List;

public interface ImageCallbackListener {
    void onError(Exception e);
    void onBitmapFinish(Bitmap bitmap);
}
