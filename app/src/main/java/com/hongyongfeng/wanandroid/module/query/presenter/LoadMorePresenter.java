package com.hongyongfeng.wanandroid.module.query.presenter;


import android.graphics.Bitmap;

import com.hongyongfeng.wanandroid.base.BaseFragmentPresenter;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.data.net.bean.BannerBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.HomeFragmentInterface;
import com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel;
import com.hongyongfeng.wanandroid.module.home.view.fragment.HomeFragment;
import com.hongyongfeng.wanandroid.module.query.interfaces.LoadMoreInterface;
import com.hongyongfeng.wanandroid.module.query.model.LoadMoreModel;
import com.hongyongfeng.wanandroid.module.query.view.fragment.ArticleFragment;

import java.util.List;

public class LoadMorePresenter extends BaseFragmentPresenter<LoadMoreModel, ArticleFragment, LoadMoreInterface.VP> {
    @Override
    public LoadMoreModel getModelInstance() {
        return new LoadMoreModel(this);
    }

    @Override
    public LoadMoreInterface.VP getContract() {
        return new LoadMoreInterface.VP() {


            @Override
            public void requestLoadMoreVP(int page) {
                try {
                    mModel.getContract().requestLoadMoreM(page);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void responseLoadMoreVP(List<ArticleBean> articleList) {
                mView.getContract().responseLoadMoreVP(articleList);
            }
        };
    }
}
