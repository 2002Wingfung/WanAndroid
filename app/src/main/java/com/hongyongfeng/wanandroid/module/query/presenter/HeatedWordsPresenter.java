package com.hongyongfeng.wanandroid.module.query.presenter;

import com.hongyongfeng.wanandroid.base.BaseFragmentPresenter;
import com.hongyongfeng.wanandroid.module.query.interfaces.HeatedWords;
import com.hongyongfeng.wanandroid.module.query.model.HeatedWordsModel;
import com.hongyongfeng.wanandroid.module.query.view.fragment.HeatedWordsFragment;
import java.util.List;
import java.util.Map;

/**
 * @author Wingfung Hung
 */
public class HeatedWordsPresenter extends BaseFragmentPresenter<HeatedWordsModel, HeatedWordsFragment, HeatedWords.Vp> {

    @Override
    public HeatedWordsModel getModelInstance() {
        return new HeatedWordsModel(this);
    }

    @Override
    public HeatedWords.Vp getContract() {
        return new HeatedWords.Vp() {
            @Override
            public void requestHeatedWordsVp() {
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
