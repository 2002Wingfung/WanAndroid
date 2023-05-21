package com.hongyongfeng.wanandroid.module.project.model;

import static com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel.SQL_INSERT_ARTICLE;
import static com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel.helper;
import static com.hongyongfeng.wanandroid.module.main.activity.MainActivity.threadPools;
import static com.hongyongfeng.wanandroid.util.Constant.COLLECT_URL;
import static com.hongyongfeng.wanandroid.util.Constant.DOMAIN_URL;
import static com.hongyongfeng.wanandroid.util.Constant.JSON_URL;
import static com.hongyongfeng.wanandroid.util.Constant.CANCEL_COLLECT_URL;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.data.net.bean.ProjectBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.CollectListener;
import com.hongyongfeng.wanandroid.module.project.interfaces.ImageCallbackListener;
import com.hongyongfeng.wanandroid.module.project.interfaces.ArticleInterface;
import com.hongyongfeng.wanandroid.module.project.presenter.ArticlePresenter;
import com.hongyongfeng.wanandroid.util.GetCookies;
import com.hongyongfeng.wanandroid.util.HttpUtil;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wingfung Hung
 */
public class ArticleModel extends BaseFragmentModel<ArticlePresenter, ArticleInterface.M> {
    public ArticleModel(ArticlePresenter mPresenter) {
        super(mPresenter);
    }
    private final List<Bitmap> bitmapList=new ArrayList<>();

    @Override
    public ArticleInterface.M getContract() {
        return new ArticleInterface.M() {
            @Override
            public void requestTitleM(int id,int page) throws Exception {
                String url="https://www.wanandroid.com/project/list/"+page+"/json?cid="+id;
                HttpUtil.sendHttpRequest(url, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        List<ProjectBean> projectList=HttpUtil.parseJsonWithObject(response, ProjectBean.class);
                        mPresenter.getContract().responseTitleResult(projectList);
                        responseImageBitmap(projectList, new ImageCallbackListener() {
                            @Override
                            public void onError(Exception e) {
                                //加载项目文章错误回调，取消dialog的显示
                            }

                            @Override
                            public void onBitmapFinish(Bitmap bitmap) {
                                mPresenter.getContract().responseImageResult(bitmap);

                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        //加载项目文章错误回调，取消dialog的显示
                    }
                },GetCookies.get());
            }

            @Override
            public void saveProjectM(ProjectBean project) throws Exception {
                SQLiteDatabase db = helper.getWritableDatabase();
                Cursor cursor=db.rawQuery("select id from article_bean where id=?",new String[]{String.valueOf(project.getId())});
                if (cursor.moveToFirst()){
                    int id=cursor.getInt(0);
                    db.execSQL("delete from article_bean where id=?",new String[]{String.valueOf(id)});
                }
                db.execSQL(SQL_INSERT_ARTICLE,new String[]
                        {String.valueOf(project.getId()),project.getAuthor(),
                                null,project.getLink(),
                                project.getTitle(),
                                null,null});
                cursor.close();
                db.close();
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

    public void responseImageBitmap(List<ProjectBean> beanList, final ImageCallbackListener listener) {
        threadPools.es.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn=null;
                InputStream is=null;
                try {
                    bitmapList.clear();
                    for (ProjectBean projectBean:beanList) {
                        String imagePath = projectBean.getEnvelopePic();
                        URL imgUrl = new URL(imagePath);
                        conn = (HttpURLConnection) imgUrl.openConnection();
                        conn.setDoInput(true);
                        conn.connect();
                        is = conn.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        //bitmapList.add(bitmap);
                        if (listener!=null){
                            //回调onFinish()方法
                            listener.onBitmapFinish(bitmap);
                        }
                    }
//                    if (listener!=null){
//                        //回调onFinish()方法
//                        listener.onBitmapFinish(bitmapList);
//                    }
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
