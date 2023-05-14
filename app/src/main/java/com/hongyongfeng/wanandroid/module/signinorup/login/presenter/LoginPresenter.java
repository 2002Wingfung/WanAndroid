package com.hongyongfeng.wanandroid.module.signinorup.login.presenter;


import com.hongyongfeng.wanandroid.base.BasePresenter;
import com.hongyongfeng.wanandroid.module.signinorup.login.interfaces.ILogin;
import com.hongyongfeng.wanandroid.module.signinorup.login.model.LoginModel;
import com.hongyongfeng.wanandroid.module.signinorup.login.view.LoginActivity;

public class LoginPresenter extends BasePresenter<LoginModel, LoginActivity,ILogin.VP> {
    @Override
    public LoginModel getModelInstance() {
        return new LoginModel(this);
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

            }
        };
    }
}
