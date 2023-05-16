package com.hongyongfeng.wanandroid.module.main.interfaces;

import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;

import java.util.List;

public interface MoreInterface {
    interface M{
        void requestHistoryM()throws Exception;
        void requestCollectM()throws Exception;

    }
    interface VP{

        void requestHistoryVP();
        void responseHistoryVP(List<ArticleBean> article);
        void requestCollectVP();
        void responseCollectVP(List<ArticleBean> article);

    }
}
