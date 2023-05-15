package com.hongyongfeng.wanandroid.module.signinorup.login.interfaces;

import java.net.HttpCookie;
import java.util.List;

public interface HttpCookiesListener {
    void onFinish(List<HttpCookie> httpCookieList);
    void error(Exception e);
}
