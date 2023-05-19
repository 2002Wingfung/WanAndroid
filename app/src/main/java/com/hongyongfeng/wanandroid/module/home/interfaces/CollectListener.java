package com.hongyongfeng.wanandroid.module.home.interfaces;

/**
 * 收藏监听
 * @author Wingfung Hung
 */
public interface CollectListener {
    /**
     * 当点赞请求成功后回调Finish方法
     */
    void onFinish();
    /**
     * 当点赞请求失败后回调onError方法
     */
    void onError();
}
