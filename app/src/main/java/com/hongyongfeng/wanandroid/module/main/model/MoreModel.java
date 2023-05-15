
package com.hongyongfeng.wanandroid.module.main.model;

import static com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel.helper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hongyongfeng.wanandroid.base.BaseModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.main.interfaces.MoreInterface;
import com.hongyongfeng.wanandroid.module.main.presenter.MorePresenter;
import com.hongyongfeng.wanandroid.module.query.interfaces.Query;
import com.hongyongfeng.wanandroid.module.query.presenter.QueryPresenter;
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
            }

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
