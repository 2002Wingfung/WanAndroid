package com.hongyongfeng.wanandroid.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment <P extends BaseFragmentPresenter,CONTRACT> extends Fragment implements View.OnClickListener{

    public abstract CONTRACT getContract();

    public P mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mPresenter=getPresenterInstance();

        mPresenter.bindView(this);
        return inflater.inflate(getFragmentView(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initListener();

        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroy();

    }

    protected abstract void destroy();

    protected abstract void initView(View view);
    protected abstract void initListener();
    protected abstract void initData();

    protected abstract P getPresenterInstance();
    protected abstract <ERROR> void responseError(ERROR error, Throwable throwable);


    protected abstract int getFragmentView();

}
