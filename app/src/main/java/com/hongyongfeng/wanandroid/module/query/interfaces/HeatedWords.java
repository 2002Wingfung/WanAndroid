package com.hongyongfeng.wanandroid.module.query.interfaces;

public interface HeatedWords {
    interface M{
        void requestQueryM(String name)throws Exception;
    }
    interface VP{
        void requestQueryVP(String name);
        void responseQueryResult(boolean loginStatusResult);
    }
}
