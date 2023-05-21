package com.hongyongfeng.wanandroid.module.query.model;

import static com.hongyongfeng.wanandroid.data.local.database.Insert.insert;
import static com.hongyongfeng.wanandroid.util.Constant.COLLECT_URL;
import static com.hongyongfeng.wanandroid.util.Constant.DOMAIN_URL;
import static com.hongyongfeng.wanandroid.util.Constant.JSON_URL;
import static com.hongyongfeng.wanandroid.util.Constant.CANCEL_COLLECT_URL;
import static com.hongyongfeng.wanandroid.util.Constant.ONE;
import static com.hongyongfeng.wanandroid.util.Constant.TWO;
import static com.hongyongfeng.wanandroid.util.Constant.ZERO;

import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.CollectListener;
import com.hongyongfeng.wanandroid.module.query.interfaces.LoadMoreInterface;
import com.hongyongfeng.wanandroid.module.query.presenter.LoadMorePresenter;
import com.hongyongfeng.wanandroid.util.GetCookies;
import com.hongyongfeng.wanandroid.util.HttpUtil;

import java.util.List;

/**
 * @author Wingfung Hung
 */
public class LoadMoreModel extends BaseFragmentModel<LoadMorePresenter, LoadMoreInterface.Model> {
    public LoadMoreModel(LoadMorePresenter mPresenter) {
        super(mPresenter);
    }
    private final String cookies=GetCookies.get();
    @Override
    public LoadMoreInterface.Model getContract() {
        return new LoadMoreInterface.Model() {
            @Override
            public void requestLoadMoreM(String key,int page){
                String query="https://www.wanandroid.com/article/query/"+page+"/json";
                HttpUtil.postQueryRequest(query,key, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        List<ArticleBean> articleBeanList = HttpUtil.parseJsonWithObject(response, ArticleBean.class);
                        mPresenter.getContract().responseLoadMoreVp(articleBeanList);
                    }
                    @Override
                    public void onError(Exception e) {
                        mPresenter.getContract().error(e);
                    }
                },cookies);
            }

            @Override
            public void collectM(int id, CollectListener listener) {
                if (cookies == null||"".equals(cookies)) {
                    mPresenter.getContract().collectResponse(ONE);
                }else {
                    HttpUtil.postCollectRequest(DOMAIN_URL + COLLECT_URL + id + JSON_URL,cookies, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            mPresenter.getContract().collectResponse(ZERO);
                            listener.onFinish();
                        }

                        @Override
                        public void onError(Exception e) {
                            mPresenter.getContract().collectResponse(TWO);
                        }
                    });
                }
            }
            @Override
            public void unCollectM(int id, CollectListener listener){
                if (cookies == null||"".equals(cookies)) {
                    mPresenter.getContract().collectResponse(ONE);
                }else {
                    HttpUtil.postCollectRequest(DOMAIN_URL + CANCEL_COLLECT_URL + id + JSON_URL, cookies, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            mPresenter.getContract().unCollectResponse(ZERO);
                            listener.onFinish();
                        }
                        @Override
                        public void onError(Exception e) {
                            mPresenter.getContract().unCollectResponse(TWO);
                        }
                    });
                }
            }
            @Override
            public void saveArticleM(ArticleBean article){
                insert(article);
            }
        };
    }

}
