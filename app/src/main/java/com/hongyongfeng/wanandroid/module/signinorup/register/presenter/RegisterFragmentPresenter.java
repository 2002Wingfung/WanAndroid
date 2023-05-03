package com.hongyongfeng.wanandroid.module.signinorup.register.presenter;


import com.hongyongfeng.wanandroid.base.BaseFragmentPresenter;
import com.hongyongfeng.wanandroid.module.signinorup.login.interfaces.ILogin;
import com.hongyongfeng.wanandroid.module.signinorup.login.model.LoginFragmentModel;
import com.hongyongfeng.wanandroid.module.signinorup.login.view.fragment.LoginFragment;
import com.hongyongfeng.wanandroid.module.signinorup.register.fragment.RegisterFragment;
import com.hongyongfeng.wanandroid.module.signinorup.register.interfaces.RegisterInterface;
import com.hongyongfeng.wanandroid.module.signinorup.register.model.RegisterFragmentModel;

public class RegisterFragmentPresenter extends BaseFragmentPresenter<RegisterFragmentModel, RegisterFragment, RegisterInterface.VP> {
    @Override
    public RegisterFragmentModel getModelInstance() {
        return new RegisterFragmentModel(this);
    }

    @Override
    public RegisterInterface.VP getContract() {
        return new RegisterInterface.VP() {
            @Override
            public void requestRegisterVP(String name, String pwd) {
                //核验请求的信息，进行逻辑处理
                //调用model层
                try {
//                    mModel.requestLogin(name,pwd);
                    mModel.getContract().requestRegisterM(name,pwd);

                } catch (Exception e) {
                    e.printStackTrace();
                    //异常的处理
                    //保存到日志
                    //一系列的异常处理
                    //...
                }
            }

            @Override
            public void responseRegisterResult(boolean loginStatusResult) {
                //真实开发过程中，是要解析数据的
                mView.getContract().responseRegisterResult(loginStatusResult);
            }
        };
    }
}
