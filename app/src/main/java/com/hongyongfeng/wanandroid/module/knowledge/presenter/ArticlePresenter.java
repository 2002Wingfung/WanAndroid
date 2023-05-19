package com.hongyongfeng.wanandroid.module.knowledge.presenter;


import com.hongyongfeng.wanandroid.base.BaseFragmentPresenter;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.CollectListener;
import com.hongyongfeng.wanandroid.module.knowledge.interfaces.ArticleInterface;
import com.hongyongfeng.wanandroid.module.knowledge.view.fragment.KnowledgeArticleFragment;
import com.hongyongfeng.wanandroid.module.knowledge.model.ArticleModel;

import java.util.List;

public class ArticlePresenter extends BaseFragmentPresenter<ArticleModel, KnowledgeArticleFragment, ArticleInterface.ViewPresenter> {
    @Override
    public ArticleModel getModelInstance() {
        return new ArticleModel(this);
    }

    @Override
    public ArticleInterface.ViewPresenter getContract() {
        return new ArticleInterface.ViewPresenter() {


            @Override
            public void saveHistory(ArticleBean article) {
                try {
                    mModel.getContract().saveArticleM(article);
                }catch (Exception e){
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
            public void unCollectVP(int id, CollectListener listener) {
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
            public void requestArticleVP(int id, int page) {
                try {
                    mModel.getContract().requestArticleM(id,page);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void responseArticleVP(List<ArticleBean> articleList) {
                mView.getContract().responseArticleVP(articleList);

            }
        };
    }
}
