package com.hongyongfeng.wanandroid.module.home.interfaces;

import android.graphics.Bitmap;

import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.data.net.bean.BannerBean;

import java.util.List;

/**
 * 主页碎片的接口
 * @author Wingfung Hung
 */
public interface HomeFragmentInterface {
    /**
     * Model层的接口
     */
    interface Model {
        /**
         *请求图片接口
         * @throws Exception 异常
         */
        void requestImageM()throws Exception;
        /**
         *请求文章接口
         *@throws Exception 异常
         */
        void requestArticleM()throws Exception;

        /**
         *请求加载更多接口
         * @param page 页码
         * @throws Exception 异常
         */
        void requestLoadMoreM(int page)throws Exception;

        /**
         * 保存首页文章接口
         * @param article ArticleBean类
         * @throws Exception 异常
         */
        void saveArticleM(ArticleBean article)throws Exception;

        /**
         * 请求收藏接口
         * @param id 文章id
         * @param listener 收藏监听
         * @throws Exception 异常
         */
        void collectM(int id, CollectListener listener)throws Exception;

        /**
         * 取消收藏监听
         * @param id 文章id
         * @param listener 取消收藏监听
         * @throws Exception 异常
         */
        void unCollectM(int id, CollectListener listener)throws Exception;
    }
    interface ViewPresenter {
        /**
         * 收藏接口
         * @param id 页码
         * @param listener 监听
         */
        void collectVp(int id, CollectListener listener);

        /**
         * 取消收藏接口
         * @param id 页码
         * @param listener 监听
         */
        void unCollectVp(int id, CollectListener listener);

        /**
         * 返回收藏是否成功的数据
         * @param code 返回的代码
         */
        void collectResponse(int code);
        /**
         * 返回取消收藏是否成功的数据
         * @param code 返回的代码
         */
        void unCollectResponse(int code);

        /**
         * 请求图片接口
         */
        void requestImageVp();

        /**
         * 返回图片结果接口
         * @param beanList BannerBean实体类的List集合
         * @param bitmapList BitMap的List集合
         */
        void responseImageResult(List<BannerBean> beanList,List<Bitmap> bitmapList);

        /**
         * 请求首页文章的接口
         */
        void requestArticleVp();

        /**
         * 返回文章结果接口
         * @param articleList ArticleBean实体类集合
         * @param articleTopList 置顶文章的集合
         */
        void responseArticleResult(List<ArticleBean> articleList,List<ArticleBean> articleTopList);

        /**
         * 错误接口
         * @param error 错误代码
         */
        void error(int error);

        /**
         * 请求加载更多接口
         * @param page 加载更多的页码
         */
        void requestLoadMoreVp(int page);

        /**
         * 返回加载更多的数据
         * @param articleList ArticleBean实体类集合
         */
        void responseLoadMoreVp(List<ArticleBean> articleList);

        /**
         * 保存历史记录接口
         * @param article ArticleBean实体类
         */
        void saveHistory(ArticleBean article);
    }
}
