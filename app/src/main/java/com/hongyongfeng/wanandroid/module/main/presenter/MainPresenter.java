package com.hongyongfeng.wanandroid.module.main.presenter;

import com.hongyongfeng.wanandroid.base.BasePresenter;
import com.hongyongfeng.wanandroid.module.main.interfaces.MainInterface;
import com.hongyongfeng.wanandroid.module.main.model.MainModel;
import com.hongyongfeng.wanandroid.module.main.activity.MainActivity;

/**
 * @author Wingfung Hung
 */
public class MainPresenter extends BasePresenter<MainModel, MainActivity, MainInterface.Vp> {
    @Override
    public MainModel getModelInstance() {
        return new MainModel(this);
    }

    @Override
    public MainInterface.Vp getContract() {
        return new MainInterface.Vp() {
            @Override
            public void requestVp() {
                try {
                    mModel.getContract().requestM();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void responseResult(String name) {
                mView.getContract().responseResult(name);
            }
        };
    }
}
