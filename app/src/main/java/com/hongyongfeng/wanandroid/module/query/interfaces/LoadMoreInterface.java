package com.hongyongfeng.wanandroid.module.query.interfaces;

import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.CollectListener;
import java.util.List;

/**
 * @author Wingfung Hung
 */
public interface LoadMoreInterface {
    interface Model {
        /**
         * 请求加载更多接口
         * @param key 关键字
         * @param page 页码
         * @throws Exception 异常
         */
        void requestLoadMoreM(String key,int page)throws Exception;

        /**
         * 请求收藏接口
         * @param id 文章id
         * @param listener 收藏成功监听
         * @throws Exception 异常
         */
        void collectM(int id, CollectListener listener)throws Exception;

        /**
         * 请取消求收藏接口
         * @param id 文章id
         * @param listener 收藏成功监听
         * @throws Exception 异常
         */
        void unCollectM(int id, CollectListener listener)throws Exception;

        /**
         * 保存文章历史记录到本地接口
         * @param article ArticleBean实体类
         * @throws Exception 异常
         */
        void saveArticleM(ArticleBean article)throws Exception;
    }
    interface Vp {
        /**
         * 请求加载更多接口
         * @param key 关键字
         * @param page 页码
         */
        void requestLoadMoreVp(String key, int page);

        /**
         * 加载更多成功回调接口
         * @param articleList ArticleBean实体类
         */
        void responseLoadMoreVp(List<ArticleBean> articleList);

        /**
         * 请求错误回调
         * @param e 异常
         */
        void error(Exception e);

        /**
         * 请求收藏接口
         * @param id 文章id
         * @param listener 收藏成功监听
         */
        void collectVp(int id, CollectListener listener);

        /**
         * 请求取消收藏接口
         * @param id 文章id
         * @param listener 取消收藏成功监听
         */
        void unCollectVp(int id, CollectListener listener);

        /**
         * 收藏结果回调
         * @param code 收藏结果代码
         */
        void collectResponse(int code);
        /**
         * 取消收藏结果回调
         * @param code 取消收藏结果代码
         */
        void unCollectResponse(int code);

        /**
         * 保存历史记录接口
         * @param article ArticleBean实体类
         */
        void saveHistory(ArticleBean article);

    }
}
