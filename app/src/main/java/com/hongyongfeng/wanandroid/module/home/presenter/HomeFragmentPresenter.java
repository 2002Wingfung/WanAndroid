package com.hongyongfeng.wanandroid.module.home.presenter;


import android.graphics.Bitmap;

import com.hongyongfeng.wanandroid.base.BaseFragmentPresenter;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.data.net.bean.BannerBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.HomeFragmentInterface;
import com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel;
import com.hongyongfeng.wanandroid.module.home.view.fragment.HomeFragment;

import java.util.List;

public class HomeFragmentPresenter extends BaseFragmentPresenter<HomeFragmentModel, HomeFragment, HomeFragmentInterface.VP> {
    @Override
    public HomeFragmentModel getModelInstance() {
        return new HomeFragmentModel(this);
    }

    @Override
    public HomeFragmentInterface.VP getContract() {
        return new HomeFragmentInterface.VP() {
            @Override
            public void requestImageVP() {
                //核验请求的信息，进行逻辑处理
                //调用model层
                try {
//                    mModel.requestLogin(name,pwd);
                    mModel.getContract().requestImageM();

                } catch (Exception e) {
                    e.printStackTrace();
                    //异常的处理
                    //保存到日志
                    //一系列的异常处理
                    //...
                }
            }

            @Override
            public void responseImageResult(List<BannerBean> beanList,List<Bitmap> bitmapList) {
                mView.getContract().responseImageResult(beanList,bitmapList);
            }

            @Override
            public void requestArticleVP()  {
                try {
                    mModel.getContract().requestArticleM();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void responseArticleResult(List<ArticleBean> articleList,List<ArticleBean> articleTopLists) {
                mView.getContract().responseArticleResult(articleList,articleTopLists);
            }
        };
    }
}
