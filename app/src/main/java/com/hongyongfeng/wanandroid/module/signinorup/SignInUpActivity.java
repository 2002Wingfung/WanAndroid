package com.hongyongfeng.wanandroid.module.signinorup;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseActivity;
import com.hongyongfeng.wanandroid.module.signinorup.login.interfaces.ILogin;
import com.hongyongfeng.wanandroid.module.signinorup.login.presenter.LoginPresenter;
import com.hongyongfeng.wanandroid.test.FragmentVPAdapter;
import com.hongyongfeng.wanandroid.test.VPFragment;

import java.util.ArrayList;
import java.util.List;

public class SignInUpActivity extends BaseActivity<LoginPresenter,ILogin.VP> {
    @Override
    public ILogin.VP getContract() {
        return new ILogin.VP() {
            @Override
            public void requestLoginVP(String name, String pwd) {
                mPresenter.getContract().requestLoginVP(name,pwd);
            }

            @Override
            public void responseLoginResult(boolean loginStatusResult) {
                Toast.makeText(SignInUpActivity.this, loginStatusResult?"登录成功":"登录失败", Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void initListener() {

        tvBack.setOnClickListener(this);
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
        VPFragment fragmentHome=VPFragment.newInstance("首页文章","");
        VPFragment fragmentHome1=VPFragment.newInstance("nihao","");

        fragmentList.add(fragmentHome);
        fragmentList.add(fragmentHome1);
        //fragmentList.add(new LoginFragment());
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
//        imgAccount.setColorFilter(0xff838383);

    }
    private List<Fragment> fragmentList;
    private FragmentVPAdapter adapter;
    private ViewPager viewPager;

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
    TextView tvBack;

    final int[] count = {0};

    @Override
    public void initView() {
//        tvVisibility=findViewById(R.id.password_visibility);
//        edtName =findViewById(R.id.edt_login_user_name);
//        edtPwd =findViewById(R.id.edt_login_password);
//        btnLogin=findViewById(R.id.login);
//        imgAccount=findViewById(R.id.account);
//        layout=findViewById(R.id.include);
        tvBack=findViewById(R.id.tv_back);
        tvLogin=findViewById(R.id.tv_login);
        tvRegister=findViewById(R.id.tv_register);
//        //tvTitle.setText("登录");
//        tvAccount=findViewById(R.id.account_warning);
//        tvPwd=findViewById(R.id.password_warning);
//        edtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        viewPager=findViewById(R.id.vp_login_and_register);


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
            case R.id.tv_back:
                finish();
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
