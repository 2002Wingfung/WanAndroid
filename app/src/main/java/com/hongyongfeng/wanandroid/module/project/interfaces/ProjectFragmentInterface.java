package com.hongyongfeng.wanandroid.module.project.interfaces;

import java.util.List;
import java.util.Map;

public interface ProjectFragmentInterface {
    interface M{
        void requestTitleM()throws Exception;
    }
    interface VP{
        void requestTitleVP();
        void responseTitleResult(List<Map<String,Object>> titleMapList);
    }
}
