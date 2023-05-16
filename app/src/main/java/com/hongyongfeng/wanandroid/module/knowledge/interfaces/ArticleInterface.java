package com.hongyongfeng.wanandroid.module.knowledge.interfaces;

import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;

import java.util.List;

public interface ArticleInterface {
    interface M{
        void requestArticleM(int id, int page)throws Exception;
        void saveArticleM(ArticleBean article)throws Exception;
        void collectM(int id)throws Exception;
        void unCollectM(int id)throws Exception;
    }
    interface VP{
        void saveHistory(ArticleBean article);
        void collectVP(int id);
        void unCollectVP(int id);
        void collectResponse(boolean bool);
        void unCollectResponse(boolean bool);
        void requestArticleVP(int id,int page);
        void responseArticleVP(List<ArticleBean> articleList);
    }
}
