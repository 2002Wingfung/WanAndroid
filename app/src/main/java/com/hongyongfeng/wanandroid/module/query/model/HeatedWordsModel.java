package com.hongyongfeng.wanandroid.module.query.model;

import static com.hongyongfeng.wanandroid.util.Constant.DOMAIN_URL;
import static com.hongyongfeng.wanandroid.util.Constant.JSON_URL;

import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
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

            @Override
            public void requestQueryM(String key, int page) throws Exception {
                String query=DOMAIN_URL+"article/query/"+page+JSON_URL;
                HttpUtil.postQueryRequest(query,key, new HttpCallbackListener() {

                    @Override
                    public void onFinish(String response) {
                        List<ArticleBean> articleBeanList = HttpUtil.parseJSONWithJSONObject(response, ArticleBean.class);
                        if (articleBeanList.size()!=0){
                            mPresenter.getContract().responseQueryResult(articleBeanList);
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        };
    }
}
