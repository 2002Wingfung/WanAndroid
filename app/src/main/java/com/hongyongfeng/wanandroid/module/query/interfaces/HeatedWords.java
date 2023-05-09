package com.hongyongfeng.wanandroid.module.query.interfaces;

import java.util.List;
import java.util.Map;

public interface HeatedWords {
    interface M{
        void requestHeatedWordsM()throws Exception;
    }
    interface VP{
        void requestHeatedWordsVP();
        void responseHeatedWordsResult(List<Map<String,Object>> heatedWordsList);
    }
}
