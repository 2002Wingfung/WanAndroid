package com.hongyongfeng.wanandroid.module.signinorup.register.model;

import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.module.signinorup.login.interfaces.HttpCookiesListener;
import com.hongyongfeng.wanandroid.module.signinorup.register.interfaces.RegisterInterface;
import com.hongyongfeng.wanandroid.module.signinorup.register.presenter.RegisterFragmentPresenter;
import com.hongyongfeng.wanandroid.util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpCookie;
import java.util.List;

public class RegisterFragmentModel extends BaseFragmentModel<RegisterFragmentPresenter, RegisterInterface.M> {
    public RegisterFragmentModel(RegisterFragmentPresenter mPresenter) {
        super(mPresenter);
    }
    private static final String REGISTER_URL ="https://www.wanandroid.com/user/register";

    @Override
    public RegisterInterface.M getContract() {
        return new RegisterInterface.M() {
            @Override
            public void requestRegisterM(String name, String pwd) throws Exception {
                HttpUtil.postLoginRequest(new HttpCookiesListener() {
                    @Override
                    public void onFinish(List<HttpCookie> httpCookieList) {

                    }

                    @Override
                    public void error(Exception e) {

                    }
                },new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        System.out.println(response);
                        try {
                            JSONObject object=new JSONObject(response);
                            String errorMsg=object.getString("errorMsg");
                            int errorCode=object.getInt("errorCode");
                            if (errorCode==0){
                                mPresenter.getContract().responseRegisterResult(true);
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
                }, REGISTER_URL, name, pwd, pwd);

                //mPresenter.getContract().responseLoginResult("wbc".equals(name) && "123".equals(pwd));
            }
        };
    }
}
