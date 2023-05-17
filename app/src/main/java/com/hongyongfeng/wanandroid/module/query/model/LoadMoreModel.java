package com.hongyongfeng.wanandroid.module.query.model;

import static com.hongyongfeng.wanandroid.data.local.database.Insert.insert;
import static com.hongyongfeng.wanandroid.util.Constant.COLLECT_URL;
import static com.hongyongfeng.wanandroid.util.Constant.DOMAIN_URL;
import static com.hongyongfeng.wanandroid.util.Constant.JSON_URL;
import static com.hongyongfeng.wanandroid.util.Constant.CANCEL_COLLECT_URL;

import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.CollectListener;
import com.hongyongfeng.wanandroid.module.query.interfaces.LoadMoreInterface;
import com.hongyongfeng.wanandroid.module.query.presenter.LoadMorePresenter;
import com.hongyongfeng.wanandroid.util.GetCookies;
import com.hongyongfeng.wanandroid.util.HttpUtil;

import java.util.List;

public class LoadMoreModel extends BaseFragmentModel<LoadMorePresenter, LoadMoreInterface.M> {
    public LoadMoreModel(LoadMorePresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public LoadMoreInterface.M getContract() {
        return new LoadMoreInterface.M() {

            @Override
            public void requestLoadMoreM(String key,int page) throws Exception {
                String query="https://www.wanandroid.com/article/query/"+page+"/json";
                HttpUtil.postQueryRequest(query,key, new HttpCallbackListener() {

                    @Override
                    public void onFinish(String response) {
                        List<ArticleBean> articleBeanList = HttpUtil.parseJSONWithJSONObject(response, ArticleBean.class);
                        mPresenter.getContract().responseLoadMoreVP(articleBeanList);
                    }


                    @Override
                    public void onError(Exception e) {

                        mPresenter.getContract().error(e);
                    }
                });
            }

            @Override
            public void collectM(int id, CollectListener listener) throws Exception {
                String cookies=GetCookies.get();
                if (cookies == null||"".equals(cookies)) {
                    mPresenter.getContract().collectResponse(1);
                }else {
                    HttpUtil.postCollectRequest(DOMAIN_URL + COLLECT_URL + id + JSON_URL,cookies, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            mPresenter.getContract().collectResponse(0);
                            listener.onFinish();
                        }

                        @Override
                        public void onError(Exception e) {
                            mPresenter.getContract().collectResponse(2);
                        }
                    });
                }
            }

            @Override
            public void unCollectM(int id, CollectListener listener) throws Exception {
                String cookies=GetCookies.get();
                if (cookies == null||"".equals(cookies)) {
                    mPresenter.getContract().collectResponse(1);
                }else {
                    HttpUtil.postCollectRequest(DOMAIN_URL + CANCEL_COLLECT_URL + id + JSON_URL, cookies, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            mPresenter.getContract().unCollectResponse(0);
                            listener.onFinish();
                        }

                        @Override
                        public void onError(Exception e) {
                            mPresenter.getContract().unCollectResponse(2);
                        }
                    });
                }
            }


            @Override
            public void saveArticleM(ArticleBean article) throws Exception {
                insert(article);
            }
        };
    }

}
