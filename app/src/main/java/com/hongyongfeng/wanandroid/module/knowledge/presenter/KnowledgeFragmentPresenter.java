package com.hongyongfeng.wanandroid.module.knowledge.presenter;


import com.hongyongfeng.wanandroid.base.BaseFragmentPresenter;
import com.hongyongfeng.wanandroid.module.knowledge.interfaces.KnowledgeFragmentInterface;
import com.hongyongfeng.wanandroid.module.knowledge.model.KnowledgeFragmentModel;
import com.hongyongfeng.wanandroid.module.knowledge.view.fragment.KnowledgeFragment;

import java.util.List;
import java.util.Map;

public class KnowledgeFragmentPresenter extends BaseFragmentPresenter<KnowledgeFragmentModel, KnowledgeFragment, KnowledgeFragmentInterface.VP> {
    @Override
    public KnowledgeFragmentModel getModelInstance() {
        return new KnowledgeFragmentModel(this);
    }

    @Override
    public KnowledgeFragmentInterface.VP getContract() {
        return new KnowledgeFragmentInterface.VP() {
            @Override
            public void requestTitleVP() {
                //核验请求的信息，进行逻辑处理
                //调用model层
                try {
//                    mModel.requestLogin(name,pwd);
                    mModel.getContract().requestLoginM();

                } catch (Exception e) {
                    e.printStackTrace();
                    //异常的处理
                    //保存到日志
                    //一系列的异常处理
                    //...
                }
            }

            @Override
            public void responseTitleResult(List<Map<String,Object>> treeList) {
                //真实开发过程中，是要解析数据的
                mView.getContract().responseTitleResult(treeList);
            }
        };
    }
}
