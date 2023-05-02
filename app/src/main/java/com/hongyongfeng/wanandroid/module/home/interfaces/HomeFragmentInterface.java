package com.hongyongfeng.wanandroid.module.home.interfaces;

public interface HomeFragmentInterface {
    interface M{
        void requestLoginM(String name, String pwd)throws Exception;
    }
    interface VP{
        void requestLoginVP(String name, String pwd);
        void responseLoginResult(boolean loginStatusResult);
    }
}
