package com.hongyongfeng.wanandroid.module.query.model;

import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.BaseModel;
import com.hongyongfeng.wanandroid.module.query.interfaces.HeatedWords;
import com.hongyongfeng.wanandroid.module.query.interfaces.Query;
import com.hongyongfeng.wanandroid.module.query.presenter.HeatedWordsPresenter;
import com.hongyongfeng.wanandroid.module.query.presenter.QueryPresenter;

public class HeatedWordsModel extends BaseFragmentModel<HeatedWordsPresenter, HeatedWords.M> {
    public HeatedWordsModel(HeatedWordsPresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public HeatedWords.M getContract() {
        return new HeatedWords.M() {
            @Override
            public void requestQueryM(String name) throws Exception {
                mPresenter.getContract().responseQueryResult(true);
            }
        };
    }
}
