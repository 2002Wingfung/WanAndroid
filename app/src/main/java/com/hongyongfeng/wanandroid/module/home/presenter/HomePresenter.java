package com.hongyongfeng.wanandroid.module.home.presenter;

import com.hongyongfeng.wanandroid.base.BasePresenter;
import com.hongyongfeng.wanandroid.module.home.interfaces.Home;
import com.hongyongfeng.wanandroid.module.home.model.HomeModel;
import com.hongyongfeng.wanandroid.module.home.view.HomeActivity;

public class HomePresenter extends BasePresenter<HomeModel, HomeActivity,Home.VP> {
    @Override
    public HomeModel getModelInstance() {
        return new HomeModel(this);
    }

    @Override
    public Home.VP getContract() {
        return null;
    }
}
