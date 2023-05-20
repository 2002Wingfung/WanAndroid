package com.hongyongfeng.wanandroid.base;

/**
 * @author Wingfung Hung
 */
public abstract class BaseFragmentModel<P extends BaseFragmentPresenter,CONTRACT> extends Base<CONTRACT> {
    public P mPresenter;

    public BaseFragmentModel(P mPresenter) {
        this.mPresenter = mPresenter;
    }
}
