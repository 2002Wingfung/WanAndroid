package com.hongyongfeng.wanandroid.module.query.model;

import static com.hongyongfeng.wanandroid.util.Constant.DOMAIN_URL;
import static com.hongyongfeng.wanandroid.util.Constant.JSON_URL;
import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.module.query.interfaces.HeatedWords;
import com.hongyongfeng.wanandroid.module.query.presenter.HeatedWordsPresenter;
import com.hongyongfeng.wanandroid.util.HttpUtil;
import java.util.List;
import java.util.Map;

/**
 * @author Wingfung Hung
 */
public class HeatedWordsModel extends BaseFragmentModel<HeatedWordsPresenter, HeatedWords.Model> {
    public HeatedWordsModel(HeatedWordsPresenter mPresenter) {
        super(mPresenter);
    }
    private static final String HOT_KEY_URL=DOMAIN_URL+"/hotkey"+JSON_URL;
    List<Map<String,Object>> heatedWordsListMap;
    @Override
    public HeatedWords.Model getContract() {
        return () -> HttpUtil.sendHttpRequest(HOT_KEY_URL, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                heatedWordsListMap =HttpUtil.parseJsonWithJsonObject(response);
                mPresenter.getContract().responseHeatedWordsResult(heatedWordsListMap);
            }

            @Override
            public void onError(Exception e) {

            }
        },null);
    }
}
