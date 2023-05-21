package com.hongyongfeng.wanandroid.module.signinorup.register.model;

import static com.hongyongfeng.wanandroid.module.signinorup.login.model.LoginFragmentModel.COOKIE_PREF;
import static com.hongyongfeng.wanandroid.util.Constant.DOMAIN_URL;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.module.signinorup.login.interfaces.HttpCookiesListener;
import com.hongyongfeng.wanandroid.module.signinorup.register.interfaces.RegisterInterface;
import com.hongyongfeng.wanandroid.module.signinorup.register.presenter.RegisterFragmentPresenter;
import com.hongyongfeng.wanandroid.util.Constant;
import com.hongyongfeng.wanandroid.util.HttpUtil;
import com.hongyongfeng.wanandroid.util.MyApplication;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpCookie;
import java.util.List;

/**
 * @author Wingfung Hung
 */
public class RegisterFragmentModel extends BaseFragmentModel<RegisterFragmentPresenter, RegisterInterface.Model> {
    public RegisterFragmentModel(RegisterFragmentPresenter mPresenter) {
        super(mPresenter);
    }
    private static final String REGISTER_URL =DOMAIN_URL+ Constant.REGISTER_URL;
    private static final String LOGIN="login";
    private static final String NULL="url is null.";
    private static final String ERROR_MSG="errorMsg";
    private static final String ERROR_CODE="errorCode";


    @Override
    public RegisterInterface.Model getContract() {
        return (name, pwd) -> HttpUtil.postLoginRequest(new HttpCookiesListener() {
            @Override
            public void onFinish(List<HttpCookie> httpCookieList) {
                StringBuilder builder = new StringBuilder();
                if (!httpCookieList.isEmpty()){
                    //将返回的cookies拼接成键值对的形式
                    for (int i = 0; i < httpCookieList.size(); i++) {
                        HttpCookie cookie = httpCookieList.get(i);
                        builder.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
                    }
                    int last = builder.lastIndexOf(";");
                    if (builder.length() - 1 == last) {
                        //删去cookies中的最后一个分号
                        builder.deleteCharAt(last);
                    }
                }
                //保存cookies
                saveCookie(LOGIN,builder.toString());
            }

            @Override
            public void error(Exception e) {

            }
        }, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    String errorMsg=object.getString(ERROR_MSG);
                    int errorCode=object.getInt(ERROR_CODE);
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
    }
    private void saveCookie(String url, String cookies) {
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        //将cookies保存到SharedPreferences中
        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException(NULL);
        } else {
            editor.putString(url, cookies);
        }
        editor.apply();
    }
}
