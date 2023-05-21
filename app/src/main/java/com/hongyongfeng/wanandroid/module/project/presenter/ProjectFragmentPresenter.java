package com.hongyongfeng.wanandroid.module.project.presenter;

import com.hongyongfeng.wanandroid.base.BaseFragmentPresenter;
import com.hongyongfeng.wanandroid.module.project.interfaces.ProjectFragmentInterface;
import com.hongyongfeng.wanandroid.module.project.model.ProjectFragmentModel;
import com.hongyongfeng.wanandroid.module.project.view.fragment.ProjectFragment;
import java.util.List;
import java.util.Map;

/**
 * @author Wingfung Hung
 */
public class ProjectFragmentPresenter extends BaseFragmentPresenter<ProjectFragmentModel, ProjectFragment, ProjectFragmentInterface.Vp> {
    @Override
    public ProjectFragmentModel getModelInstance() {
        return new ProjectFragmentModel(this);
    }

    @Override
    public ProjectFragmentInterface.Vp getContract() {
        return new ProjectFragmentInterface.Vp() {
            @Override
            public void requestTitleVp() {
                //调用model层
                try {
                    mModel.getContract().requestTitleM();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void responseTitleResult(List<Map<String,Object>> titleMapList) {
                mView.getContract().responseTitleResult(titleMapList);
            }
        };
    }
}
