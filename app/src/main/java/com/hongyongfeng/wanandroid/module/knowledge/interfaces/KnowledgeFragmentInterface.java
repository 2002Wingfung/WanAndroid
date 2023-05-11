package com.hongyongfeng.wanandroid.module.knowledge.interfaces;

public interface KnowledgeFragmentInterface {
    interface M{
        void requestLoginM()throws Exception;
    }
    interface VP{
        void requestTitleVP();
        void responseTitleResult(boolean loginStatusResult);
    }
}
