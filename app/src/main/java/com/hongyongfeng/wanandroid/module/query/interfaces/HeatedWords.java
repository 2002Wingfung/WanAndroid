package com.hongyongfeng.wanandroid.module.query.interfaces;

import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;

import java.util.List;
import java.util.Map;

public interface HeatedWords {
    interface M{
        void requestHeatedWordsM()throws Exception;
        void requestQueryM(String key,int page)throws Exception;

    }
    interface VP{
        void requestQueryVP(String key,int page);
        void responseQueryResult(List<ArticleBean> queryResult);

        void requestHeatedWordsVP();
        void responseHeatedWordsResult(List<Map<String,Object>> heatedWordsList);
    }
}
