
package com.hongyongfeng.wanandroid.module.project.model;

import static com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel.SQL_INSERT_ARTICLE;
import static com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel.helper;
import static com.hongyongfeng.wanandroid.util.ThreadPools.es;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.data.net.bean.BannerBean;
import com.hongyongfeng.wanandroid.data.net.bean.ProjectBean;
import com.hongyongfeng.wanandroid.module.project.interfaces.ImageCallbackListener;
import com.hongyongfeng.wanandroid.module.project.interfaces.ArticleInterface;
import com.hongyongfeng.wanandroid.module.project.interfaces.ProjectFragmentInterface;
import com.hongyongfeng.wanandroid.module.project.presenter.ArticlePresenter;
import com.hongyongfeng.wanandroid.module.project.presenter.ProjectFragmentPresenter;
import com.hongyongfeng.wanandroid.util.HttpUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                        List<ProjectBean> projectList=HttpUtil.parseJSONWithJSONObject(response, ProjectBean.class);
                        mPresenter.getContract().responseTitleResult(projectList);
                        responseImageBitmap(projectList, new ImageCallbackListener() {
                            @Override
                            public void onError(Exception e) {

                            }

//                            @Override
//                            public void onBitmapFinish(List<Bitmap> bitmapList) {
//                                mPresenter.getContract().responseImageResult(bitmapList);
//
//                            }
                            @Override
                            public void onBitmapFinish(Bitmap bitmap) {
                                mPresenter.getContract().responseImageResult(bitmap);

                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }

            @Override
            public void saveProjectM(ProjectBean project) throws Exception {
                SQLiteDatabase db = helper.getWritableDatabase();
                db.execSQL(SQL_INSERT_ARTICLE,new String[]
                        {String.valueOf(project.getId()),project.getAuthor(),
                                null,project.getLink(),
                                project.getTitle(),project.getNiceDate(),
                                null,null});
            }
        };
    }

    public void responseImageBitmap(List<ProjectBean> beanList, final ImageCallbackListener listener) {
        es.execute(new Runnable() {
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
