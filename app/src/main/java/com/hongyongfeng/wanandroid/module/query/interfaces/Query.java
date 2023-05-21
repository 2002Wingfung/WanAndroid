package com.hongyongfeng.wanandroid.module.query.interfaces;

import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import java.util.List;

/**
 *
 * 查询接口
 * @author Wingfung Hung
 */
public interface Query {
    interface Model {
        /**
         * 请求查询接口M层
         * @param key 关键字
         * @param page 页码
         * @throws Exception 异常
         */
        void requestQueryM(String key,int page)throws Exception;
    }
    interface Vp {
        /**
         * 请求查询接口ViewPresenter层
         * @param key 关键字
         * @param page 页码
         */
        void requestQueryVp(String key, int page);

        /**
         * 返回查询结果接口
         * @param queryResult ArticleBean实体类集合
         */
        void responseQueryResult(List<ArticleBean> queryResult);
    }
}
