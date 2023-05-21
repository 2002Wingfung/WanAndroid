package com.hongyongfeng.wanandroid.module.knowledge.interfaces;

import java.util.List;
import java.util.Map;

/**
 * @author Wingfung Hung
 */
public interface KnowledgeFragmentInterface {
    interface Model {
        /**
         * 请求知识体系数的接口
         * @throws Exception 异常
         */
        void requestTitleM()throws Exception;
    }
    interface Vp {
        /**
         * 请求知识体系树的接口
         */
        void requestTitleVp();

        /**
         * 回调知识体系树的接口
         * @param treeList 带有树结构的List<Map>集合
         */
        void responseTitleResult(List<Map<String,Object>> treeList);
    }
}
