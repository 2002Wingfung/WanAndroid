package com.hongyongfeng.wanandroid.module.knowledge.interfaces;

import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.CollectListener;

import java.util.List;

/**
 * @author Wingfung Hung
 */
public interface ArticleInterface {
    interface Model {
        /**
         * 请求文章接口
         * @param id 文章id
         * @param page 页码
         * @throws Exception 异常
         */
        void requestArticleM(int id, int page)throws Exception;

        /**
         * 保存文章接口
         * @param article ArticleBean实体类
         * @throws Exception 异常
         */
        void saveArticleM(ArticleBean article)throws Exception;

        /**
         * 收藏接口
         * @param id 文章id
         * @param listener 收藏监听
         * @throws Exception 异常
         */
        void collectM(int id, CollectListener listener)throws Exception;

        /**
         * 取消收藏接口
         * @param id 文章id
         * @param listener 取消收藏监听
         * @throws Exception 异常
         */
        void unCollectM(int id, CollectListener listener)throws Exception;
    }
    interface ViewPresenter {
        /**
         * 保存历史记录
         * @param article ArticleBean实体类
         */
        void saveHistory(ArticleBean article);

        /**
         * 请求收藏接口
         * @param id 文章id
         * @param listener 监听
         */
        void collectVp(int id, CollectListener listener);

        /**
         * 取消收藏接口
         * @param id 文章id
         * @param listener 取消收藏监听
         */
        void unCollectVp(int id, CollectListener listener);

        /**
         * 收藏结果回调
         * @param code 收藏结果代码
         */
        void collectResponse(int code);

        /**
         * 取消收藏结果回调
         * @param code 取消收藏代码
         */
        void unCollectResponse(int code);
        /**
         * 请求文章接口
         * @param id 文章id
         * @param page 页码
         */
        void requestArticleVp(int id, int page);

        /**
         * 回调文章接口
         * @param articleList ArticleBean实体类集合
         */
        void responseArticleVp(List<ArticleBean> articleList);
    }
}
