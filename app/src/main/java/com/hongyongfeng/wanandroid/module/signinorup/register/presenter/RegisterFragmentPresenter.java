package com.hongyongfeng.wanandroid.module.signinorup.register.presenter;

import com.hongyongfeng.wanandroid.base.BaseFragmentPresenter;
import com.hongyongfeng.wanandroid.module.signinorup.register.fragment.RegisterFragment;
import com.hongyongfeng.wanandroid.module.signinorup.register.interfaces.RegisterInterface;
import com.hongyongfeng.wanandroid.module.signinorup.register.model.RegisterFragmentModel;

/**
 * @author Wingfung Hung
 */
public class RegisterFragmentPresenter extends BaseFragmentPresenter<RegisterFragmentModel, RegisterFragment, RegisterInterface.Vp> {
    @Override
    public RegisterFragmentModel getModelInstance() {
        return new RegisterFragmentModel(this);
    }

    @Override
    public RegisterInterface.Vp getContract() {
        return new RegisterInterface.Vp() {
            @Override
            public void requestRegisterVp(String name, String pwd) {
                //核验请求的信息，进行逻辑处理
                //调用model层
                try {
                    mModel.getContract().requestRegisterM(name,pwd);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void responseRegisterResult(boolean loginStatusResult) {
                mView.getContract().responseRegisterResult(loginStatusResult);
            }

            @Override
            public void error(String error) {
                mView.getContract().error(error);
            }
        };
    }
}
