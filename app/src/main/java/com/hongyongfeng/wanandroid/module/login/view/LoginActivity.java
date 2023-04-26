package com.hongyongfeng.wanandroid.module.login.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hongyongfeng.wanandroid.base.BaseActivity;
import com.hongyongfeng.wanandroid.module.login.interfaces.ILogin;
import com.hongyongfeng.wanandroid.module.login.presenter.LoginPresenter;

public class LoginActivity extends BaseActivity<LoginPresenter,ILogin.VP> {
    @Override
    public ILogin.VP getContract() {
        return new ILogin.VP() {
            @Override
            public void requestLoginVP(String name, String pwd) {
                //mPresenter.requestLogin(name,pwd);
                System.out.println("login"+System.currentTimeMillis());
                mPresenter.getContract().requestLoginVP(name,pwd);
                System.out.println(name);
                System.out.println(pwd);
            }

            @Override
            public void responseLoginResult(boolean loginStatusResult) {
                System.out.println(System.currentTimeMillis());
                System.out.println(loginStatusResult);
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
        return 0;
//        return R.layout.activity_login;
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
//        etName=findViewById(R.id.et_name);
//        etPwd=findViewById(R.id.et_pwd);
//        btnLogin=findViewById(R.id.btn_login);

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

//    @Override
//    public void requestLogin(String name, String pwd) {
//        mPresenter.requestLogin(name,pwd);
//    }
//
//    @Override
//    public void responseLoginResult(boolean loginStatusResult) {
//        Toast.makeText(this, loginStatusResult?"登录成功":"登录失败", Toast.LENGTH_SHORT).show();
//    }
}
