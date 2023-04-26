package com.hongyongfeng.wanandroid.module.query.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseActivity;
import com.hongyongfeng.wanandroid.module.query.interfaces.Query;
import com.hongyongfeng.wanandroid.module.query.presenter.QueryPresenter;
import com.hongyongfeng.wanandroid.util.KeyboardUtils;

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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {//获取当前获得焦点的View
            View view = getCurrentFocus();
            //调用方法判断是否需要隐藏键盘
            KeyboardUtils.hideKeyboard(ev, view, this);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        edtKeyWords.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //点击搜索的时候隐藏软键盘
                    KeyboardUtils.hideKeyboardWithQuery(edtKeyWords);
                    Toast.makeText(QueryActivity.this, edtKeyWords.getText().toString(), Toast.LENGTH_SHORT).show();
                    // 在这里写搜索的操作,一般都是网络请求数据
                    return true;
                }

                return false;
            }
        });
    }


    @SuppressLint("NonConstantResourceId")
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
