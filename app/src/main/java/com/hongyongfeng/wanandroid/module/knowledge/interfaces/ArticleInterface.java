package com.hongyongfeng.wanandroid.module.knowledge.interfaces;

import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.CollectListener;

import java.util.List;

/**
 * @author Wingfung Hung
 */
public interface ArticleInterface {
    interface Model {
        void requestArticleM(int id, int page)throws Exception;
        void saveArticleM(ArticleBean article)throws Exception;
        void collectM(int id, CollectListener listener)throws Exception;
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
        void unCollectVP(int id, CollectListener listener);
        void collectResponse(int code);
        void unCollectResponse(int code);
        void requestArticleVP(int id,int page);
        void responseArticleVP(List<ArticleBean> articleList);
    }
}
