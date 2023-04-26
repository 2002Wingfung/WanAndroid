package com.hongyongfeng.wanandroid.module.login.interfaces;

public interface ILogin {
    interface M{
        void requestLoginM(String name, String pwd)throws Exception;
    }
    interface VP{
        void requestLoginVP(String name, String pwd);
        void responseLoginResult(boolean loginStatusResult);
    }
}
