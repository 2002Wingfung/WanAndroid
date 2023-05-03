package com.hongyongfeng.wanandroid.module.signinorup;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseActivity;
import com.hongyongfeng.wanandroid.module.signinorup.login.interfaces.ILogin;
import com.hongyongfeng.wanandroid.module.signinorup.login.presenter.LoginPresenter;
import com.hongyongfeng.wanandroid.module.signinorup.login.view.fragment.LoginFragment;
import com.hongyongfeng.wanandroid.module.signinorup.register.fragment.RegisterFragment;
import com.hongyongfeng.wanandroid.test.FragmentVPAdapter;
import com.hongyongfeng.wanandroid.test.VPFragment;

import java.util.ArrayList;
import java.util.List;

public class SignInUpActivity extends BaseActivity<LoginPresenter,ILogin.VP> {
    @Override
    public ILogin.VP getContract() {
        return null;
    }

    @Override
    public void initListener() {
        tvBack.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                onViewPagerSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    private void setItemSelected(int id){
        switch (id){
            case R.id.tv_login:
                tvLogin.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.tv_register:
                tvRegister.setTextColor(getResources().getColor(R.color.white));
                break;
            default:
                break;
        }
    }

    private void resetTitleState(){
        //重置标题栏的文字的颜色
        tvLogin.setTextColor(getResources().getColor(R.color.darker_gray));
        tvRegister.setTextColor(getResources().getColor(R.color.darker_gray));
    }
    private void onViewPagerSelected(int position) {
        resetTitleState();
        if (position==0){
            setItemSelected(R.id.tv_login);
        } else {
            setItemSelected(R.id.tv_register);
        }
    }

    @Override
    public void initData() {
        fragmentList=new ArrayList<>();

        //fragmentList.add(new BaseLogInUpFragment());
        fragmentList.add(new LoginFragment());

        fragmentList.add(new RegisterFragment());
        //fragmentList.add(VPFragment.newInstance("123",""));

    }

    @Override
    public void destroy() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_login_and_register;
    }


    @Override
    public LoginPresenter getPresenterInstance() {
        return new LoginPresenter();
    }

    @Override
    public <ERROR> void responseError(ERROR error, Throwable throwable) {
        Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter=new FragmentVPAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
    }
    private List<Fragment> fragmentList;
    private FragmentVPAdapter adapter;
    private ViewPager viewPager;
    TextView tvLogin;
    TextView tvRegister;
    TextView tvBack;
    @Override
    public void initView() {
        tvBack=findViewById(R.id.tv_back);
        tvLogin=findViewById(R.id.tv_login);
        tvRegister=findViewById(R.id.tv_register);
        viewPager=findViewById(R.id.vp_login_and_register);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_login:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_register:
                viewPager.setCurrentItem(2);
                break;
            default:
                break;
        }

    }
}
