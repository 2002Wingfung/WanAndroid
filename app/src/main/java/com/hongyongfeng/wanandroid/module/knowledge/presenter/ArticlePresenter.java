package com.hongyongfeng.wanandroid.module.knowledge.presenter;


import com.hongyongfeng.wanandroid.base.BaseFragmentPresenter;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.knowledge.interfaces.ArticleInterface;
import com.hongyongfeng.wanandroid.module.knowledge.view.fragment.KnowledgeArticleFragment;
import com.hongyongfeng.wanandroid.module.knowledge.model.ArticleModel;

import java.util.List;

public class ArticlePresenter extends BaseFragmentPresenter<ArticleModel, KnowledgeArticleFragment, ArticleInterface.VP> {
    @Override
    public ArticleModel getModelInstance() {
        return new ArticleModel(this);
    }

    @Override
    public ArticleInterface.VP getContract() {
        return new ArticleInterface.VP() {


            @Override
            public void saveHistory(ArticleBean article) {
                try {
                    mModel.getContract().saveArticleM(article);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void collectVP(int id) {
                try {
                    mModel.getContract().collectM(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void unCollectVP(int id) {
                try {
                    mModel.getContract().unCollectM(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void collectResponse(boolean bool) {
                mView.getContract().collectResponse(bool);
            }

            @Override
            public void unCollectResponse(boolean bool) {
                mView.getContract().unCollectResponse(bool);
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
