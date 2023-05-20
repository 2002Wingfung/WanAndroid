package com.hongyongfeng.wanandroid.module.webview.presenter;

import com.hongyongfeng.wanandroid.base.BasePresenter;
import com.hongyongfeng.wanandroid.module.webview.interfaces.WebView;
import com.hongyongfeng.wanandroid.module.webview.model.WebViewModel;
import com.hongyongfeng.wanandroid.module.webview.view.WebViewActivity;

/**
 * @author Wingfung Hung
 */
public class WebViewPresenter extends BasePresenter<WebViewModel, WebViewActivity, WebView.Vp> {
    @Override
    public WebViewModel getModelInstance() {
        return new WebViewModel(this);
    }

    @Override
    public WebView.Vp getContract() {
        return null;
    }
}
