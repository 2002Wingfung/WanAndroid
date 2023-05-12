package com.hongyongfeng.wanandroid.module.knowledge.view.presenter;


import com.hongyongfeng.wanandroid.base.BaseFragmentPresenter;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.knowledge.interfaces.ArticleInterface;
import com.hongyongfeng.wanandroid.module.knowledge.view.fragment.KnowledgeArticleFragment;
import com.hongyongfeng.wanandroid.module.knowledge.view.model.ArticleModel;

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
