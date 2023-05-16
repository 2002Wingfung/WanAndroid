package com.hongyongfeng.wanandroid.module.home.interfaces;

import android.graphics.Bitmap;
import android.widget.TextView;

import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.data.net.bean.BannerBean;

import java.util.List;

public interface HomeFragmentInterface {
    interface M{
        void requestImageM()throws Exception;
        void requestArticleM()throws Exception;
        void requestLoadMoreM(int page)throws Exception;
        void saveArticleM(ArticleBean article)throws Exception;
        void collectM(int id, CollectListener listener)throws Exception;
        void unCollectM(int id, CollectListener listener)throws Exception;
    }
    interface VP{
        void collectVP(int id, CollectListener listener);
        void unCollectVP(int id, CollectListener listener);
        void collectResponse(int code);
        void unCollectResponse(int code);
        void requestImageVP();
        void responseImageResult(List<BannerBean> beanList,List<Bitmap> bitmapList);
        void requestArticleVP();
        void responseArticleResult(List<ArticleBean> articleList,List<ArticleBean> articleTopList);
        void error(int error);
        void requestLoadMoreVP(int page);
        void responseLoadMoreVP(List<ArticleBean> articleList);
        void saveHistory(ArticleBean article);
    }
}
