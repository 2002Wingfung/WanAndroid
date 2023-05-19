package com.hongyongfeng.wanandroid.module.home.presenter;

import android.graphics.Bitmap;
import com.hongyongfeng.wanandroid.base.BaseFragmentPresenter;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.data.net.bean.BannerBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.CollectListener;
import com.hongyongfeng.wanandroid.module.home.interfaces.HomeFragmentInterface;
import com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel;
import com.hongyongfeng.wanandroid.module.home.view.fragment.HomeFragment;
import java.util.List;

/**
 * @author Wingfung Hung
 */
public class HomeFragmentPresenter extends BaseFragmentPresenter<HomeFragmentModel, HomeFragment, HomeFragmentInterface.ViewPresenter> {
    @Override
    public HomeFragmentModel getModelInstance() {
        return new HomeFragmentModel(this);
    }

    /**
     *
     * @return 返回p层接口
     */
    @Override
    public HomeFragmentInterface.ViewPresenter getContract() {
        return new HomeFragmentInterface.ViewPresenter() {
            @Override
            public void collectVp(int id, CollectListener listener) {
                try {
                    mModel.getContract().collectM(id,listener);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void unCollectVp(int id, CollectListener listener) {
                try {
                    mModel.getContract().unCollectM(id,listener);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void collectResponse(int code) {
                mView.getContract().collectResponse(code);
            }

            @Override
            public void unCollectResponse(int code) {
                mView.getContract().unCollectResponse(code);
            }

            @Override
            public void requestImageVp() {
                //核验请求的信息，进行逻辑处理
                //调用model层
                try {
//                    mModel.requestLogin(name,pwd);
                    mModel.getContract().requestImageM();

                } catch (Exception e) {
                    e.printStackTrace();
                    //异常的处理
                    //保存到日志
                    //一系列的异常处理
                    //...
                }
            }

            @Override
            public void responseImageResult(List<BannerBean> beanList,List<Bitmap> bitmapList) {
                mView.getContract().responseImageResult(beanList,bitmapList);
            }

            @Override
            public void requestArticleVp()  {
                try {
                    mModel.getContract().requestArticleM();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void responseArticleResult(List<ArticleBean> articleList,List<ArticleBean> articleTopLists) {
                mView.getContract().responseArticleResult(articleList,articleTopLists);
            }

            @Override
            public void error(int error) {
                mView.getContract().error(error);
            }

            @Override
            public void requestLoadMoreVp(int page) {
                try {
                    mModel.getContract().requestLoadMoreM(page);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void responseLoadMoreVp(List<ArticleBean> articleList) {
                mView.getContract().responseLoadMoreVp(articleList);
            }

            @Override
            public void saveHistory(ArticleBean article) {
                try {
                    mModel.getContract().saveArticleM(article);

                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
    }
}
