package com.hongyongfeng.wanandroid.module.main.presenter;

import com.hongyongfeng.wanandroid.base.BasePresenter;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
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
            public void requestQueryVP(String key,int page) {
                try {
                    mModel.getContract().requestQueryM(key,page);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void responseQueryResult(List<ArticleBean> queryResult) {
                mView.getContract().responseQueryResult(queryResult);
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

        };
    }
}
