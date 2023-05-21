package com.hongyongfeng.wanandroid.module.signinorup.login.interfaces;

import java.net.HttpCookie;
import java.util.List;

/**
 * cookies接收回调接口
 * @author Wingfung Hung
 */
public interface HttpCookiesListener {
    /**
     * 接收cookies成功接口处
     * @param httpCookieList HttpCookie集合
     */
    void onFinish(List<HttpCookie> httpCookieList);

    /**
     * 错误信息
     * @param e 异常
     */
    void error(Exception e);
}
