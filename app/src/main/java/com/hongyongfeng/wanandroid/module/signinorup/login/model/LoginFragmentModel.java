package com.hongyongfeng.wanandroid.module.signinorup.login.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.BaseModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.module.signinorup.login.interfaces.HttpCookiesListener;
import com.hongyongfeng.wanandroid.module.signinorup.login.interfaces.ILogin;
import com.hongyongfeng.wanandroid.module.signinorup.login.presenter.LoginFragmentPresenter;
import com.hongyongfeng.wanandroid.module.signinorup.login.presenter.LoginPresenter;
import com.hongyongfeng.wanandroid.module.signinorup.login.view.fragment.LoginFragment;
import com.hongyongfeng.wanandroid.util.HttpUtil;
import com.hongyongfeng.wanandroid.util.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpCookie;
import java.util.List;

public class LoginFragmentModel extends BaseFragmentModel<LoginFragmentPresenter, ILogin.M> {
    public LoginFragmentModel(LoginFragmentPresenter mPresenter) {
        super(mPresenter);
    }
    private static final String Login_URL="https://www.wanandroid.com/user/login";
    private static final String COOKIE_PREF = "cookies_prefs";

    @Override
    public ILogin.M getContract() {
        return new ILogin.M() {
            @Override
            public void requestLoginM(String name, String pwd) throws Exception {
                //请求服务器登录接口，然后拿到

                HttpUtil.postLoginRequest(new HttpCookiesListener() {
                    @Override
                    public void onFinish(List<HttpCookie> httpCookieList) {
                        System.out.println(httpCookieList);
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
                        System.out.println(builder);
                        saveCookie("login",builder.toString());

                        //最后将拼接后的字符串传回到view层，view层就可以根据这个字符类型的cookie进行使用了
                        //mView.responseGettingCookie(builder.toString());
                    }

                    @Override
                    public void error(Exception e) {

                    }
                },new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        //System.out.println(response);
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
    /**
     * 保存cookie到本地，这里我们分别为该url和host设置相同的cookie，其中host可选
     * 这样能使得该cookie的应用范围更广
     */
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

    /**
     * 清除本地Cookie
     *
     * @param context Context
     */
    public static void clearCookie(Context context) {
        SharedPreferences sp = context.getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }
//    @Override
//    public void requestLogin(String name, String pwd) throws Exception {
//
//        //请求服务器登录接口，然后拿到
//        //...
//        mPresenter.responseLoginResult("wbc".equals(name) && "123".equals(pwd));
//    }
}
