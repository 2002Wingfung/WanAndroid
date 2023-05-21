package com.hongyongfeng.wanandroid.module.query.presenter;

import com.hongyongfeng.wanandroid.base.BasePresenter;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.query.interfaces.Query;
import com.hongyongfeng.wanandroid.module.query.model.QueryModel;
import com.hongyongfeng.wanandroid.module.query.view.QueryActivity;
import java.util.List;

/**
 * @author Wingfung Hung
 */
public class QueryPresenter extends BasePresenter<QueryModel, QueryActivity, Query.Vp> {

    @Override
    public QueryModel getModelInstance() {
        return new QueryModel(this);
    }

    @Override
    public Query.Vp getContract() {
        return new Query.Vp() {
            @Override
            public void requestQueryVp(String key, int page) {
                try {
                    mModel.getContract().requestQueryM(key,page);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void responseQueryResult(List<ArticleBean> queryResult) {
                mView.getContract().responseQueryResult(queryResult);
            }
        };
    }
}
