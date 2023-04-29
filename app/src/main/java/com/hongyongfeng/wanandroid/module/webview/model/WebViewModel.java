package com.hongyongfeng.wanandroid.module.webview.model;

import com.hongyongfeng.wanandroid.base.BaseModel;
import com.hongyongfeng.wanandroid.module.webview.interfaces.WebView;
import com.hongyongfeng.wanandroid.module.webview.presenter.WebViewPresenter;

public class WebViewModel extends BaseModel<WebViewPresenter, WebView.M> {
    @Override
    public WebView.M getContract() {
        return new WebView.M() {
            @Override
            public void requestWebViewM(String name, String pwd) throws Exception {

            }
        };
    }
    public WebViewModel(WebViewPresenter mPresenter) {
        super(mPresenter);
    }
}
