package com.hongyongfeng.wanandroid.base;

public abstract class BaseFragmentModel<P extends BaseFragmentPresenter,CONTRACT> extends SuperBase<CONTRACT>{
    public P mPresenter;

    public BaseFragmentModel(P mPresenter) {
        this.mPresenter = mPresenter;
    }
}
