package com.hongyongfeng.wanandroid.module.signinorup.login.presenter;


import com.hongyongfeng.wanandroid.base.BaseFragmentPresenter;
import com.hongyongfeng.wanandroid.module.signinorup.login.interfaces.ILogin;
import com.hongyongfeng.wanandroid.module.signinorup.login.model.LoginFragmentModel;
import com.hongyongfeng.wanandroid.module.signinorup.login.view.fragment.LoginFragment;

/**
 * @author Wingfung Hung
 */
public class LoginFragmentPresenter extends BaseFragmentPresenter<LoginFragmentModel, LoginFragment, ILogin.Vp> {
    @Override
    public LoginFragmentModel getModelInstance() {
        return new LoginFragmentModel(this);
    }

    @Override
    public ILogin.Vp getContract() {
        return new ILogin.Vp() {
            @Override
            public void requestLoginVp(String name, String pwd) {
                //核验请求的信息，进行逻辑处理
                //调用model层
                try {
                    mModel.getContract().requestLoginM(name,pwd);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void responseLoginResult(boolean loginStatusResult) {
                mView.getContract().responseLoginResult(loginStatusResult);
            }

            @Override
            public void error(String error) {
                mView.getContract().error(error);
            }
        };
    }
}
