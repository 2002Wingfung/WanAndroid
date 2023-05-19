package com.hongyongfeng.wanandroid.module.main.presenter;

import com.hongyongfeng.wanandroid.base.BasePresenter;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.CollectListener;
import com.hongyongfeng.wanandroid.module.main.activity.MoreActivity;
import com.hongyongfeng.wanandroid.module.main.interfaces.MoreInterface;
import com.hongyongfeng.wanandroid.module.main.model.MoreModel;
import com.hongyongfeng.wanandroid.module.query.interfaces.Query;
import com.hongyongfeng.wanandroid.module.query.model.QueryModel;
import com.hongyongfeng.wanandroid.module.query.view.QueryActivity;

import java.util.List;

public class MorePresenter extends BasePresenter<MoreModel, MoreActivity, MoreInterface.VP> {

    @Override
    public MoreModel getModelInstance() {
        return new MoreModel(this);
    }

    @Override
    public MoreInterface.VP getContract() {
        return new MoreInterface.VP() {

            @Override
            public void saveHistory(ArticleBean article) {
                try {
                    mModel.getContract().saveArticleM(article);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void collectVP(int id, CollectListener listener) {
                try {
                    mModel.getContract().collectM(id,listener);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void unCollectVP(int id, CollectListener listener) {
                try {
                    mModel.getContract().unCollectM(id,listener);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestHistoryVP() {
                try {
                    mModel.getContract().requestHistoryM();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void responseHistoryVP(List<ArticleBean> article) {
                mView.getContract().responseHistoryVP(article);
            }

            @Override
            public void requestCollectVP(int page) {
                try {
                    mModel.getContract().requestCollectM(page);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void responseCollectVP(List<ArticleBean> article) {
                mView.getContract().responseCollectVP(article);
            }

            @Override
            public void collectResponse(int code) {
                mView.getContract().collectResponse(code);
            }

            @Override
            public void unCollectResponse(int code) {
                mView.getContract().unCollectResponse(code);
            }

        };
    }
}
