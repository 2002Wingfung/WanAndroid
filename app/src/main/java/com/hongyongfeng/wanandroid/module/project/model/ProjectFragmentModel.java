
package com.hongyongfeng.wanandroid.module.project.model;

import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.module.home.presenter.HomeFragmentPresenter;
import com.hongyongfeng.wanandroid.module.project.interfaces.ProjectFragmentInterface;
import com.hongyongfeng.wanandroid.module.project.presenter.ProjectFragmentPresenter;

public class ProjectFragmentModel extends BaseFragmentModel<ProjectFragmentPresenter, ProjectFragmentInterface.M> {
    public ProjectFragmentModel(ProjectFragmentPresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public ProjectFragmentInterface.M getContract() {
        return new ProjectFragmentInterface.M() {
            @Override
            public void requestLoginM(String name, String pwd) throws Exception {
                //请求服务器登录接口，然后拿到
                //...
//                mPresenter.responseLoginResult("wbc".equals(name) && "123".equals(pwd));
//                mPresenter.getContract().responseLoginResult("wbc".equals(name) && "123".equals(pwd));
            }
        };
    }

//    @Override
//    public void requestLogin(String name, String pwd) throws Exception {
//
//        //请求服务器登录接口，然后拿到
//        //...
//        mPresenter.responseLoginResult("wbc".equals(name) && "123".equals(pwd));
//    }
}
