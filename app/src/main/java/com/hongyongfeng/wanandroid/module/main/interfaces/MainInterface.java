package com.hongyongfeng.wanandroid.module.main.interfaces;

/**
 * @author Wingfung Hung
 */
public interface MainInterface {
    interface Model {
        /**
         * 读取cookies
         * @throws Exception 异常
         */
        void requestM()throws Exception;
    }
    interface Vp {
        /**
         * 读取cookies
         */
        void requestVp();

        /**
         * 返回cookies
         * @param name cookies字符串
         */
        void responseResult(String name);
    }
}
