package com.hongyongfeng.wanandroid.deprecated;

import com.hongyongfeng.wanandroid.base.BaseModel;
import com.hongyongfeng.wanandroid.module.signinorup.login.interfaces.ILogin;

/**
 * @author Wingfung Hung
 */
@Deprecated
public class LoginModel extends BaseModel<LoginPresenter, ILogin.Model>{
    public LoginModel(LoginPresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public ILogin.Model getContract() {
        return new ILogin.Model() {
            @Override
            public void requestLoginM(String name, String pwd) throws Exception {
                //请求服务器登录接口，然后拿到
                //...
//                mPresenter.responseLoginResult("wbc".equals(name) && "123".equals(pwd));
                mPresenter.getContract().responseLoginResult("wbc".equals(name) && "123".equals(pwd));
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
