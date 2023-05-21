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
        void requestTitleVp(int id, int page);

        /**
         * 返回项目文章
         * @param articleList ProjectBean实体类集合
         */
        void responseTitleResult(List<ProjectBean> articleList);

        /**
         * 返回项目图片
         * @param bitmap 项目图片
         */
        void responseImageResult(Bitmap bitmap);
        /**
         * 保存项目文章到历史记录
         * @param project 项目实体类
         */
        void saveProject(ProjectBean project);
        /**
         * 收藏接口
         * @param id 项目id
         * @param listener 监听
         */
        void collectVp(int id, CollectListener listener);
        /**
         * 取消收藏接口
         * @param id 文章id
         * @param listener 取消收藏监听
         */
        void unCollectVp(int id, CollectListener listener);

        /**
         * 返回收藏结果接口
         * @param code 收藏代码
         */
        void collectResponse(int code);

        /**
         * 返回取消收藏结果接口
         * @param code 收藏代码
         */
        void unCollectResponse(int code);
    }
}
