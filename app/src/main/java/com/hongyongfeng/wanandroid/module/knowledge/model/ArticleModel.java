package com.hongyongfeng.wanandroid.module.knowledge.model;

import static com.hongyongfeng.wanandroid.data.local.database.Insert.insert;
import static com.hongyongfeng.wanandroid.util.Constant.COLLECT_URL;
import static com.hongyongfeng.wanandroid.util.Constant.DOMAIN_URL;
import static com.hongyongfeng.wanandroid.util.Constant.JSON_URL;
import static com.hongyongfeng.wanandroid.util.Constant.CANCEL_COLLECT_URL;

import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.CollectListener;
import com.hongyongfeng.wanandroid.module.knowledge.interfaces.ArticleInterface;
import com.hongyongfeng.wanandroid.module.knowledge.presenter.ArticlePresenter;
import com.hongyongfeng.wanandroid.util.GetCookies;
import com.hongyongfeng.wanandroid.util.HttpUtil;

import java.util.List;

public class ArticleModel extends BaseFragmentModel<ArticlePresenter, ArticleInterface.M> {
    public ArticleModel(ArticlePresenter mPresenter) {
        super(mPresenter);
    }
    //MyDatabaseHelper helper=new MyDatabaseHelper(MyApplication.getContext(),"HistoryArticle.db",null,1);

    @Override
    public ArticleInterface.M getContract() {
        return new ArticleInterface.M() {

            @Override
            public void requestArticleM(int id, int page) throws Exception {
                String url="https://www.wanandroid.com/article/list/"+page+"/json?cid="+id;
                HttpUtil.sendHttpRequest(url, new HttpCallbackListener() {

                    @Override
                    public void onFinish(String response) {
                        List<ArticleBean> articleBeanList = HttpUtil.parseJSONWithJSONObject(response, ArticleBean.class);
                        mPresenter.getContract().responseArticleVP(articleBeanList);
                    }


                    @Override
                    public void onError(Exception e) {
                    }
                },GetCookies.get());
            }

            @Override
            public void saveArticleM(ArticleBean article) throws Exception {
//                SQLiteDatabase db = helper.getWritableDatabase();
//                db.execSQL(SQL_INSERT_ARTICLE,new String[]
//                        {String.valueOf(article.getId()),article.getAuthor(),
//                                article.getChapterName(),article.getLink(),
//                                article.getTitle(),article.getNiceDate(),
//                                article.getSuperChapterName(),String.valueOf(article.getTop())});
                insert(article);
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
        };
    }

}
