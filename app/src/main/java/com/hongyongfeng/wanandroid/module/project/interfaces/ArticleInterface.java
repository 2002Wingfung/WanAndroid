package com.hongyongfeng.wanandroid.module.project.interfaces;

import android.graphics.Bitmap;

import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.data.net.bean.ProjectBean;

import java.util.List;
import java.util.Map;

public interface ArticleInterface {
    interface M{
        void requestTitleM(int id,int page)throws Exception;
        void saveProjectM(ProjectBean project)throws Exception;

    }
    interface VP{
        void requestTitleVP(int id,int page);
        void responseTitleResult(List<ProjectBean> articleList);
        //void responseImageResult(List<Bitmap> bitmapList);
        void responseImageResult(Bitmap bitmap);
        void saveProject(ProjectBean project);

    }
}
