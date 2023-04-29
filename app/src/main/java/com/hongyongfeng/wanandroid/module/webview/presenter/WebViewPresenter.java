package com.hongyongfeng.wanandroid.module.webview.presenter;

import com.hongyongfeng.wanandroid.base.BasePresenter;
import com.hongyongfeng.wanandroid.module.webview.interfaces.WebView;
import com.hongyongfeng.wanandroid.module.webview.model.WebViewModel;
import com.hongyongfeng.wanandroid.module.webview.view.WebViewActivity;

public class WebViewPresenter extends BasePresenter<WebViewModel, WebViewActivity, WebView.VP> {
    @Override
    public WebViewModel getModelInstance() {
        return new WebViewModel(this);
    }

    @Override
    public WebView.VP getContract() {
        return new WebView.VP() {
            @Override
            public void requestWebViewVP(String name, String pwd) {

            }

            @Override
            public void responseWebViewResult(boolean loginStatusResult) {

            }
        };
    }
}
