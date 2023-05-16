package com.hongyongfeng.wanandroid.module.knowledge.interfaces;

import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.CollectListener;

import java.util.List;

public interface ArticleInterface {
    interface M{
        void requestArticleM(int id, int page)throws Exception;
        void saveArticleM(ArticleBean article)throws Exception;
        void collectM(int id, CollectListener listener)throws Exception;
        void unCollectM(int id, CollectListener listener)throws Exception;
    }
    interface VP{
        void saveHistory(ArticleBean article);
        void collectVP(int id, CollectListener listener);
        void unCollectVP(int id, CollectListener listener);
        void collectResponse(int code);
        void unCollectResponse(int code);
        void requestArticleVP(int id,int page);
        void responseArticleVP(List<ArticleBean> articleList);
    }
}
