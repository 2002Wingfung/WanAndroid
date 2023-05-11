package com.hongyongfeng.wanandroid.module.knowledge.interfaces;

import java.util.List;
import java.util.Map;

public interface KnowledgeFragmentInterface {
    interface M{
        void requestLoginM()throws Exception;
    }
    interface VP{
        void requestTitleVP();
        void responseTitleResult(List<Map<String,Object>> treeList);
    }
}
