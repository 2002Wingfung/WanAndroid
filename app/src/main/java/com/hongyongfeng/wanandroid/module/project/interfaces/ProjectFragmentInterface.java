package com.hongyongfeng.wanandroid.module.project.interfaces;

public interface ProjectFragmentInterface {
    interface M{
        void requestLoginM(String name, String pwd)throws Exception;
    }
    interface VP{
        void requestLoginVP(String name, String pwd);
        void responseLoginResult(boolean loginStatusResult);
    }
}
