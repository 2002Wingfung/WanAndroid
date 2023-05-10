package com.hongyongfeng.wanandroid.module.query.interfaces;

import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;

import java.util.List;

public interface Query {
    interface M{
        void requestQueryM(String key,int page)throws Exception;
    }
    interface VP{
        void requestQueryVP(String key,int page);
        void responseQueryResult(List<ArticleBean> queryResult);
    }
}
