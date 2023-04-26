package com.hongyongfeng.wanandroid.module.query.view;

import android.view.View;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseActivity;
import com.hongyongfeng.wanandroid.module.query.interfaces.Query;
import com.hongyongfeng.wanandroid.module.query.presenter.QueryPresenter;

public class QueryActivity extends BaseActivity<QueryPresenter, Query.VP> {

    @Override
    public Query.VP getContract() {
        return null;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_query;
    }

    @Override
    public QueryPresenter getPresenterInstance() {
        return new QueryPresenter();
    }

    @Override
    public <ERROR> void responseError(ERROR error, Throwable throwable) {

    }

    @Override
    public void initView() {

    }

    @Override
    public void onClick(View v) {

    }
}
