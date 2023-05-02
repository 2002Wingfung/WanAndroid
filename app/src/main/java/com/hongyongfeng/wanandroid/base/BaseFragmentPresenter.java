package com.hongyongfeng.wanandroid.base;

public abstract class BaseFragmentPresenter<M extends BaseFragmentModel,V extends BaseFragment,CONTRACT> extends SuperBase<CONTRACT>{
    public V mView;
    public M mModel;

    public BaseFragmentPresenter() {
        this.mModel = getModelInstance();
    }

    public void bindView(V mView){
        this.mView=mView;
    }
    public void unBindView(){
        this.mView=null;
    }
    public abstract M getModelInstance();
}
