package com.hongyongfeng.wanandroid.module.query.presenter;


import com.hongyongfeng.wanandroid.base.BaseFragmentPresenter;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.CollectListener;
import com.hongyongfeng.wanandroid.module.query.interfaces.LoadMoreInterface;
import com.hongyongfeng.wanandroid.module.query.model.LoadMoreModel;
import com.hongyongfeng.wanandroid.module.query.view.fragment.ArticleFragment;
import java.util.List;

/**
 * @author Wingfung Hung
 */
public class LoadMorePresenter extends BaseFragmentPresenter<LoadMoreModel, ArticleFragment, LoadMoreInterface.Vp> {
    @Override
    public LoadMoreModel getModelInstance() {
        return new LoadMoreModel(this);
    }

    @Override
    public LoadMoreInterface.Vp getContract() {
        return new LoadMoreInterface.Vp() {
            @Override
            public void requestLoadMoreVp(String key, int page) {
                try {
                    mModel.getContract().requestLoadMoreM(key,page);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void responseLoadMoreVp(List<ArticleBean> articleList) {
                mView.getContract().responseLoadMoreVp(articleList);
            }

            @Override
            public void error(Exception e) {
                mView.getContract().error(e);
            }

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
            public void saveHistory(ArticleBean article) {
                try {
                    mModel.getContract().saveArticleM(article);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
