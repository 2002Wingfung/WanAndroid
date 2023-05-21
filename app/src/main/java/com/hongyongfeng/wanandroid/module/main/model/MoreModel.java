
package com.hongyongfeng.wanandroid.module.main.model;

import static com.hongyongfeng.wanandroid.data.local.database.Insert.insert;
import static com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel.helper;
import static com.hongyongfeng.wanandroid.util.Constant.COLLECTION_URL;
import static com.hongyongfeng.wanandroid.util.Constant.COLLECT_URL;
import static com.hongyongfeng.wanandroid.util.Constant.DOMAIN_URL;
import static com.hongyongfeng.wanandroid.util.Constant.FIVE;
import static com.hongyongfeng.wanandroid.util.Constant.FOUR;
import static com.hongyongfeng.wanandroid.util.Constant.JSON_URL;
import static com.hongyongfeng.wanandroid.util.Constant.CANCEL_COLLECT_URL;
import static com.hongyongfeng.wanandroid.util.Constant.ONE;
import static com.hongyongfeng.wanandroid.util.Constant.SELECT_HISTORY_SQL;
import static com.hongyongfeng.wanandroid.util.Constant.SEVEN;
import static com.hongyongfeng.wanandroid.util.Constant.SIX;
import static com.hongyongfeng.wanandroid.util.Constant.THREE;
import static com.hongyongfeng.wanandroid.util.Constant.TWO;
import static com.hongyongfeng.wanandroid.util.Constant.ZERO;
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

/**
 * @author Wingfung Hung
 */
public class MoreModel extends BaseModel<MorePresenter, MoreInterface.Model> {
    public MoreModel(MorePresenter mPresenter) {
        super(mPresenter);
    }
    private final List<ArticleBean> articleBeanList=new ArrayList<>();


    @Override
    public MoreInterface.Model getContract() {
        return new MoreInterface.Model() {
            @Override
            public void requestHistoryM() {
                SQLiteDatabase db = helper.getWritableDatabase();
                Cursor cursor=db.rawQuery(SELECT_HISTORY_SQL,new String[] {});
                if (cursor.moveToFirst()){
                    do {
                        ArticleBean article=getArticle(cursor);
                        articleBeanList.add(article);
                    }while (cursor.moveToNext());
                }
                mPresenter.getContract().responseHistoryVp(articleBeanList);
            }

            @Override
            public void requestCollectM(int page)  {
                String cookies=GetCookies.get();
                String url=COLLECTION_URL+page+JSON_URL;
                if (cookies == null||"".equals(cookies)) {
                    mPresenter.getContract().collectResponse(ONE);
                }else {
                    HttpUtil.sendHttpRequest(url, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            //System.out.println(response);
                            List<ArticleBean> articleBeanList =HttpUtil.parseJsonWithObject(response,ArticleBean.class);
                            mPresenter.getContract().responseCollectVp(articleBeanList);
                        }

                        @Override
                        public void onError(Exception e) {
                        }
                    }, cookies);
                }
            }

            @Override
            public void saveArticleM(ArticleBean article){
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
                            listener.onFinish();
                            mPresenter.getContract().collectResponse(ZERO);
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

    private ArticleBean getArticle(Cursor cursor) {
        ArticleBean article=new ArticleBean();
        article.setId(cursor.getInt(ZERO));
        article.setAuthor(cursor.getString(ONE));
        article.setChapterName(cursor.getString(TWO));
        article.setLink(cursor.getString(THREE));
        article.setTitle(cursor.getString(FOUR));
        article.setNiceDate(cursor.getString(FIVE));
        article.setSuperChapterName(cursor.getString(SIX));
        article.setTop(cursor.getInt(SEVEN));
        return article;
    }
}
