package com.hongyongfeng.wanandroid.module.query.presenter;

import com.hongyongfeng.wanandroid.base.BasePresenter;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.query.interfaces.Query;
import com.hongyongfeng.wanandroid.module.query.model.QueryModel;
import com.hongyongfeng.wanandroid.module.query.view.QueryActivity;

import java.util.List;

public class QueryPresenter extends BasePresenter<QueryModel, QueryActivity, Query.VP> {

    @Override
    public QueryModel getModelInstance() {
        return new QueryModel(this);
    }

    @Override
    public Query.VP getContract() {
        return new Query.VP() {
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
        };
    }
}
