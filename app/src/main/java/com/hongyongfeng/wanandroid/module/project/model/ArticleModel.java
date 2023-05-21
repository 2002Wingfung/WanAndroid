package com.hongyongfeng.wanandroid.module.project.model;

import static com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel.SQL_INSERT_ARTICLE;
import static com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel.helper;
import static com.hongyongfeng.wanandroid.module.main.activity.MainActivity.threadPools;
import static com.hongyongfeng.wanandroid.util.Constant.COLLECT_URL;
import static com.hongyongfeng.wanandroid.util.Constant.DELETE_SQL;
import static com.hongyongfeng.wanandroid.util.Constant.DOMAIN_URL;
import static com.hongyongfeng.wanandroid.util.Constant.JSON_URL;
import static com.hongyongfeng.wanandroid.util.Constant.CANCEL_COLLECT_URL;
import static com.hongyongfeng.wanandroid.util.Constant.ONE;
import static com.hongyongfeng.wanandroid.util.Constant.PROJECT_URL;
import static com.hongyongfeng.wanandroid.util.Constant.SELECT_SQL;
import static com.hongyongfeng.wanandroid.util.Constant.TWO;
import static com.hongyongfeng.wanandroid.util.Constant.ZERO;
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
import java.util.List;

/**
 * @author Wingfung Hung
 */
public class ArticleModel extends BaseFragmentModel<ArticlePresenter, ArticleInterface.Model> {
    public ArticleModel(ArticlePresenter mPresenter) {
        super(mPresenter);
    }
    private final String cookies=GetCookies.get();
    @Override
    public ArticleInterface.Model getContract() {
        return new ArticleInterface.Model() {
            @Override
            public void requestTitleM(int id,int page){
                String url=DOMAIN_URL+PROJECT_URL+page+JSON_URL+"?cid="+id;
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
                },cookies);
            }

            @Override
            public void saveProjectM(ProjectBean project){
                SQLiteDatabase db = helper.getWritableDatabase();
                Cursor cursor=db.rawQuery(SELECT_SQL,new String[]{String.valueOf(project.getId())});
                if (cursor.moveToFirst()){
                    int id=cursor.getInt(ZERO);
                    db.execSQL(DELETE_SQL,new String[]{String.valueOf(id)});
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
            public void collectM(int id, CollectListener listener){
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

    public void responseImageBitmap(List<ProjectBean> beanList, final ImageCallbackListener listener) {
        threadPools.es.execute(() -> {
            HttpURLConnection conn=null;
            InputStream is=null;
            try {
                for (ProjectBean projectBean:beanList) {
                    String imagePath = projectBean.getEnvelopePic();
                    URL imgUrl = new URL(imagePath);
                    conn = (HttpURLConnection) imgUrl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    is = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    if (listener!=null){
                        //回调onFinish()方法
                        listener.onBitmapFinish(bitmap);
                    }
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
