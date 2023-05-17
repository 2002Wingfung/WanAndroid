package com.hongyongfeng.wanandroid.module.knowledge.model;

import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.module.knowledge.interfaces.KnowledgeFragmentInterface;
import com.hongyongfeng.wanandroid.module.knowledge.presenter.KnowledgeFragmentPresenter;
import com.hongyongfeng.wanandroid.util.HttpUtil;

import java.util.List;
import java.util.Map;

public class KnowledgeFragmentModel extends BaseFragmentModel<KnowledgeFragmentPresenter, KnowledgeFragmentInterface.M> {
    public KnowledgeFragmentModel(KnowledgeFragmentPresenter mPresenter) {
        super(mPresenter);
    }
    private static final String Knowledge_Tree_URL="https://www.wanandroid.com/tree/json";
    List<Map<String,Object>> treeMapList;

    @Override
    public KnowledgeFragmentInterface.M getContract() {
        return new KnowledgeFragmentInterface.M() {
            @Override
            public void requestTitleM() throws Exception {
                //请求服务器登录接口，然后拿到

                HttpUtil.sendHttpRequest(Knowledge_Tree_URL, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        treeMapList =HttpUtil.parseJsonWithJsonObject(response);

                        if (treeMapList.size()!=0){
                            mPresenter.getContract().responseTitleResult(treeMapList);
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                },null);
            }
        };
    }
}
