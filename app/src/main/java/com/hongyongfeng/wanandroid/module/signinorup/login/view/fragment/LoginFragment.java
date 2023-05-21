package com.hongyongfeng.wanandroid.module.signinorup.login.view.fragment;

import static com.hongyongfeng.wanandroid.module.signinorup.register.fragment.RegisterFragment.NAME;
import static com.hongyongfeng.wanandroid.module.signinorup.register.fragment.RegisterFragment.NONE;
import static com.hongyongfeng.wanandroid.module.signinorup.register.fragment.RegisterFragment.NULL;
import static com.hongyongfeng.wanandroid.util.Constant.ONE;
import static com.hongyongfeng.wanandroid.util.Constant.TWO;
import static com.hongyongfeng.wanandroid.util.Constant.ZERO;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.module.signinorup.login.interfaces.ILogin;
import com.hongyongfeng.wanandroid.module.signinorup.login.presenter.LoginFragmentPresenter;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author Wingfung Hung
 */
public class LoginFragment extends BaseFragment<LoginFragmentPresenter, ILogin.Vp> {
    private Button btnLogin;
    private TextView tvVisibility;
    private TextView tvSignUp;
    private EditText edtPwd;
    private EditText edtName;
    private TextView tvAccount;
    private TextView tvPwd;
    private int name=ZERO;
    private int password=ZERO;
    private FragmentActivity fragmentActivity;
    final int[] count = {ZERO};
    private ViewPager viewPager;

    public LoginFragment() {
        // Required empty public constructor
    }

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
                fragmentActivity.runOnUiThread(() -> {
                    Toast.makeText(fragmentActivity, loginStatusResult?"登录成功":"登录失败", Toast.LENGTH_SHORT).show();
                    if (loginStatusResult){
                        Intent intent = new Intent();
                        intent.putExtra(NAME,edtName.getText().toString());
                        fragmentActivity.setResult(ONE, intent);
                        fragmentActivity.finish();
                    }
                });

            }

            @Override
            public void error(String error) {
                fragmentActivity.runOnUiThread(() -> Toast.makeText(fragmentActivity, error, Toast.LENGTH_SHORT).show());
            }
        };
    }


    @Override
    protected void destroy() {

    }

    @Override
    protected void initView(View view) {
        tvVisibility=fragmentActivity.findViewById(R.id.password_visibility);
        tvSignUp=fragmentActivity.findViewById(R.id.tv_sign_up);
        edtName =fragmentActivity.findViewById(R.id.edt_login_user_name);
        edtPwd =fragmentActivity.findViewById(R.id.edt_login_password);
        btnLogin=fragmentActivity.findViewById(R.id.login);
        tvAccount=fragmentActivity.findViewById(R.id.account_warning);
        tvPwd=fragmentActivity.findViewById(R.id.password_warning);
        edtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        viewPager=fragmentActivity.findViewById(R.id.vp_login_and_register);
    }

    @Override
    protected void initListener() {
        btnLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        edtPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().contains(NULL)){
                    String[] str = s.toString().split(NULL);
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
                if (edtPwd.getText().toString().length()>ZERO){
                    tvPwd.setVisibility(View.INVISIBLE);
                    password=ONE;
                    if (name==ONE){
                        btnLogin.setEnabled(true);
                        btnLogin.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.button_shape,null));
                    }

                }else {
                    tvPwd.setVisibility(View.VISIBLE);
                    password=ZERO;
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
                if(s.toString().contains(NULL)){
                    String[] str = s.toString().split(NULL);
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
                if (edtName.getText().toString().length()>ZERO){
                    tvAccount.setVisibility(View.INVISIBLE);
                    name=ONE;
                    if (password==ONE){
                        btnLogin.setEnabled(true);
                        btnLogin.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.button_shape,null));
                    }

                }else {
                    tvAccount.setVisibility(View.VISIBLE);
                    name=ZERO;
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
        switch (v.getId()){
            case R.id.password_visibility:
                if (count[ZERO] % TWO == ZERO){
                    tvVisibility.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_visible,null));
                    edtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
                else {
                    tvVisibility.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_invisible,null));
                    edtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
                count[ZERO]++;
                break;
            case R.id.tv_sign_up:
                viewPager.setCurrentItem(ONE);
                break;
            case R.id.login:
                String name= edtName.getText().toString().replaceAll(NULL,NONE);
                String pwd= edtPwd.getText().toString().replaceAll(NULL,NONE);
                //面向接口
                getContract().requestLoginVp(name,pwd);
            default:
                break;
        }
    }
}