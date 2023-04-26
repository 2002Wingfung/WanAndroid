package com.hongyongfeng.wanandroid.module.query.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseActivity;
import com.hongyongfeng.wanandroid.module.query.interfaces.Query;
import com.hongyongfeng.wanandroid.module.query.presenter.QueryPresenter;

public class QueryActivity extends BaseActivity<QueryPresenter, Query.VP>{

    TextView tvBack;
    TextView tvClear;
    EditText edtKeyWords;

    @Override
    public Query.VP getContract() {
        return null;
    }

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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_clear:
                edtKeyWords.setText("");
                break;
            case R.id.tv_back:
                finish();
                break;
            default:
                break;
        }
    }


}
