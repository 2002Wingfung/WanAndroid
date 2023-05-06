package com.hongyongfeng.wanandroid.module.home.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.data.net.bean.BannerBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.HomeFragmentInterface;
import com.hongyongfeng.wanandroid.module.home.interfaces.ImageCallbackListener;
import com.hongyongfeng.wanandroid.module.home.presenter.HomeFragmentPresenter;
import com.hongyongfeng.wanandroid.test.Bean;
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

public class HomeFragmentModel extends BaseFragmentModel<HomeFragmentPresenter, HomeFragmentInterface.M> {
    public HomeFragmentModel(HomeFragmentPresenter mPresenter) {
        super(mPresenter);
    }
    private List<BannerBean> parseJSONWithJSONObject(String toString) {
        List<BannerBean> beanList=new ArrayList<>();
        try {
            int indexStart=toString.indexOf('[');
            int indexEnd=toString.indexOf(']');
            JSONArray jsonArray=new JSONArray(toString.substring(indexStart,indexEnd+1));
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String id =jsonObject.getString("id");
                String imagePath =jsonObject.getString("imagePath");
                String url =jsonObject.getString("url");
//                Log.d("MainActivity","id is "+id);
//                Log.d("MainActivity","imagePath is "+imagePath);
//                Log.d("MainActivity","url is "+url);
                BannerBean bannerBean=new BannerBean(imagePath,url);
                beanList.add(bannerBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return beanList;
    }
    List<Bitmap> bitmapList=new ArrayList<>();

    List<BannerBean> beanList;
    private static final String IMAGE_URL="https://www.wanandroid.com/banner/json";
    private static final String ARTICLE_URL="https://www.wanandroid.com/article/list/0/json";
    private static final String ARTICLE_TOP_URL="https://www.wanandroid.com/article/top/json";


    @Override
    public HomeFragmentInterface.M getContract() {
        return new HomeFragmentInterface.M() {
            @Override
            public void requestImageM() throws Exception {
                HttpUtil.sendHttpRequest(IMAGE_URL, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        beanList= parseJSONWithJSONObject(response);
                        requestImageBitmap(beanList, new ImageCallbackListener() {
                            @Override
                            public void onError(Exception e) {

                            }

                            @Override
                            public void onBitmapFinish(List<Bitmap> bitmapList) {
                                finish(beanList,bitmapList);

                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }

            @Override
            public void requestArticleM() throws Exception {
                HttpUtil.sendHttpRequest(ARTICLE_URL, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        List<ArticleBean> articleBeanList=HttpUtil.parseJSONWithJSONObject(response, ArticleBean.class);
                        mPresenter.getContract().responseArticleResult(articleBeanList);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        };
    }
    public void finish(List<BannerBean> beanList,List<Bitmap> bitmapList){
        //处理得到的beanlist
        //将beanlist储存为bitmap数组进行返回
        mPresenter.getContract().responseImageResult(beanList,bitmapList);
    }
    public void requestImageBitmap(List<BannerBean> beanList,final ImageCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn=null;
                InputStream is=null;
                try {
                    for (BannerBean bannerBean:beanList) {
                        String imagePath = bannerBean.getImagePath();
                        URL imgUrl = new URL(imagePath);
                        conn = (HttpURLConnection) imgUrl.openConnection();
                        conn.setDoInput(true);
                        conn.connect();
                        is = conn.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        bitmapList.add(bitmap);
                    }
                    if (listener!=null){
                        //回调onFinish()方法
                        listener.onBitmapFinish(bitmapList);
                    }
                } catch (IOException e) {
                    if (listener!=null){
                        //回调onError()方法
                        listener.onError(e);
                        e.printStackTrace();
                    }
                }finally {
                    if (is!=null&&listener!=null){
                        try {
                            is.close();
                        }catch (IOException e){
                            listener.onError(e);
                        }
                    }
                    if (conn !=null){
                        conn.disconnect();
                    }
                }
            }
        }).start();
    }
}
