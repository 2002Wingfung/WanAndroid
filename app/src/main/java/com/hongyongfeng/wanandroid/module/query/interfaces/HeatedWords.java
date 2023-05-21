package com.hongyongfeng.wanandroid.module.query.interfaces;

import java.util.List;
import java.util.Map;

/**
 * @author Wingfung Hung
 */
public interface HeatedWords {
    interface Model {
        /**
         * 请求搜索热词接口
         * @throws Exception 异常
         */
        void requestHeatedWordsM()throws Exception;
    }
    interface Vp {
        /**
         * 请求搜索热词接口
         */
        void requestHeatedWordsVp();

        /**
         * 回传搜索热词接口
         * @param heatedWordsList 带有搜索热词的List<Map>集合
         */
        void responseHeatedWordsResult(List<Map<String,Object>> heatedWordsList);
    }
}
