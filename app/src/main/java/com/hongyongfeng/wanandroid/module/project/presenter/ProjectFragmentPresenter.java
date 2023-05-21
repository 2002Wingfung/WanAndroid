package com.hongyongfeng.wanandroid.module.project.presenter;


import com.hongyongfeng.wanandroid.base.BaseFragmentPresenter;
import com.hongyongfeng.wanandroid.module.project.interfaces.ProjectFragmentInterface;
import com.hongyongfeng.wanandroid.module.project.model.ProjectFragmentModel;
import com.hongyongfeng.wanandroid.module.project.view.fragment.ProjectFragment;

import java.util.List;
import java.util.Map;

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
                //核验请求的信息，进行逻辑处理
                //调用model层
                try {
//                    mModel.requestLogin(name,pwd);
                    mModel.getContract().requestTitleM();

                } catch (Exception e) {
                    e.printStackTrace();
                    //异常的处理
                    //保存到日志
                    //一系列的异常处理
                    //...
                }
            }

            @Override
            public void responseTitleResult(List<Map<String,Object>> titleMapList) {
                //真实开发过程中，是要解析数据的
                mView.getContract().responseTitleResult(titleMapList);
            }
        };
    }
}
