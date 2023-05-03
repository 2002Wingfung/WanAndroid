package com.hongyongfeng.wanandroid.module.signinorup.login.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.module.knowledge.interfaces.KnowledgeFragmentInterface;
import com.hongyongfeng.wanandroid.module.knowledge.presenter.KnowledgeFragmentPresenter;
import com.hongyongfeng.wanandroid.module.signinorup.login.interfaces.ILogin;
import com.hongyongfeng.wanandroid.module.signinorup.login.presenter.LoginFragmentPresenter;
import com.hongyongfeng.wanandroid.module.signinorup.login.presenter.LoginPresenter;
import com.hongyongfeng.wanandroid.module.signinorup.login.view.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends BaseFragment<LoginFragmentPresenter, ILogin.VP> {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
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


    public LoginFragment() {
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
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
    public ILogin.VP getContract() {
        return new ILogin.VP() {
            @Override
            public void requestLoginVP(String name, String pwd) {
                mPresenter.getContract().requestLoginVP(name,pwd);
            }

            @Override
            public void responseLoginResult(boolean loginStatusResult) {
                Toast.makeText(fragmentActivity, loginStatusResult?"登录成功":"登录失败", Toast.LENGTH_SHORT).show();

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
//        layout=findViewById(R.id.include);
//        tvBack=layout.findViewById(R.id.tv_back);
//        tvLogin=layout.findViewById(R.id.tv_login);
//        tvRegister=layout.findViewById(R.id.tv_register);
        //tvTitle.setText("登录");
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
                    tvVisibility.setBackground(getResources().getDrawable(R.drawable.ic_visible));
                    edtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
                else {
                    tvVisibility.setBackground(getResources().getDrawable(R.drawable.ic_invisible));
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
                getContract().requestLoginVP(name,pwd);
            default:
                break;
        }
    }
}