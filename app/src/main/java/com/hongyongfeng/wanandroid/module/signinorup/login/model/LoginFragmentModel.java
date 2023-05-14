package com.hongyongfeng.wanandroid.module.signinorup.login.model;

import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.BaseModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.module.signinorup.login.interfaces.ILogin;
import com.hongyongfeng.wanandroid.module.signinorup.login.presenter.LoginFragmentPresenter;
import com.hongyongfeng.wanandroid.module.signinorup.login.presenter.LoginPresenter;
import com.hongyongfeng.wanandroid.module.signinorup.login.view.fragment.LoginFragment;
import com.hongyongfeng.wanandroid.util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginFragmentModel extends BaseFragmentModel<LoginFragmentPresenter, ILogin.M> {
    public LoginFragmentModel(LoginFragmentPresenter mPresenter) {
        super(mPresenter);
    }
    private static final String Login_URL="https://www.wanandroid.com/user/login";

    @Override
    public ILogin.M getContract() {
        return new ILogin.M() {
            @Override
            public void requestLoginM(String name, String pwd) throws Exception {
                //请求服务器登录接口，然后拿到

                HttpUtil.postLoginRequest(new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        System.out.println(response);
                        try {
                            JSONObject object=new JSONObject(response);
                            String errorMsg=object.getString("errorMsg");
                            int errorCode=object.getInt("errorCode");
                            if (errorCode==0){
                                mPresenter.getContract().responseLoginResult(true);
                            }else {
                                mPresenter.getContract().error(errorMsg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                },Login_URL, name, pwd);

                //mPresenter.getContract().responseLoginResult("wbc".equals(name) && "123".equals(pwd));
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
