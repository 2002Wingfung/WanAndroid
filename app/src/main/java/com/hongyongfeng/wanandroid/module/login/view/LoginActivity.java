package com.hongyongfeng.wanandroid.module.login.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

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
        edtPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtPwd.getText().toString().length()>0){
                    tvPwd.setVisibility(View.INVISIBLE);
                    password=1;
                    if (name==1){
                        btnLogin.setEnabled(true);
                        btnLogin.setBackground(getResources().getDrawable(R.drawable.button_shape));
                    }

                }else {
                    tvPwd.setVisibility(View.VISIBLE);
                    password=0;
                    btnLogin.setEnabled(false);
                    btnLogin.setBackground(getResources().getDrawable(R.drawable.bg_btn_login));
                }
            }
        });
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtName.getText().toString().length()>0){
                    tvAccount.setVisibility(View.INVISIBLE);
                    name=1;
                    if (password==1){
                        btnLogin.setEnabled(true);
                        btnLogin.setBackground(getResources().getDrawable(R.drawable.button_shape));
                    }

                }else {
                    tvAccount.setVisibility(View.VISIBLE);
                    name=0;
                    btnLogin.setEnabled(false);
                    btnLogin.setBackground(getResources().getDrawable(R.drawable.bg_btn_login));
                }
            }
        });
        tvVisibility.setOnClickListener(this);
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        imgAccount.setColorFilter(0xff838383);
    }

    private int name=0;
    private int password=0;
    private TextView tvPwd;
    private TextView tvAccount;
    private EditText edtName;
    private EditText edtPwd;
    private Button btnLogin;
    private ConstraintLayout layout;
    private ImageView imgAccount;
    private TextView tvVisibility;
    TextView tvLogin;
    TextView tvRegister;

    @Override
    public void initView() {
        tvVisibility=findViewById(R.id.password_visibility);
        edtName =findViewById(R.id.edt_login_user_name);
        edtPwd =findViewById(R.id.edt_login_password);
        btnLogin=findViewById(R.id.login);
        imgAccount=findViewById(R.id.account);
        layout=findViewById(R.id.include);

        tvLogin=layout.findViewById(R.id.tv_login);
        tvRegister=layout.findViewById(R.id.tv_register);
        //tvTitle.setText("登录");
        tvAccount=findViewById(R.id.account_warning);
        tvPwd=findViewById(R.id.password_warning);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        String name= edtName.getText().toString();
        String pwd= edtPwd.getText().toString();
        final int[] count = {0};
        int num2=2;
        int num0=0;
        switch (v.getId()){
            case R.id.password_visibility:
                if (count[0] %num2==num0){
                    edtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    tvVisibility.setBackground(getResources().getDrawable(R.drawable.ic_visible));
                }
                else {
                    edtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    tvVisibility.setBackground(getResources().getDrawable(R.drawable.ic_invisible));
                }
                count[0]++;
                break;
            default:
                break;
        }
        //第一种方式：面向具体方法

        //requestLogin(name,pwd);

        //第二种方式：面向接口
        //getContract().requestLoginVP(name,pwd);
    }

}
