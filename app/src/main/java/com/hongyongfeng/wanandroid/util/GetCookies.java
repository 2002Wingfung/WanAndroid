package com.hongyongfeng.wanandroid.util;

import static android.content.Context.MODE_PRIVATE;
import static com.hongyongfeng.wanandroid.module.signinorup.login.model.LoginFragmentModel.COOKIE_PREF;

import android.content.SharedPreferences;

public class GetCookies {
    public static String get(){
        SharedPreferences preferences= MyApplication.getContext().getSharedPreferences(COOKIE_PREF,MODE_PRIVATE);
        //        //System.out.println(cookies);
//        int first=cookies.indexOf(";")+1;
//        int last=cookies.lastIndexOf(";");
//        cookies=cookies.substring(first,last);
//        first=cookies.indexOf("=")+1;
        return preferences.getString("login","");
    }
}
