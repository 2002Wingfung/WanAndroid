
package com.hongyongfeng.wanandroid.module.main.model;

import static com.hongyongfeng.wanandroid.data.local.database.Insert.insert;
import static com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel.helper;
import static com.hongyongfeng.wanandroid.util.Constant.COLLECTION_URL;
import static com.hongyongfeng.wanandroid.util.Constant.COLLECT_URL;
import static com.hongyongfeng.wanandroid.util.Constant.DOMAIN_URL;
import static com.hongyongfeng.wanandroid.util.Constant.JSON_URL;
import static com.hongyongfeng.wanandroid.util.Constant.CANCEL_COLLECT_URL;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hongyongfeng.wanandroid.base.BaseModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.CollectListener;
import com.hongyongfeng.wanandroid.module.main.interfaces.MoreInterface;
import com.hongyongfeng.wanandroid.module.main.presenter.MorePresenter;
import com.hongyongfeng.wanandroid.util.GetCookies;
import com.hongyongfeng.wanandroid.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

public class MoreModel extends BaseModel<MorePresenter, MoreInterface.M> {
    public MoreModel(MorePresenter mPresenter) {
        super(mPresenter);
    }
    private final List<ArticleBean> articleBeanList=new ArrayList<>();


    @Override
    public MoreInterface.M getContract() {
        return new MoreInterface.M() {


            @Override
            public void requestHistoryM() throws Exception {
                SQLiteDatabase db = helper.getWritableDatabase();
                Cursor cursor=db.rawQuery("select * from article_bean order by main_id desc",new String[] {});
                if (cursor.moveToFirst()){
                    do {
                        ArticleBean article=getArticle(cursor);
                        articleBeanList.add(article);
                    }while (cursor.moveToNext());
                }
                mPresenter.getContract().responseHistoryVP(articleBeanList);
            }

            @Override
            public void requestCollectM() throws Exception {
                String cookies=GetCookies.get();
                if (cookies == null||"".equals(cookies)) {
                    mPresenter.getContract().collectResponse(1);
                }else {
                    HttpUtil.sendHttpRequest(COLLECTION_URL, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            System.out.println(response);
                            List<ArticleBean> articleBeanList =HttpUtil.parseJsonWithObject(response,ArticleBean.class);
                            mPresenter.getContract().responseCollectVP(articleBeanList);
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    }, cookies);
                }

            }

            @Override
            public void saveArticleM(ArticleBean article) throws Exception {
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
                            //System.out.println(response);
                            listener.onFinish();
                            mPresenter.getContract().collectResponse(0);
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

    private ArticleBean getArticle(Cursor cursor) {
        ArticleBean article=new ArticleBean();
        article.setId(cursor.getInt(0));
        article.setAuthor(cursor.getString(1));
        article.setChapterName(cursor.getString(2));
        article.setLink(cursor.getString(3));
        article.setTitle(cursor.getString(4));
        article.setNiceDate(cursor.getString(5));
        article.setSuperChapterName(cursor.getString(6));
        article.setTop(cursor.getInt(7));
        return article;
    }
}
