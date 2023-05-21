package com.hongyongfeng.wanandroid.module.signinorup.login.model;

import static com.hongyongfeng.wanandroid.module.signinorup.register.model.RegisterFragmentModel.ERROR_CODE;
import static com.hongyongfeng.wanandroid.module.signinorup.register.model.RegisterFragmentModel.ERROR_MSG;
import static com.hongyongfeng.wanandroid.module.signinorup.register.model.RegisterFragmentModel.LOGIN;
import static com.hongyongfeng.wanandroid.module.signinorup.register.model.RegisterFragmentModel.NULL;
import static com.hongyongfeng.wanandroid.util.Constant.DOMAIN_URL;
import static com.hongyongfeng.wanandroid.util.Constant.ONE;
import static com.hongyongfeng.wanandroid.util.Constant.ZERO;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.module.signinorup.login.interfaces.HttpCookiesListener;
import com.hongyongfeng.wanandroid.module.signinorup.login.interfaces.ILogin;
import com.hongyongfeng.wanandroid.module.signinorup.login.presenter.LoginFragmentPresenter;
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
public class LoginFragmentModel extends BaseFragmentModel<LoginFragmentPresenter, ILogin.Model> {
    public LoginFragmentModel(LoginFragmentPresenter mPresenter) {
        super(mPresenter);
    }
    private static final String LOGIN_URL =DOMAIN_URL+ Constant.LOGIN_URL;
    public static final String COOKIE_PREF = "cookies_prefs";

    @Override
    public ILogin.Model getContract() {
        return (name, pwd) -> {
            //请求服务器登录接口，然后拿到数据
            HttpUtil.postLoginRequest(new HttpCookiesListener() {
                @Override
                public void onFinish(List<HttpCookie> httpCookieList) {
                    StringBuilder builder = new StringBuilder();
                    if (!httpCookieList.isEmpty()){
                        //将返回的cookies拼接成键值对的形式
                        for (int i = ZERO; i < httpCookieList.size(); i++) {
                            HttpCookie cookie = httpCookieList.get(i);
                            builder.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
                        }
                        int last = builder.lastIndexOf(";");
                        if (builder.length() - ONE == last) {
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
            },new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    try {
                        JSONObject object=new JSONObject(response);
                        String errorMsg=object.getString(ERROR_MSG);
                        int errorCode=object.getInt(ERROR_CODE);
                        if (errorCode==ZERO){
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
            }, LOGIN_URL, name, pwd);
        };
    }
    /**
     * 保存cookie到本地，这里我们分别为该url和host设置相同的cookie，其中host可选
     * 这样能使得该cookie的应用范围更广
     */
    private void saveCookie(String url, String cookies) {
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException(NULL);
        } else {
            editor.putString(url, cookies);
        }
        editor.apply();
    }

    /**
     * 清除本地Cookie
     *
     * @param context Context
     */
    public static void clearCookie(Context context) {
        SharedPreferences sp = context.getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }
}
