package com.hongyongfeng.wanandroid.base;

import android.graphics.Bitmap;

import java.util.List;

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
