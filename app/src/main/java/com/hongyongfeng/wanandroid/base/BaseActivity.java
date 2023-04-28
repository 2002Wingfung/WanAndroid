package com.hongyongfeng.wanandroid.base;

import android.app.UiModeManager;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.navigation.NavigationView;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.util.KeyboardUtils;
import com.hongyongfeng.wanandroid.util.StatusBarUtils;

public abstract class BaseActivity<P extends BasePresenter,CONTRACT> extends AppCompatActivity implements View.OnClickListener {

    public abstract CONTRACT getContract();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //获取当前获得焦点的View
                View view = getCurrentFocus();
                //调用方法判断是否需要隐藏键盘
                KeyboardUtils.hideKeyboard(ev, view, this);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(getContentViewId());
        //判断是否处于深色模式
        //深色模式的值为:0x21
        //浅色模式的值为:0x11
        if (this.getApplicationContext().getResources().getConfiguration().uiMode == 0x21) {
            StatusBarUtils.setWindowStatusBarColor(this, R.color.transparent);
            ConstraintLayout layout=findViewById(R.id.include);
            NavigationView navigationView=findViewById(R.id.nav_view);

            if (layout!=null){
                layout.setBackgroundColor(getResources().getColor(R.color.transparent));
            }
            if (navigationView!=null){
                View headView=navigationView.getHeaderView(0);
                headView.setBackgroundColor(getResources().getColor(R.color.transparent));
            }
        }else {
            StatusBarUtils.setWindowStatusBarColor(this, R.color.blue);
        }
        initView();
        initListener();
        initData();
        mPresenter=getPresenterInstance();
        mPresenter.bindView(this);
    }

    public P mPresenter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroy();
    }

    public abstract void initListener();
    public abstract void initData();
    public abstract void destroy();
    public abstract int getContentViewId();

    public abstract P getPresenterInstance();
    //处理 ，响应错误信息
    public abstract <ERROR extends Object> void responseError(ERROR error, Throwable throwable);
    public abstract void initView();
}
