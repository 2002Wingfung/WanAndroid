package com.hongyongfeng.wanandroid.module.knowledge.model;

import static com.hongyongfeng.wanandroid.data.local.database.Insert.insert;
import static com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel.SQL_INSERT_ARTICLE;
import static com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel.helper;

import android.database.sqlite.SQLiteDatabase;

import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.knowledge.interfaces.ArticleInterface;
import com.hongyongfeng.wanandroid.module.knowledge.presenter.ArticlePresenter;
import com.hongyongfeng.wanandroid.util.HttpUtil;
import com.hongyongfeng.wanandroid.util.MyApplication;
import com.hongyongfeng.wanandroid.util.MyDatabaseHelper;

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
                });
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
        };
    }

}
