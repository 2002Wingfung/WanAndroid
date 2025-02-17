package com.hongyongfeng.wanandroid.module.knowledge.model;

import static com.hongyongfeng.wanandroid.data.local.database.Insert.insert;
import static com.hongyongfeng.wanandroid.util.Constant.ARTICLE_URL_1;
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
import com.hongyongfeng.wanandroid.module.knowledge.interfaces.ArticleInterface;
import com.hongyongfeng.wanandroid.module.knowledge.presenter.ArticlePresenter;
import com.hongyongfeng.wanandroid.util.GetCookies;
import com.hongyongfeng.wanandroid.util.HttpUtil;
import java.util.List;

/**
 * @author Wingfung Hung
 */
public class ArticleModel extends BaseFragmentModel<ArticlePresenter, ArticleInterface.Model> {
    public ArticleModel(ArticlePresenter mPresenter) {
        super(mPresenter);
    }
    @Override
    public ArticleInterface.Model getContract() {
        return new ArticleInterface.Model() {

            @Override
            public void requestArticleM(int id, int page) {
                String url=DOMAIN_URL+ARTICLE_URL_1+page+JSON_URL+"?cid="+id;
                HttpUtil.sendHttpRequest(url, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        List<ArticleBean> articleBeanList = HttpUtil.parseJsonWithObject(response, ArticleBean.class);
                        mPresenter.getContract().responseArticleVp(articleBeanList);
                    }

                    @Override
                    public void onError(Exception e) {
                    }
                },GetCookies.get());
            }

            @Override
            public void saveArticleM(ArticleBean article) {
                insert(article);
            }

            @Override
            public void collectM(int id, CollectListener listener){
                String cookies=GetCookies.get();
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
            public void unCollectM(int id, CollectListener listener) {
                String cookies=GetCookies.get();
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
        };
    }
}
