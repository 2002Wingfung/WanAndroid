package com.hongyongfeng.wanandroid.module.signinorup.register.fragment;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.module.signinorup.login.interfaces.ILogin;
import com.hongyongfeng.wanandroid.module.signinorup.login.presenter.LoginFragmentPresenter;
import com.hongyongfeng.wanandroid.module.signinorup.register.interfaces.RegisterInterface;
import com.hongyongfeng.wanandroid.module.signinorup.register.presenter.RegisterFragmentPresenter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends BaseFragment<RegisterFragmentPresenter, RegisterInterface.VP> {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
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


    public RegisterFragment() {
        // Required empty public constructor

    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fragmentActivity=requireActivity();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public RegisterInterface.VP getContract() {
        return new RegisterInterface.VP() {
            @Override
            public void requestRegisterVP(String name, String pwd) {
                mPresenter.getContract().requestRegisterVP(name,pwd);
            }

            @Override
            public void responseRegisterResult(boolean loginStatusResult) {
                Toast.makeText(fragmentActivity, loginStatusResult?"注册成功":"该用户名已存在", Toast.LENGTH_SHORT).show();

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
    }

    @Override
    protected void initListener() {
        btnRegister.setOnClickListener(this);
        edtPwdAgain.addTextChangedListener(new TextWatcher() {
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
                String edtPassword=edtPwdAgain.getText().toString();
                if (edtPassword.length()>0){
                    passwordAgain=1;
                }else {
                    passwordAgain=0;

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
                String edtPassword=edtPwd.getText().toString();
                if (edtPassword.length()>0){
                    tvPwd.setVisibility(View.INVISIBLE);
                    password=1;
                    if (name==1&&passwordAgain==1){
                        if (edtPassword.equals(edtPwdAgain.getText().toString())){
                            btnRegister.setEnabled(true);
                            tvPwdAgain.setVisibility(View.INVISIBLE);
                            btnRegister.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.button_shape,null));
                        }
                        else {
                            btnRegister.setEnabled(false);
                            tvPwdAgain.setVisibility(View.VISIBLE);
                            btnRegister.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.bg_btn_login,null));
                        }
                    }

                }else {
                    tvPwd.setVisibility(View.VISIBLE);
                    password=0;
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
                    if (password==1&&passwordAgain==1){
                        btnRegister.setEnabled(true);
                        btnRegister.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.button_shape,null));
                    }

                }else {
                    tvAccount.setVisibility(View.VISIBLE);
                    name=0;
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
        int num2 = 2;
        int num0 = 0;
        switch (v.getId()){
            case R.id.password_visibility_register:
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
            case R.id.password_visibility_again_register:
                if (count[1] % num2 == num0){
                    tvVisibilityAgain.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_visible,null));
                    edtPwdAgain.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
                else {
                    tvVisibilityAgain.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_invisible,null));
                    edtPwdAgain.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
                count[1]++;
                break;
            case R.id.register:
                String name= edtName.getText().toString().replaceAll(" ","");
                String pwd= edtPwd.getText().toString().replaceAll(" ","");
                System.out.println("name"+name);
                System.out.println("pwd"+pwd);
                //面向接口
                getContract().requestRegisterVP(name,pwd);
            default:
                break;
        }
    }
}