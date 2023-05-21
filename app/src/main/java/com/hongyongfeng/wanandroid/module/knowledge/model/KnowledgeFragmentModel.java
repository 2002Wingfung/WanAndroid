package com.hongyongfeng.wanandroid.module.knowledge.model;

import static com.hongyongfeng.wanandroid.util.Constant.DOMAIN_URL;
import static com.hongyongfeng.wanandroid.util.Constant.JSON_URL;
import static com.hongyongfeng.wanandroid.util.Constant.ZERO;
import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.module.knowledge.interfaces.KnowledgeFragmentInterface;
import com.hongyongfeng.wanandroid.module.knowledge.presenter.KnowledgeFragmentPresenter;
import com.hongyongfeng.wanandroid.util.HttpUtil;
import java.util.List;
import java.util.Map;

/**
 * @author Wingfung Hung
 */
public class KnowledgeFragmentModel extends BaseFragmentModel<KnowledgeFragmentPresenter, KnowledgeFragmentInterface.Model> {
    public KnowledgeFragmentModel(KnowledgeFragmentPresenter mPresenter) {
        super(mPresenter);
    }
    private static final String KNOWLEDGE_TREE_URL =DOMAIN_URL+"tree"+JSON_URL;
    List<Map<String,Object>> treeMapList;

    @Override
    public KnowledgeFragmentInterface.Model getContract() {
        return () -> {
            HttpUtil.sendHttpRequest(KNOWLEDGE_TREE_URL, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    treeMapList =HttpUtil.parseJsonWithJsonObject(response);
                    if (treeMapList.size()!=ZERO){
                        mPresenter.getContract().responseTitleResult(treeMapList);
                    }
                }
                @Override
                public void onError(Exception e) {
                }
            },null);
        };
    }
}
