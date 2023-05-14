package com.hongyongfeng.wanandroid.module.signinorup.login.presenter;


import com.hongyongfeng.wanandroid.base.BaseFragmentPresenter;
import com.hongyongfeng.wanandroid.module.signinorup.login.interfaces.ILogin;
import com.hongyongfeng.wanandroid.module.signinorup.login.model.LoginFragmentModel;

import com.hongyongfeng.wanandroid.module.signinorup.login.view.fragment.LoginFragment;

public class LoginFragmentPresenter extends BaseFragmentPresenter<LoginFragmentModel, LoginFragment,ILogin.VP> {
    @Override
    public LoginFragmentModel getModelInstance() {
        return new LoginFragmentModel(this);
    }

    @Override
    public ILogin.VP getContract() {
        return new ILogin.VP() {
            @Override
            public void requestLoginVP(String name, String pwd) {
                //核验请求的信息，进行逻辑处理
                //调用model层
                try {
//                    mModel.requestLogin(name,pwd);
                    mModel.getContract().requestLoginM(name,pwd);

                } catch (Exception e) {
                    e.printStackTrace();
                    //异常的处理
                    //保存到日志
                    //一系列的异常处理
                    //...
                }
            }

            @Override
            public void responseLoginResult(boolean loginStatusResult) {
                //真实开发过程中，是要解析数据的
                mView.getContract().responseLoginResult(loginStatusResult);
            }

            @Override
            public void error(String error) {
                mView.getContract().error(error);
            }
        };
    }
}
