package com.hongyongfeng.wanandroid.module.signinorup.register.interfaces;

public interface RegisterInterface {
    interface M{
        void requestRegisterM(String name, String pwd)throws Exception;
    }
    interface VP{
        void requestRegisterVP(String name, String pwd);
        void responseRegisterResult(boolean loginStatusResult);
        void error(String error);

    }
}
