package com.hongyongfeng.wanandroid.module.knowledge.presenter;

import com.hongyongfeng.wanandroid.base.BaseFragmentPresenter;
import com.hongyongfeng.wanandroid.module.knowledge.interfaces.KnowledgeFragmentInterface;
import com.hongyongfeng.wanandroid.module.knowledge.model.KnowledgeFragmentModel;
import com.hongyongfeng.wanandroid.module.knowledge.view.fragment.KnowledgeFragment;
import java.util.List;
import java.util.Map;

/**
 * @author Wingfung Hung
 */
public class KnowledgeFragmentPresenter extends BaseFragmentPresenter<KnowledgeFragmentModel, KnowledgeFragment, KnowledgeFragmentInterface.Vp> {
    @Override
    public KnowledgeFragmentModel getModelInstance() {
        return new KnowledgeFragmentModel(this);
    }

    @Override
    public KnowledgeFragmentInterface.Vp getContract() {
        return new KnowledgeFragmentInterface.Vp() {
            @Override
            public void requestTitleVp() {
                try {
                    mModel.getContract().requestTitleM();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void responseTitleResult(List<Map<String,Object>> treeList) {
                mView.getContract().responseTitleResult(treeList);
            }
        };
    }
}
