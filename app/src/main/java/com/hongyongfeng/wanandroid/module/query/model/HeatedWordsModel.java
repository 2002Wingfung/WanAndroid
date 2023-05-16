package com.hongyongfeng.wanandroid.module.query.model;

import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.module.query.interfaces.HeatedWords;
import com.hongyongfeng.wanandroid.module.query.presenter.HeatedWordsPresenter;
import com.hongyongfeng.wanandroid.util.HttpUtil;

import java.util.List;
import java.util.Map;

public class HeatedWordsModel extends BaseFragmentModel<HeatedWordsPresenter, HeatedWords.M> {
    public HeatedWordsModel(HeatedWordsPresenter mPresenter) {
        super(mPresenter);
    }
    private static final String HOT_KEY_URL="https://www.wanandroid.com//hotkey/json";
    List<Map<String,Object>> heatedWordsListMap;
    @Override
    public HeatedWords.M getContract() {
        return new HeatedWords.M() {
            @Override
            public void requestHeatedWordsM() throws Exception {

                HttpUtil.sendHttpRequest(HOT_KEY_URL, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        heatedWordsListMap =HttpUtil.parseJsonWithJSONObject(response);
                        mPresenter.getContract().responseHeatedWordsResult(heatedWordsListMap);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                },null);


            }
        };
    }
}
