package com.hongyongfeng.wanandroid.module.main.interfaces;

public interface MainInterface {
    interface M{
        void requestM()throws Exception;
    }
    interface VP{
        void requestVP();
        void responseResult(String name);
    }
}
