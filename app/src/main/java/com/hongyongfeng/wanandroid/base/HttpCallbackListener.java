package com.hongyongfeng.wanandroid.base;

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
