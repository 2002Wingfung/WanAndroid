package com.hongyongfeng.wanandroid.module.login.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseActivity;
import com.hongyongfeng.wanandroid.module.login.interfaces.ILogin;
import com.hongyongfeng.wanandroid.module.login.presenter.LoginPresenter;

public class LoginActivity extends BaseActivity<LoginPresenter,ILogin.VP> {
    @Override
    public ILogin.VP getContract() {
        return new ILogin.VP() {
            @Override
            public void requestLoginVP(String name, String pwd) {
                mPresenter.getContract().requestLoginVP(name,pwd);
            }

            @Override
            public void responseLoginResult(boolean loginStatusResult) {
                Toast.makeText(LoginActivity.this, loginStatusResult?"登录成功":"登录失败", Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void initListener() {
        btnLogin.setOnClickListener(this);
    }



    @Override
    public void initData() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginPresenter getPresenterInstance() {
        return new LoginPresenter();
    }

    @Override
    public <ERROR> void responseError(ERROR error, Throwable throwable) {
        Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
    }

    private EditText etName;
    private EditText etPwd;
    private Button btnLogin;

    @Override
    public void initView() {
        etName=findViewById(R.id.login_user_name);
        etPwd=findViewById(R.id.login_password);
        btnLogin=findViewById(R.id.login);

    }

    @Override
    public void onClick(View v) {
        String name=etName.getText().toString();
        String pwd=etPwd.getText().toString();
        //第一种方式：面向具体方法

        //requestLogin(name,pwd);

        //第二种方式：面向接口
        getContract().requestLoginVP(name,pwd);
    }

}
