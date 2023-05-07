package com.hongyongfeng.wanandroid.module.query.presenter;

import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.base.BaseFragmentPresenter;
import com.hongyongfeng.wanandroid.base.BasePresenter;
import com.hongyongfeng.wanandroid.module.query.interfaces.HeatedWords;
import com.hongyongfeng.wanandroid.module.query.interfaces.Query;
import com.hongyongfeng.wanandroid.module.query.model.HeatedWordsModel;
import com.hongyongfeng.wanandroid.module.query.model.QueryModel;
import com.hongyongfeng.wanandroid.module.query.view.QueryActivity;
import com.hongyongfeng.wanandroid.module.query.view.fragment.HeatedWordsFragment;

public class HeatedWordsPresenter extends BaseFragmentPresenter<HeatedWordsModel, HeatedWordsFragment, HeatedWords.VP> {

    @Override
    public HeatedWordsModel getModelInstance() {
        return new HeatedWordsModel(this);
    }

    @Override
    public HeatedWords.VP getContract() {
        return new HeatedWords.VP() {
            @Override
            public void requestQueryVP(String name) {
                try {
                    mModel.getContract().requestQueryM(name);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void responseQueryResult(boolean loginStatusResult) {
                mView.getContract().responseQueryResult(loginStatusResult);
            }
        };
    }
}
