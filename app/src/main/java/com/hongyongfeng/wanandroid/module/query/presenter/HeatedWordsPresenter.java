package com.hongyongfeng.wanandroid.module.query.presenter;

import com.hongyongfeng.wanandroid.base.BaseFragmentPresenter;
import com.hongyongfeng.wanandroid.module.query.interfaces.HeatedWords;
import com.hongyongfeng.wanandroid.module.query.model.HeatedWordsModel;
import com.hongyongfeng.wanandroid.module.query.view.fragment.HeatedWordsFragment;

import java.util.List;
import java.util.Map;

public class HeatedWordsPresenter extends BaseFragmentPresenter<HeatedWordsModel, HeatedWordsFragment, HeatedWords.VP> {

    @Override
    public HeatedWordsModel getModelInstance() {
        return new HeatedWordsModel(this);
    }

    @Override
    public HeatedWords.VP getContract() {
        return new HeatedWords.VP() {
            @Override
            public void requestHeatedWordsVP() {
                try {
                    mModel.getContract().requestHeatedWordsM();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void responseHeatedWordsResult(List<Map<String,Object>> heatedWordsList) {
                mView.getContract().responseHeatedWordsResult(heatedWordsList);
            }
        };
    }
}
