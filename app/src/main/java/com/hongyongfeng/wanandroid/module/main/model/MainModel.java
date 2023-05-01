package com.hongyongfeng.wanandroid.module.main.model;

import com.hongyongfeng.wanandroid.base.BaseModel;
import com.hongyongfeng.wanandroid.module.main.interfaces.MainInterface;
import com.hongyongfeng.wanandroid.module.main.presenter.MainPresenter;

public class MainModel extends BaseModel<MainPresenter, MainInterface.M> {
    public MainModel(MainPresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public MainInterface.M getContract() {
        return null;
    }
}
