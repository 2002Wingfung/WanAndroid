package com.hongyongfeng.wanandroid.module.main.interfaces;

import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.CollectListener;

import java.util.List;

/**
 * @author Wingfung Hung
 */
public interface MoreInterface {
    interface Model {
        /**
         * 请求历史记录
         * @throws Exception 异常
         */
        void requestHistoryM()throws Exception;
        /**
         * 请求收藏列表
         * @param page 页码
         * @throws Exception 异常
         */
        void requestCollectM(int page)throws Exception;

        /**
         * 保存文章历史
         * @param article ArticleBean实体类
         * @throws Exception 异常
         */
        void saveArticleM(ArticleBean article)throws Exception;

        /**
         * 收藏
         * @param id 文章id
         * @param listener 监听
         * @throws Exception 异常
         */
        void collectM(int id, CollectListener listener)throws Exception;

        /**
         * 取消收藏
         * @param id 文章id
         * @param listener 监听
         * @throws Exception 异常
         */
        void unCollectM(int id, CollectListener listener)throws Exception;
    }
    interface Vp {
        /**
         * 保存文章历史
         * @param article ArticleBean实体类
         */
        void saveHistory(ArticleBean article);
        /**
         * 收藏
         * @param id 文章id
         * @param listener 监听
         */
        void collectVp(int id, CollectListener listener);
        /**
         * 取消收藏
         * @param id 文章id
         * @param listener 监听
         */
        void unCollectVp(int id, CollectListener listener);
        /**
         * 请求历史记录
         */
        void requestHistoryVp();

        /**
         * 返回历史记录
         * @param article ArticleBean实体类集合
         */
        void responseHistoryVp(List<ArticleBean> article);

        /**
         * 请求收藏列表
         * @param page 页码
         */
        void requestCollectVp(int page);

        /**
         * 返回收藏列表
         * @param article ArticleBean实体类集合
         */
        void responseCollectVp(List<ArticleBean> article);

        /**
         * 返回收藏结果
         * @param code 收藏结果代码
         */
        void collectResponse(int code);
        /**
         * 返回取消收藏结果
         * @param code 取消收藏结果代码
         */
        void unCollectResponse(int code);

    }
}
