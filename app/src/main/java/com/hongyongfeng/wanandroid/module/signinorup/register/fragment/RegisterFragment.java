package com.hongyongfeng.wanandroid.module.signinorup.register.fragment;

import static com.hongyongfeng.wanandroid.util.Constant.ONE;
import static com.hongyongfeng.wanandroid.util.Constant.TWO;
import static com.hongyongfeng.wanandroid.util.Constant.ZERO;
import android.annotation.SuppressLint;
import android.content.Intent;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.module.signinorup.register.interfaces.RegisterInterface;
import com.hongyongfeng.wanandroid.module.signinorup.register.presenter.RegisterFragmentPresenter;

/**
 * A simple {@link Fragment} subclass.
 * @author Wingfung Hung
 */
public class RegisterFragment extends BaseFragment<RegisterFragmentPresenter, RegisterInterface.Vp> {
    private Button btnRegister;
    private TextView tvVisibility;
    private TextView tvVisibilityAgain;
    private EditText edtPwd;
    private EditText edtPwdAgain;
    private EditText edtName;
    private TextView tvAccount;
    private TextView tvPwd;
    private TextView tvPwdAgain;
    private int name=0;
    private int password=0;
    private int passwordAgain=0;
    private FragmentActivity fragmentActivity;
    final int[] count = {0,1};
    private ViewPager viewPager;
    private TextView tvRegister;
    public static final String NAME="name";
    public static final String NULL=" ";
    public static final String NONE="";
    public RegisterFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fragmentActivity=requireActivity();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public RegisterInterface.Vp getContract() {
        return new RegisterInterface.Vp() {
            @Override
            public void requestRegisterVp(String name, String pwd) {
                mPresenter.getContract().requestRegisterVp(name,pwd);
            }

            @Override
            public void responseRegisterResult(boolean loginStatusResult) {
                fragmentActivity.runOnUiThread(() -> {
                    Toast.makeText(fragmentActivity, loginStatusResult?"注册成功":"注册失败", Toast.LENGTH_SHORT).show();
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
        tvVisibility=fragmentActivity.findViewById(R.id.password_visibility_register);
        tvVisibilityAgain=fragmentActivity.findViewById(R.id.password_visibility_again_register);
        edtName =fragmentActivity.findViewById(R.id.edt_register_user_name);
        edtPwd =fragmentActivity.findViewById(R.id.edt_register_password);
        edtPwdAgain=fragmentActivity.findViewById(R.id.edt_login_password_again_register);
        btnRegister =fragmentActivity.findViewById(R.id.register);
        tvAccount=fragmentActivity.findViewById(R.id.account_warning);
        tvPwd=fragmentActivity.findViewById(R.id.password_warning);
        tvPwdAgain=fragmentActivity.findViewById(R.id.password_warning_again);
        edtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        edtPwdAgain.setTransformationMethod(PasswordTransformationMethod.getInstance());
        viewPager=fragmentActivity.findViewById(R.id.vp_login_and_register);
        tvRegister=fragmentActivity.findViewById(R.id.tv_register1);
    }

    @Override
    protected void initListener() {
        tvRegister.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        edtPwdAgain.addTextChangedListener(new TextWatcher() {
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
                String edtPassword=edtPwdAgain.getText().toString();
                if (edtPassword.length()>ZERO){
                    passwordAgain=ONE;
                }else {
                    passwordAgain=ZERO;
                }
                if (edtPassword.equals(edtPwd.getText().toString())){
                    tvPwdAgain.setVisibility(View.INVISIBLE);
                    if (name==1&&password==1){
                        btnRegister.setEnabled(true);
                        btnRegister.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.button_shape,null));
                    }
                }else {
                    tvPwdAgain.setVisibility(View.VISIBLE);
                    btnRegister.setEnabled(false);
                    btnRegister.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.bg_btn_login,null));
                }
            }
        });
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
                String edtPassword=edtPwd.getText().toString();
                if (edtPassword.length()>ZERO){
                    tvPwd.setVisibility(View.INVISIBLE);
                    password=ONE;
                    if (passwordAgain==ONE){
                        if (edtPassword.equals(edtPwdAgain.getText().toString())){
                            tvPwdAgain.setVisibility(View.INVISIBLE);
                            if (name==ONE){
                                btnRegister.setEnabled(true);
                                btnRegister.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.button_shape,null));
                            }
                        }
                        else {
                            btnRegister.setEnabled(false);
                            tvPwdAgain.setVisibility(View.VISIBLE);
                            btnRegister.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.bg_btn_login,null));
                        }
                    }
                }else {
                    tvPwd.setVisibility(View.VISIBLE);
                    password=ZERO;
                    btnRegister.setEnabled(false);
                    btnRegister.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.bg_btn_login,null));
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
                if (edtName.getText().toString().length()>0){
                    tvAccount.setVisibility(View.INVISIBLE);
                    name=ONE;
                    if (password==ONE&&passwordAgain==ONE){
                        btnRegister.setEnabled(true);
                        btnRegister.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.button_shape,null));
                    }

                }else {
                    tvAccount.setVisibility(View.VISIBLE);
                    name=ZERO;
                    btnRegister.setEnabled(false);
                    btnRegister.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.bg_btn_login,null));
                }
            }
        });
        tvVisibility.setOnClickListener(this);
        tvVisibilityAgain.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected RegisterFragmentPresenter getPresenterInstance() {
        return new RegisterFragmentPresenter();
    }

    @Override
    protected <ERROR> void responseError(ERROR error, Throwable throwable) {

    }

    @Override
    protected int getFragmentView() {
        return R.layout.fragment_register;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int num2 = TWO;
        int num0 = ZERO;
        switch (v.getId()){
            case R.id.password_visibility_register:
                if (count[ZERO] % num2 == num0){
                    tvVisibility.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_visible,null));
                    edtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
                else {
                    tvVisibility.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_invisible,null));
                    edtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
                count[ZERO]++;
                break;
            case R.id.password_visibility_again_register:
                if (count[ONE] % num2 == num0){
                    tvVisibilityAgain.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_visible,null));
                    edtPwdAgain.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    tvVisibilityAgain.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_invisible,null));
                    edtPwdAgain.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                count[ONE]++;
                break;
            case R.id.tv_register1:
                viewPager.setCurrentItem(ZERO);
                break;
            case R.id.register:
                String name= edtName.getText().toString().replaceAll(NULL,NONE);
                String pwd= edtPwd.getText().toString().replaceAll(NULL,NONE);
                //面向接口
                getContract().requestRegisterVp(name,pwd);
            default:
                break;
        }
    }
}