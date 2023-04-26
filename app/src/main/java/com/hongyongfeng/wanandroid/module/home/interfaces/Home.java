package com.hongyongfeng.wanandroid.module.home.interfaces;

public interface Home {
    interface M{
        void requestM(String name, String pwd)throws Exception;
    }
    interface VP{
        void requestVP(String name, String pwd);
        void responseResult(boolean loginStatusResult);
    }
}
