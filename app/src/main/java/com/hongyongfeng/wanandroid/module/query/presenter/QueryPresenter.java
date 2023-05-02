package com.hongyongfeng.wanandroid.module.query.presenter;

import com.hongyongfeng.wanandroid.base.BasePresenter;
import com.hongyongfeng.wanandroid.module.query.interfaces.Query;
import com.hongyongfeng.wanandroid.module.query.model.QueryModel;
import com.hongyongfeng.wanandroid.module.query.view.QueryActivity;

public class QueryPresenter extends BasePresenter<QueryModel, QueryActivity, Query.VP> {

    @Override
    public QueryModel getModelInstance() {
        return new QueryModel(this);
    }

    @Override
    public Query.VP getContract() {
        return null;
    }
}
