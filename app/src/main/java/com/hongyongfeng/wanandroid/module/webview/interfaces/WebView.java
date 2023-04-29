package com.hongyongfeng.wanandroid.module.webview.interfaces;

public interface WebView {
    interface M{
        void requestWebViewM(String name, String pwd)throws Exception;
    }
    interface VP{
        void requestWebViewVP(String name, String pwd);
        void responseWebViewResult(boolean loginStatusResult);
    }
}
