package com.hongyongfeng.wanandroid.module.knowledge.model;

import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.module.home.interfaces.HomeFragmentInterface;
import com.hongyongfeng.wanandroid.module.home.presenter.HomeFragmentPresenter;
import com.hongyongfeng.wanandroid.module.knowledge.interfaces.KnowledgeFragmentInterface;
import com.hongyongfeng.wanandroid.module.knowledge.presenter.KnowledgeFragmentPresenter;
import com.hongyongfeng.wanandroid.util.HttpUtil;

public class KnowledgeFragmentModel extends BaseFragmentModel<KnowledgeFragmentPresenter, KnowledgeFragmentInterface.M> {
    public KnowledgeFragmentModel(KnowledgeFragmentPresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public KnowledgeFragmentInterface.M getContract() {
        return new KnowledgeFragmentInterface.M() {
            @Override
            public void requestLoginM() throws Exception {
                //请求服务器登录接口，然后拿到

                //HttpUtil.
            }
        };
    }
}
