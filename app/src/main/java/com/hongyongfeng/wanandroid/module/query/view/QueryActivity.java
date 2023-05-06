package com.hongyongfeng.wanandroid.module.query.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseActivity;
import com.hongyongfeng.wanandroid.module.query.interfaces.Query;
import com.hongyongfeng.wanandroid.module.query.presenter.QueryPresenter;
import com.hongyongfeng.wanandroid.module.query.view.fragment.ArticleFragment;
import com.hongyongfeng.wanandroid.module.query.view.fragment.HeatedWordsFragment;
import com.hongyongfeng.wanandroid.module.query.view.fragment.LoadingFragment;
import com.hongyongfeng.wanandroid.util.KeyboardUtils;

public class QueryActivity extends BaseActivity<QueryPresenter, Query.VP>{
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    HeatedWordsFragment heatedWordsFragment=new HeatedWordsFragment();
    LoadingFragment loadingFragment=new LoadingFragment();
    ArticleFragment articleFragment=new ArticleFragment();

    TextView tvBack;
    TextView tvClear;
    EditText edtKeyWords;

    @Override
    public Query.VP getContract() {
        return new Query.VP() {
            @Override
            public void requestQueryVP(String name) {

                mPresenter.getContract().requestQueryVP(name);
            }

            @Override
            public void responseQueryResult(boolean loginStatusResult) {
                loadFragment();
                handler.sendEmptyMessageDelayed(0,2000);
//                transaction.hide(loadingFragment).add(R.id.fragment_query,articleFragment).show(articleFragment).commit();
            }
        };
    }
    Handler handler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //System.out.println(msg.what);
            if (!articleFragment.isAdded()){
                transaction.hide(loadingFragment).add(R.id.fragment_query,articleFragment).show(articleFragment).commit();
            }else {
                transaction.hide(loadingFragment).show(articleFragment).commit();
            }

        }
    };

    @Override
    public void initListener() {
        tvClear.setOnClickListener(this);
        tvBack.setOnClickListener(this);
        edtKeyWords.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (edtKeyWords.getText().toString().length()>0){
                    tvClear.setVisibility(View.VISIBLE);
                }else {
                    tvClear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtKeyWords.getText().toString().length()>0){
                    tvClear.setVisibility(View.VISIBLE);
                }else {
                    tvClear.setVisibility(View.INVISIBLE);
                }
            }
        });
        edtKeyWords.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Toast.makeText(QueryActivity.this, edtKeyWords.getText().toString(), Toast.LENGTH_SHORT).show();

                    loadFragment();
                    if (!articleFragment.isAdded()){
//                        transaction.hide(heatedWordsFragment).add(R.id.fragment_query,articleFragment).show(articleFragment).commit();


                    }

                    if (!loadingFragment.isAdded()){
                        transaction.hide(heatedWordsFragment).add(R.id.fragment_query,loadingFragment).show(loadingFragment).commit();
                    }else {
                        transaction.hide(heatedWordsFragment).show(loadingFragment).commit();
                    }
                    getContract().requestQueryVP("");

                    // 在这里写搜索的操作,一般都是网络请求数据
                }
                //点击搜索的时候隐藏软键盘
                return false;

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_query;
    }


    @Override
    public QueryPresenter getPresenterInstance() {
        return new QueryPresenter();
    }

    @Override
    public <ERROR> void responseError(ERROR error, Throwable throwable) {

    }

    @Override
    public void initView() {
        tvBack=findViewById(R.id.tv_back);
        tvClear=findViewById(R.id.tv_clear);
        edtKeyWords=findViewById(R.id.edt_keyword);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(heatedWordsFragment);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_clear:
                edtKeyWords.setText("");
                loadFragment();
                if (articleFragment.isVisible()){
                    transaction.hide(articleFragment).show(heatedWordsFragment).commit();
                }
                break;
            case R.id.tv_back:
                finish();
                break;
            default:
                break;
        }
    }
    private void replaceFragment(Fragment fragment){
        loadFragment();
        transaction.add(R.id.fragment_query,fragment).show(fragment);
        transaction.commit();
    }
    private void loadFragment(){
        fragmentManager=getSupportFragmentManager();
        transaction=fragmentManager.beginTransaction();
    }

}
