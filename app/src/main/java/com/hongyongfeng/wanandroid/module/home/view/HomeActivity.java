package com.hongyongfeng.wanandroid.module.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseActivity;
import com.hongyongfeng.wanandroid.module.home.interfaces.Home;
import com.hongyongfeng.wanandroid.module.home.presenter.HomePresenter;
import com.hongyongfeng.wanandroid.module.query.view.QueryActivity;


public class HomeActivity extends BaseActivity<HomePresenter, Home.VP> {

    TextView tvQuery;

    @Override
    public Home.VP getContract() {
        return null;
    }

    @Override
    public void initListener() {
        tvQuery.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void destroy() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        tvQuery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println(123);
//            }
//        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_home;
    }


    @Override
    public HomePresenter getPresenterInstance() {
        return new HomePresenter();
    }

    @Override
    public <ERROR> void responseError(ERROR error, Throwable throwable) {

    }

    @Override
    public void initView() {
        tvQuery=findViewById(R.id.tv_query);
    }

    @Override
    public void onClick(View v) {
        System.out.println(v.getId());
        switch (v.getId()){
            case R.id.tv_query:
                Intent intent=new Intent(HomeActivity.this, QueryActivity.class);
                startActivity(intent);
            default:
                break;
        }
    }
}
