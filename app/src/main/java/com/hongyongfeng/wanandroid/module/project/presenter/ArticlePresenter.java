package com.hongyongfeng.wanandroid.module.project.presenter;


import android.graphics.Bitmap;

import com.hongyongfeng.wanandroid.base.BaseFragmentPresenter;
import com.hongyongfeng.wanandroid.data.net.bean.ProjectBean;
import com.hongyongfeng.wanandroid.module.project.interfaces.ArticleInterface;
import com.hongyongfeng.wanandroid.module.project.interfaces.ProjectFragmentInterface;
import com.hongyongfeng.wanandroid.module.project.model.ArticleModel;
import com.hongyongfeng.wanandroid.module.project.model.ProjectFragmentModel;
import com.hongyongfeng.wanandroid.module.project.view.fragment.ProjectArticleFragment;
import com.hongyongfeng.wanandroid.module.project.view.fragment.ProjectFragment;

import java.util.List;
import java.util.Map;

public class ArticlePresenter extends BaseFragmentPresenter<ArticleModel, ProjectArticleFragment, ArticleInterface.VP> {
    @Override
    public ArticleModel getModelInstance() {
        return new ArticleModel(this);
    }

    @Override
    public ArticleInterface.VP getContract() {
        return new ArticleInterface.VP() {
            @Override
            public void requestTitleVP(int id,int page) {
                //核验请求的信息，进行逻辑处理
                //调用model层
                try {
//                    mModel.requestLogin(name,pwd);
                    mModel.getContract().requestTitleM(id,page);

                } catch (Exception e) {
                    e.printStackTrace();
                    //异常的处理
                    //保存到日志
                    //一系列的异常处理
                    //...
                }
            }

            @Override
            public void responseTitleResult(List<ProjectBean> projectList) {
                //真实开发过程中，是要解析数据的
                mView.getContract().responseTitleResult(projectList);
            }

            @Override
            public void responseImageResult(Bitmap bitmap) {
                mView.getContract().responseImageResult(bitmap);
            }
        };
    }
}
