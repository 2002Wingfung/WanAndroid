package com.hongyongfeng.wanandroid.module.home.model;

import static com.hongyongfeng.wanandroid.data.local.database.Insert.insert;
import static com.hongyongfeng.wanandroid.module.main.activity.MainActivity.threadPools;
import static com.hongyongfeng.wanandroid.util.Constant.ARTICLE_URL_1;
import static com.hongyongfeng.wanandroid.util.Constant.ONE;
import static com.hongyongfeng.wanandroid.util.Constant.TWO;
import static com.hongyongfeng.wanandroid.util.Constant.ZERO;

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

/**
 * 首页文章model层
 * @author Wingfung Hung
 */
public class HomeFragmentModel extends BaseFragmentModel<HomeFragmentPresenter, HomeFragmentInterface.Model> {
    /**
     * 绑定对应的Presenter
     * @param mPresenter P层
     */
    public HomeFragmentModel(HomeFragmentPresenter mPresenter) {
        super(mPresenter);
    }
    private final String cookies=GetCookies.get();
    /**
     * 将json字符串的数据解析出来，并存入实体类中
     * @param toString json字符串
     * @return 返回一个BannerBean实体类的集合
     */
    private List<BannerBean> parseJsonWithJsonObject(String toString) {
        List<BannerBean> beanList=new ArrayList<>();
        try {
            //截取json字符串
            int indexStart=toString.indexOf('[');
            int indexEnd=toString.indexOf(']');
            JSONArray jsonArray=new JSONArray(toString.substring(indexStart,indexEnd+1));
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                //获取json中的字段
                int id=jsonObject.getInt("id");
                String imagePath =jsonObject.getString("imagePath");
                String url =jsonObject.getString("url");
                BannerBean bannerBean=new BannerBean(imagePath,url,id);
                beanList.add(bannerBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return beanList;
    }

    private final List<Bitmap> bitmapList=new ArrayList<>();
    private List<ArticleBean> articleBeanList;
    private List<ArticleBean> articleTopList;
    private List<ArticleBean> articleMoreList;
    private List<byte[]> bitmapByteList;
    public static MyDatabaseHelper helper=new MyDatabaseHelper(MyApplication.getContext(),"HistoryArticle.db",null,1);
    private List<BannerBean> beanList;
    private static final String IMAGE_URL="https://www.wanandroid.com/banner/json";
    private static final String DOMAIN_URL="https://www.wanandroid.com/";
    private static final String COLLECT_URL="lg/collect/";
    private static final String UNCOLLECT_URL ="lg/uncollect_originId/";

    private static final String JSON_URL="/json";
    public static final String ARTICLE_URL="https://www.wanandroid.com/article/list/0/json";
    private static final String ARTICLE_TOP_URL="https://www.wanandroid.com/article/top/json";
    public static final String SQL_INSERT_ARTICLE="insert into article_bean (id,author,chapterName,link,title,niceDate,superChapterName,top)values(?,?,?,?,?,strftime('%Y-%m-%d %H:%M','now','localtime'),?,?)";
    private final Context context=MyApplication.getContext();
    public static Bitmap getBitmap(byte[] data){
        return BitmapFactory.decodeByteArray(data, ZERO, data.length);
        //从字节数组解码位图
    }

    /**
     * 返回一个Model层接口的对象，用于回调方法
     */
    @Override
    public HomeFragmentInterface.Model getContract() {
        return new HomeFragmentInterface.Model() {
            @Override
            public void requestImageM() {//每次打开app时首先打开本地缓存的图片文件
                try {
                    bitmapByteList = SaveArticle.getData(context,ONE);
                    //获取缓存数据
                    beanList=SaveArticle.getData(context,TWO);
                } catch (IllegalAccessException | java.lang.InstantiationException e) {
                    e.printStackTrace();
                }
                if(bitmapByteList!=null&&beanList!=null){//不为空，即缓存中有数据
                    Log.i("TAG","cache is not null");
                    List<Bitmap> bitmapList=new ArrayList<>();
                    for (int i = ZERO; i < bitmapByteList.size(); i++) {
                        byte[] data=bitmapByteList.get(i);
                        Bitmap bitmap=getBitmap(data);
                        bitmapList.add(bitmap);
                    }
                    finish(beanList,bitmapList);
                }else{//为空，从网络获取
                    HttpUtil.sendHttpRequest(IMAGE_URL, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            beanList= parseJsonWithJsonObject(response);
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
                //请求首页置顶文章的数据
                HttpUtil.sendHttpRequest(ARTICLE_TOP_URL, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        articleTopList=HttpUtil.parseJsonWithObject(response, ArticleBean.class);
                        if (articleBeanList.size()!=ZERO){
                            mPresenter.getContract().responseArticleResult(articleBeanList,articleTopList);
                        }
                    }
                    @Override
                    public void onError(Exception e) {
                    }
                },cookies);
                //请求首页文章的数据
                HttpUtil.sendHttpRequest(ARTICLE_URL, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        articleBeanList=HttpUtil.parseJsonWithObject(response, ArticleBean.class);
                        if (articleTopList!=null&&articleTopList.size()!=ZERO){
                            mPresenter.getContract().responseArticleResult(articleBeanList,articleTopList);
                        }
                    }
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                        mPresenter.getContract().error(ONE);
                        List<ArticleBean> list;
                        try {
                            list= SaveArticle.getData(MyApplication.getContext(),ZERO);  //获取缓存数据
                            if(list!=null){
                                //不为空，即缓存中有数据
                                Log.i("TAG","cache is not null");
                                mPresenter.getContract().responseArticleResult(list,null);
                            }
                        } catch (IllegalAccessException | java.lang.InstantiationException exception) {
                            exception.printStackTrace();
                        }
                    }
                },cookies);
            }
            @Override
            public void requestLoadMoreM(int page) {
                String loadMore=DOMAIN_URL+ARTICLE_URL_1+page+JSON_URL;
                HttpUtil.sendHttpRequest(loadMore, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        articleMoreList=HttpUtil.parseJsonWithObject(response, ArticleBean.class);
                        mPresenter.getContract().responseLoadMoreVp(articleMoreList);
                    }
                    @Override
                    public void onError(Exception e) {
                    }
                },cookies);
            }
            @Override
            public void saveArticleM(ArticleBean article) {
                insert(article);
            }
            @Override
            public void collectM(int id, CollectListener listener) {
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
                if (cookies == null||"".equals(cookies)) {
                    mPresenter.getContract().collectResponse(ONE);
                }else {
                    HttpUtil.postCollectRequest(DOMAIN_URL + UNCOLLECT_URL + id + JSON_URL, cookies, new HttpCallbackListener() {
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
    public void finish(List<BannerBean> beanList,List<Bitmap> bitmapList){
        //处理得到的bean集合//将bean集合储存为bitmap数组进行返回
        mPresenter.getContract().responseImageResult(beanList,bitmapList);
    }
    public void requestImageBitmap(List<BannerBean> beanList,final ImageCallbackListener listener) {
        threadPools.es.execute(() -> {
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
                if (listener!=null){//回调onFinish()方法
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
        });
    }
}
