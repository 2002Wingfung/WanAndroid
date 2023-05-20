package com.hongyongfeng.wanandroid.base;

/**
 * http回调接口
 * @author Wingfung Hung
 */
public interface HttpCallbackListener {
    /**
     * 完成时回调
     * @param response Json数组
     */
    void onFinish(String response);

    /**
     * 失败时回调
     * @param e 异常
     */
    void onError(Exception e);
}
