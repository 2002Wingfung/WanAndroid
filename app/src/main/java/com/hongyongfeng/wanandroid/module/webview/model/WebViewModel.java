package com.hongyongfeng.wanandroid.module.webview.model;

import com.hongyongfeng.wanandroid.base.BaseModel;
import com.hongyongfeng.wanandroid.module.webview.interfaces.WebView;
import com.hongyongfeng.wanandroid.module.webview.presenter.WebViewPresenter;

/**
 * @author Wingfung Hung
 */
public class WebViewModel extends BaseModel<WebViewPresenter, WebView.Model> {
    @Override
    public WebView.Model getContract() {
        return null;
    }
    public WebViewModel(WebViewPresenter mPresenter) {
        super(mPresenter);
    }
}
