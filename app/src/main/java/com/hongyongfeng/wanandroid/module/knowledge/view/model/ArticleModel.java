package com.hongyongfeng.wanandroid.module.knowledge.view.model;

import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.knowledge.interfaces.ArticleInterface;
import com.hongyongfeng.wanandroid.module.knowledge.view.presenter.ArticlePresenter;
import com.hongyongfeng.wanandroid.module.query.interfaces.LoadMoreInterface;
import com.hongyongfeng.wanandroid.module.query.presenter.LoadMorePresenter;
import com.hongyongfeng.wanandroid.util.HttpUtil;

import java.util.List;

public class ArticleModel extends BaseFragmentModel<ArticlePresenter, ArticleInterface.M> {
    public ArticleModel(ArticlePresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public ArticleInterface.M getContract() {
        return new ArticleInterface.M() {

            @Override
            public void requestLoadMoreM(int id, int page) throws Exception {
                String url="https://www.wanandroid.com/article/list/"+page+"/json?cid="+page;

                HttpUtil.sendHttpRequest(url, new HttpCallbackListener() {

                    @Override
                    public void onFinish(String response) {
                        List<ArticleBean> articleBeanList = HttpUtil.parseJSONWithJSONObject(response, ArticleBean.class);
                        mPresenter.getContract().responseArticleVP(articleBeanList);
                    }


                    @Override
                    public void onError(Exception e) {
                    }
                });
            }
        };
    }

}
