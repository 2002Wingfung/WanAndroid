package com.hongyongfeng.wanandroid.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment <P extends BasePresenter,CONTRACT> extends Fragment implements View.OnClickListener{

    public abstract CONTRACT getContract();
    public P mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView();
        initListener();
        initData();
        mPresenter=getPresenterInstance();
        //mPresenter.bindView(this);
        return inflater.inflate(getFragmentView(), container, false);
    }

    public abstract void initView();
    public abstract void initListener();
    public abstract void initData();

    public abstract P getPresenterInstance();


    public abstract int getFragmentView();

}
