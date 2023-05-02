package com.hongyongfeng.wanandroid.module.home.model;

import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.module.home.interfaces.HomeFragmentInterface;
import com.hongyongfeng.wanandroid.module.home.presenter.HomeFragmentPresenter;

public class HomeFragmentModel extends BaseFragmentModel<HomeFragmentPresenter, HomeFragmentInterface.M> {
    public HomeFragmentModel(HomeFragmentPresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public HomeFragmentInterface.M getContract() {
        return new HomeFragmentInterface.M() {
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
