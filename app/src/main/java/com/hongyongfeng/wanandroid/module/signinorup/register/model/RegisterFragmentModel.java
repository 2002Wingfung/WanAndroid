package com.hongyongfeng.wanandroid.module.signinorup.register.model;

import static com.hongyongfeng.wanandroid.module.signinorup.login.model.LoginFragmentModel.COOKIE_PREF;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.module.signinorup.login.interfaces.HttpCookiesListener;
import com.hongyongfeng.wanandroid.module.signinorup.register.interfaces.RegisterInterface;
import com.hongyongfeng.wanandroid.module.signinorup.register.presenter.RegisterFragmentPresenter;
import com.hongyongfeng.wanandroid.util.HttpUtil;
import com.hongyongfeng.wanandroid.util.MyApplication;

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
                        StringBuilder builder = new StringBuilder();
                        if (!httpCookieList.isEmpty()){
                            for (int i = 0; i < httpCookieList.size(); i++) {
                                HttpCookie cookie = httpCookieList.get(i);
                                builder.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
                            }
                            int last = builder.lastIndexOf(";");
                            if (builder.length() - 1 == last) {
                                builder.deleteCharAt(last);
                            }
                        }
//                        System.out.println(builder);
                        saveCookie("login",builder.toString());
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
    private void saveCookie(String url, String cookies) {
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException("url is null.");
        } else {
            editor.putString(url, cookies);
        }

//        if (!TextUtils.isEmpty(domain)) {
//            editor.putString(domain, cookies);
//        }
        editor.apply();
    }
}
