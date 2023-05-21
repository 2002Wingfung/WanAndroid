package com.hongyongfeng.wanandroid.module.signinorup;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.module.signinorup.login.interfaces.ILogin;
import com.hongyongfeng.wanandroid.module.signinorup.login.presenter.LoginFragmentPresenter;
@Deprecated
public class BaseLogInUpFragment extends BaseFragment<LoginFragmentPresenter, ILogin.Vp> {
    private Button btnLogin;
    private TextView tvVisibility;
    private EditText edtPwd;
    private EditText edtName;
    private TextView tvAccount;
    private TextView tvPwd;
    private int name=0;
    private int password=0;
    private FragmentActivity fragmentActivity;
    final int[] count = {0};
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fragmentActivity=requireActivity();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public ILogin.Vp getContract() {
        return new ILogin.Vp() {
            @Override
            public void requestLoginVp(String name, String pwd) {
                mPresenter.getContract().requestLoginVp(name,pwd);
            }

            @Override
            public void responseLoginResult(boolean loginStatusResult) {
                System.out.println(loginStatusResult);
                Toast.makeText(fragmentActivity, loginStatusResult?"登录成功":"登录失败", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void error(String error) {

            }
        };
    }


    @Override
    protected void destroy() {

    }

    @Override
    protected void initView(View view) {
        tvVisibility=fragmentActivity.findViewById(R.id.password_visibility);
        edtName =fragmentActivity.findViewById(R.id.edt_login_user_name);
        edtPwd =fragmentActivity.findViewById(R.id.edt_login_password);
        btnLogin=fragmentActivity.findViewById(R.id.login);
        tvAccount=fragmentActivity.findViewById(R.id.account_warning);
        tvPwd=fragmentActivity.findViewById(R.id.password_warning);
        edtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    @Override
    protected void initListener() {
        btnLogin.setOnClickListener(this);
        edtPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().contains(" ")){
                    String[] str = s.toString().split(" ");
                    StringBuilder content = new StringBuilder();
                    for (String value : str) {
                        content.append(value);
                    }
                    edtPwd.setText(content.toString());
                    edtPwd.setSelection(start);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtPwd.getText().toString().length()>0){
                    tvPwd.setVisibility(View.INVISIBLE);
                    password=1;
                    if (name==1){
                        btnLogin.setEnabled(true);
                        btnLogin.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.button_shape,null));
                    }

                }else {
                    tvPwd.setVisibility(View.VISIBLE);
                    password=0;
                    btnLogin.setEnabled(false);
                    btnLogin.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.bg_btn_login,null));
                }
            }
        });
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().contains(" ")){
                    String[] str = s.toString().split(" ");
                    StringBuilder content = new StringBuilder();
                    for (String value : str) {
                        content.append(value);
                    }
                    edtName.setText(content.toString());
                    edtName.setSelection(start);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtName.getText().toString().length()>0){
                    tvAccount.setVisibility(View.INVISIBLE);
                    name=1;
                    if (password==1){
                        btnLogin.setEnabled(true);
                        btnLogin.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.button_shape,null));
                    }

                }else {
                    tvAccount.setVisibility(View.VISIBLE);
                    name=0;
                    btnLogin.setEnabled(false);
                    btnLogin.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.bg_btn_login,null));
                }
            }
        });
        tvVisibility.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected LoginFragmentPresenter getPresenterInstance() {
        return new LoginFragmentPresenter();
    }

    @Override
    protected <ERROR> void responseError(ERROR error, Throwable throwable) {

    }

    @Override
    protected int getFragmentView() {
        return R.layout.fragment_login;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int num2 = 2;
        int num0 = 0;
        switch (v.getId()){
            case R.id.password_visibility:
                if (count[0] % num2 == num0){
                    tvVisibility.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_visible,null));
                    edtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
                else {
                    tvVisibility.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_invisible,null));
                    edtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
                count[0]++;
                break;
            case R.id.login:
                String name= edtName.getText().toString().replaceAll(" ","");
                String pwd= edtPwd.getText().toString().replaceAll(" ","");
                System.out.println("name"+name);
                System.out.println("pwd"+pwd);
                //面向接口
                getContract().requestLoginVp(name,pwd);
            default:
                break;
        }
    }
}
