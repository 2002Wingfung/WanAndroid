package com.hongyongfeng.wanandroid.module.query.model;

import com.hongyongfeng.wanandroid.base.BaseModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.query.interfaces.Query;
import com.hongyongfeng.wanandroid.module.query.presenter.QueryPresenter;
import com.hongyongfeng.wanandroid.util.HttpUtil;

import java.util.List;

public class QueryModel extends BaseModel<QueryPresenter, Query.M> {
    public QueryModel(QueryPresenter mPresenter) {
        super(mPresenter);
    }



    @Override
    public Query.M getContract() {
        return new Query.M() {
            @Override
            public void requestQueryM(String key,int page) throws Exception {
                String query="https://www.wanandroid.com/article/query/"+page+"/json";


                HttpUtil.postQueryRequest(query,key, new HttpCallbackListener() {

                    @Override
                    public void onFinish(String response) {
                        List<ArticleBean> articleBeanList = HttpUtil.parseJSONWithJSONObject(response, ArticleBean.class);
                        if (articleBeanList.size()!=0){

                            mPresenter.getContract().responseQueryResult(articleBeanList);
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
                //mPresenter.getContract().responseQueryResult(true);

            }
        };
    }
}
