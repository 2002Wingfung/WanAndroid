package com.hongyongfeng.wanandroid.module.home.model;

import com.hongyongfeng.wanandroid.base.BaseModel;
import com.hongyongfeng.wanandroid.module.home.interfaces.Home;
import com.hongyongfeng.wanandroid.module.home.presenter.HomePresenter;

public class HomeModel extends BaseModel<HomePresenter, Home.M> {
    public HomeModel(HomePresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public Home.M getContract() {
        return null;
    }
}
