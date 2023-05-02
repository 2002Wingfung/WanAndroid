package com.hongyongfeng.wanandroid.module.knowledge.interfaces;

public interface KnowledgeFragmentInterface {
    interface M{
        void requestLoginM(String name, String pwd)throws Exception;
    }
    interface VP{
        void requestLoginVP(String name, String pwd);
        void responseLoginResult(boolean loginStatusResult);
    }
}
