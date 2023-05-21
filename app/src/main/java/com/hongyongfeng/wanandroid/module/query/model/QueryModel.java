package com.hongyongfeng.wanandroid.module.query.model;

import static com.hongyongfeng.wanandroid.util.Constant.DOMAIN_URL;
import static com.hongyongfeng.wanandroid.util.Constant.JSON_URL;
import static com.hongyongfeng.wanandroid.util.Constant.QUERY_URL;
import static com.hongyongfeng.wanandroid.util.Constant.ZERO;
import com.hongyongfeng.wanandroid.base.BaseModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.query.interfaces.Query;
import com.hongyongfeng.wanandroid.module.query.presenter.QueryPresenter;
import com.hongyongfeng.wanandroid.util.GetCookies;
import com.hongyongfeng.wanandroid.util.HttpUtil;
import java.util.List;

/**
 * @author Wingfung Hung
 */
public class QueryModel extends BaseModel<QueryPresenter, Query.Model> {
    public QueryModel(QueryPresenter mPresenter) {
        super(mPresenter);
    }
    @Override
    public Query.Model getContract() {
        return (key, page) -> {
            String query=DOMAIN_URL+QUERY_URL+page+JSON_URL;
            String cookies= GetCookies.get();
            HttpUtil.postQueryRequest(query,key, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    List<ArticleBean> articleBeanList = HttpUtil.parseJsonWithObject(response, ArticleBean.class);
                    if (articleBeanList.size()!=ZERO){
                        mPresenter.getContract().responseQueryResult(articleBeanList);
                    }
                }
                @Override
                public void onError(Exception e) {
                }
            },cookies);
        };
    }
}
