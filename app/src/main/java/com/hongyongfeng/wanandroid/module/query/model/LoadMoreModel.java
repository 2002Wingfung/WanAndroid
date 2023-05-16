package com.hongyongfeng.wanandroid.module.query.model;

import static com.hongyongfeng.wanandroid.data.local.database.Insert.insert;
import static com.hongyongfeng.wanandroid.util.Constant.COLLECT_URL;
import static com.hongyongfeng.wanandroid.util.Constant.DOMAIN_URL;
import static com.hongyongfeng.wanandroid.util.Constant.JSON_URL;
import static com.hongyongfeng.wanandroid.util.Constant.UNCOLLECT_URL;
import static com.hongyongfeng.wanandroid.util.ThreadPools.es;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.data.net.bean.BannerBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.HomeFragmentInterface;
import com.hongyongfeng.wanandroid.module.home.interfaces.ImageCallbackListener;
import com.hongyongfeng.wanandroid.module.home.presenter.HomeFragmentPresenter;
import com.hongyongfeng.wanandroid.module.query.interfaces.LoadMoreInterface;
import com.hongyongfeng.wanandroid.module.query.presenter.LoadMorePresenter;
import com.hongyongfeng.wanandroid.util.GetCookies;
import com.hongyongfeng.wanandroid.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
            public void collectM(int id) throws Exception {
                HttpUtil.postCollectRequest(DOMAIN_URL + COLLECT_URL + id + JSON_URL, GetCookies.get(), new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        //System.out.println(response);
                        mPresenter.getContract().collectResponse(true);
                    }

                    @Override
                    public void onError(Exception e) {
                        mPresenter.getContract().collectResponse(false);
                    }
                });
            }

            @Override
            public void unCollectM(int id) throws Exception {
                HttpUtil.postCollectRequest(DOMAIN_URL + UNCOLLECT_URL + id + JSON_URL, GetCookies.get(), new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        mPresenter.getContract().unCollectResponse(true);
                    }

                    @Override
                    public void onError(Exception e) {
                        mPresenter.getContract().unCollectResponse(false);
                    }
                });
            }

            @Override
            public void saveArticleM(ArticleBean article) throws Exception {
                insert(article);
            }
        };
    }

}
