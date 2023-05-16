package com.hongyongfeng.wanandroid.module.query.interfaces;

import android.graphics.Bitmap;

import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.data.net.bean.BannerBean;

import java.util.List;

public interface LoadMoreInterface {
    interface M{
        void requestLoadMoreM(String key,int page)throws Exception;
        void collectM(int id)throws Exception;
        void unCollectM(int id)throws Exception;
        void saveArticleM(ArticleBean article)throws Exception;
    }
    interface VP{
        void requestLoadMoreVP(String key,int page);
        void responseLoadMoreVP(List<ArticleBean> articleList);
        void error(Exception e);
        void collectVP(int id);
        void unCollectVP(int id);
        void collectResponse(int code);
        void unCollectResponse(int code);
        void saveHistory(ArticleBean article);

    }
}
