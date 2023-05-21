package com.hongyongfeng.wanandroid.module.project.interfaces;

import java.util.List;
import java.util.Map;

/**
 * @author Wingfung Hung
 */
public interface ProjectFragmentInterface {
    interface Model {
        /**
         * 请求Tab标题字段接口
         * @throws Exception 异常
         */
        void requestTitleM()throws Exception;
    }
    interface Vp {
        /**
         *请求Tab标题字段接口
         */
        void requestTitleVp();

        /**
         * 返回标题结果接口
         * @param titleMapList 结果集合
         */
        void responseTitleResult(List<Map<String,Object>> titleMapList);
    }
}
