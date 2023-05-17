package com.hongyongfeng.wanandroid.module.home.model;

import static com.hongyongfeng.wanandroid.data.local.database.Insert.insert;
import static com.hongyongfeng.wanandroid.module.main.activity.MainActivity.threadPools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.data.net.bean.BannerBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.CollectListener;
import com.hongyongfeng.wanandroid.module.home.interfaces.HomeFragmentInterface;
import com.hongyongfeng.wanandroid.module.home.interfaces.ImageCallbackListener;
import com.hongyongfeng.wanandroid.module.home.presenter.HomeFragmentPresenter;
import com.hongyongfeng.wanandroid.util.GetCookies;
import com.hongyongfeng.wanandroid.util.HttpUtil;
import com.hongyongfeng.wanandroid.util.MyApplication;
import com.hongyongfeng.wanandroid.util.MyDatabaseHelper;
import com.hongyongfeng.wanandroid.util.SaveArticle;
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
    List<ArticleBean> articleBeanList;
    List<ArticleBean> articleTopList;
    List<ArticleBean> articleMoreList;
    List<byte[]> bitmapByteList;
    public static MyDatabaseHelper helper=new MyDatabaseHelper(MyApplication.getContext(),"HistoryArticle.db",null,1);
    List<BannerBean> beanList;
    private static final String IMAGE_URL="https://www.wanandroid.com/banner/json";
    private static final String DOMAIN_URL="https://www.wanandroid.com/";
    private static final String COLLECT_URL="lg/collect/";
    private static final String UNCOLLECT_URL="lg/uncollect_originId/";

    private static final String JSON_URL="/json";
    private static final String ARTICLE_URL="https://www.wanandroid.com/article/list/0/json";
    private static final String ARTICLE_TOP_URL="https://www.wanandroid.com/article/top/json";
    public static final String SQL_INSERT_ARTICLE="insert into article_bean (id,author,chapterName,link,title,niceDate,superChapterName,top)values(?,?,?,?,?,?,?,?)";
    private final Context context=MyApplication.getContext();
    public static Bitmap getBitmap(byte[] data){
        return BitmapFactory.decodeByteArray(data, 0, data.length);//从字节数组解码位图
    }

    @Override
    public HomeFragmentInterface.M getContract() {
        return new HomeFragmentInterface.M() {
            @Override
            public void requestImageM()  {
                try {
                    bitmapByteList = SaveArticle.getData(context,1);  //获取缓存数据
                    beanList=SaveArticle.getData(context,2);

                    //System.out.println(beanList.get(0).getUrl());
                } catch (IllegalAccessException | java.lang.InstantiationException e) {
                    e.printStackTrace();
                }
                if(bitmapByteList!=null&&beanList!=null){     //不为空，即缓存中有数据
                    Log.i("TAG","cache is not null");
                    List<Bitmap> bitmapList=new ArrayList<>();
                    for (int i = 0; i < bitmapByteList.size(); i++) {
                        byte[] data=bitmapByteList.get(i);
                        Bitmap bitmap=getBitmap(data);
                        bitmapList.add(bitmap);
                    }
                    finish(beanList,bitmapList);
                }else{   //为空，从网络获取
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
                    },null);
                }

            }

            @Override
            public void requestArticleM() {
                HttpUtil.sendHttpRequest(ARTICLE_TOP_URL, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        articleTopList=HttpUtil.parseJsonWithObject(response, ArticleBean.class);
                        if (articleBeanList.size()!=0){
                            mPresenter.getContract().responseArticleResult(articleBeanList,articleTopList);
                        }
                    }
                    @Override
                    public void onError(Exception e) {

                    }
                },GetCookies.get());
                HttpUtil.sendHttpRequest(ARTICLE_URL, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        //System.out.println(response);
                        articleBeanList=HttpUtil.parseJsonWithObject(response, ArticleBean.class);
                        if (articleTopList.size()!=0){
                            mPresenter.getContract().responseArticleResult(articleBeanList,articleTopList);
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                        mPresenter.getContract().error(1);
                        List<ArticleBean> list;
                        try {
                            list= SaveArticle.getData(MyApplication.getContext(),0);  //获取缓存数据
                            if(list!=null){     //不为空，即缓存中有数据
                                Log.i("TAG","cache is not null");
                                mPresenter.getContract().responseArticleResult(list,null);
                            }
                        } catch (IllegalAccessException | java.lang.InstantiationException exception) {
                            exception.printStackTrace();
                        }
                    }
                },GetCookies.get());
            }

            @Override
            public void requestLoadMoreM(int page) {
                String loadMore="https://www.wanandroid.com/article/list/"+page+"/json";
                HttpUtil.sendHttpRequest(loadMore, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        articleMoreList=HttpUtil.parseJsonWithObject(response, ArticleBean.class);
                        mPresenter.getContract().responseLoadMoreVP(articleMoreList);
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
                            //System.out.println(response);
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
                    HttpUtil.postCollectRequest(DOMAIN_URL + UNCOLLECT_URL + id + JSON_URL, cookies, new HttpCallbackListener() {
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
    public void finish(List<BannerBean> beanList,List<Bitmap> bitmapList){
        //处理得到的beanlist
        //将beanlist储存为bitmap数组进行返回
        mPresenter.getContract().responseImageResult(beanList,bitmapList);
    }
    public void requestImageBitmap(List<BannerBean> beanList,final ImageCallbackListener listener) {
        threadPools.es.execute(new Runnable() {
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
        });
    }
}
