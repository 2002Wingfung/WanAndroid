package com.hongyongfeng.wanandroid.module.project.interfaces;

import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.data.net.bean.ProjectBean;

import java.util.List;
import java.util.Map;

public interface ArticleInterface {
    interface M{
        void requestTitleM(int id,int page)throws Exception;
    }
    interface VP{
        void requestTitleVP(int id,int page);
        void responseTitleResult(List<ProjectBean> articleList);
    }
}
