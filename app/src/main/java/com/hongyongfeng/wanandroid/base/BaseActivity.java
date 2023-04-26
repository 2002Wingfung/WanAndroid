package com.hongyongfeng.wanandroid.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity<P extends BasePresenter,CONTRACT> extends AppCompatActivity implements View.OnClickListener {

    public abstract CONTRACT getContract();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        initView();
        initListener();
        initData();
        mPresenter=getPresenterInstance();
        mPresenter.bindView(this);
    }

    public P mPresenter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroy();
    }

    public abstract void initListener();
    public abstract void initData();
    public abstract void destroy();
    public abstract int getContentViewId();

    public abstract P getPresenterInstance();
    //处理 ，响应错误信息
    public abstract <ERROR extends Object> void responseError(ERROR error, Throwable throwable);
    public abstract void initView();
}
