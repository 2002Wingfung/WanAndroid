package com.hongyongfeng.wanandroid.module.query.model;

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
            public void requestLoadMoreM(int page) throws Exception {

            }
        };
    }

}
