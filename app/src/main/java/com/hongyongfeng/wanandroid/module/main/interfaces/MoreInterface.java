package com.hongyongfeng.wanandroid.module.main.interfaces;

import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.CollectListener;

import java.util.List;

public interface MoreInterface {
    interface M{
        void requestHistoryM()throws Exception;
        void requestCollectM(int page)throws Exception;
        void saveArticleM(ArticleBean article)throws Exception;
        void collectM(int id, CollectListener listener)throws Exception;
        void unCollectM(int id, CollectListener listener)throws Exception;
    }
    interface VP{
        void saveHistory(ArticleBean article);
        void collectVP(int id, CollectListener listener);
        void unCollectVP(int id, CollectListener listener);
        void requestHistoryVP();
        void responseHistoryVP(List<ArticleBean> article);
        void requestCollectVP(int page);
        void responseCollectVP(List<ArticleBean> article);
        void collectResponse(int code);
        void unCollectResponse(int code);

    }
}
