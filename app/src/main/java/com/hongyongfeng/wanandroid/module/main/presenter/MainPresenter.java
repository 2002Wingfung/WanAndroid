package com.hongyongfeng.wanandroid.module.main.presenter;

import com.hongyongfeng.wanandroid.base.BasePresenter;
import com.hongyongfeng.wanandroid.module.main.interfaces.MainInterface;
import com.hongyongfeng.wanandroid.module.main.model.MainModel;
import com.hongyongfeng.wanandroid.module.main.activity.MainActivity;

public class MainPresenter extends BasePresenter<MainModel, MainActivity, MainInterface.VP> {
    @Override
    public MainModel getModelInstance() {
        return new MainModel(this);
    }

    @Override
    public MainInterface.VP getContract() {
        return null;
    }
}
