package com.hongyongfeng.wanandroid.module.project.interfaces;

import android.graphics.Bitmap;

import com.hongyongfeng.wanandroid.data.net.bean.ProjectBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.CollectListener;

import java.util.List;

/**
 * @author Wingfung Hung
 */
public interface ArticleInterface {
    interface Model {
        /**
         * 请求项目文章
         * @param id 项目id
         * @param page 页码
         * @throws Exception 异常
         */
        void requestTitleM(int id,int page)throws Exception;

        /**
         * 保存项目文章到历史记录
         * @param project 项目实体类
         * @throws Exception 异常
         */
        void saveProjectM(ProjectBean project)throws Exception;

        /**
         * 收藏接口
         * @param id 项目id
         * @param listener 监听
         * @throws Exception 异常
         */
        void collectM(int id, CollectListener listener)throws Exception;

        /**
         * 取消收藏接口
         * @param id 文章id
         * @param listener 取消收藏监听
         * @throws Exception 异常
         */
        void unCollectM(int id, CollectListener listener)throws Exception;
    }
    interface Vp {
        /**
         * 请求项目文章
         * @param id 项目id
         * @param page 页码
         * */
        void requestTitleVP(int id,int page);
        void responseTitleResult(List<ProjectBean> articleList);
        void responseImageResult(Bitmap bitmap);
        void saveProject(ProjectBean project);
        void collectVP(int id, CollectListener listener);
        void unCollectVP(int id, CollectListener listener);
        void collectResponse(int code);
        void unCollectResponse(int code);
    }
}
