package com.hongyongfeng.wanandroid.util;

import static android.content.Context.MODE_PRIVATE;
import static com.hongyongfeng.wanandroid.module.signinorup.login.model.LoginFragmentModel.COOKIE_PREF;
import android.content.SharedPreferences;

/**
 * 获取本地的持久化cookies
 * @author Wingfung Hung
 */
public class GetCookies {
    public static String get(){
        SharedPreferences preferences= MyApplication.getContext().getSharedPreferences(COOKIE_PREF,MODE_PRIVATE);
        return preferences.getString("login","");
    }
}
