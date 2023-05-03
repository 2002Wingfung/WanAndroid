package com.hongyongfeng.wanandroid.module.signinorup.register.model;

import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.module.signinorup.login.interfaces.ILogin;
import com.hongyongfeng.wanandroid.module.signinorup.login.presenter.LoginFragmentPresenter;
import com.hongyongfeng.wanandroid.module.signinorup.register.interfaces.RegisterInterface;
import com.hongyongfeng.wanandroid.module.signinorup.register.presenter.RegisterFragmentPresenter;

public class RegisterFragmentModel extends BaseFragmentModel<RegisterFragmentPresenter, RegisterInterface.M> {
    public RegisterFragmentModel(RegisterFragmentPresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public RegisterInterface.M getContract() {
        return new RegisterInterface.M() {
            @Override
            public void requestRegisterM(String name, String pwd) throws Exception {
                //请求服务器登录接口，然后拿到
                //...
//                mPresenter.responseLoginResult("wbc".equals(name) && "123".equals(pwd));
                mPresenter.getContract().responseRegisterResult("wbc".equals(name) && "123".equals(pwd));
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
