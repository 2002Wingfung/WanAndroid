package com.hongyongfeng.wanandroid.module.main.presenter;

import com.hongyongfeng.wanandroid.base.BasePresenter;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.CollectListener;
import com.hongyongfeng.wanandroid.module.main.activity.MoreActivity;
import com.hongyongfeng.wanandroid.module.main.interfaces.MoreInterface;
import com.hongyongfeng.wanandroid.module.main.model.MoreModel;
import java.util.List;

/**
 * @author Wingfung Hung
 */
public class MorePresenter extends BasePresenter<MoreModel, MoreActivity, MoreInterface.Vp> {

    @Override
    public MoreModel getModelInstance() {
        return new MoreModel(this);
    }

    @Override
    public MoreInterface.Vp getContract() {
        return new MoreInterface.Vp() {
            @Override
            public void saveHistory(ArticleBean article) {
                try {
                    mModel.getContract().saveArticleM(article);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
            public void requestHistoryVp() {
                try {
                    mModel.getContract().requestHistoryM();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void responseHistoryVp(List<ArticleBean> article) {
                mView.getContract().responseHistoryVp(article);
            }

            @Override
            public void requestCollectVp(int page) {
                try {
                    mModel.getContract().requestCollectM(page);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void responseCollectVp(List<ArticleBean> article) {
                mView.getContract().responseCollectVp(article);
            }

            @Override
            public void collectResponse(int code) {
                mView.getContract().collectResponse(code);
            }

            @Override
            public void unCollectResponse(int code) {
                mView.getContract().unCollectResponse(code);
            }

        };
    }
}
