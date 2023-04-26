package com.hongyongfeng.wanandroid.module.query.model;

import com.hongyongfeng.wanandroid.base.BaseModel;
import com.hongyongfeng.wanandroid.module.query.interfaces.Query;
import com.hongyongfeng.wanandroid.module.query.presenter.QueryPresenter;

public class QueryModel extends BaseModel<QueryPresenter, Query.M> {
    public QueryModel(QueryPresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public Query.M getContract() {
        return null;
    }
}
