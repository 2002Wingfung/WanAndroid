package com.hongyongfeng.wanandroid.module.knowledge.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseActivity;
import com.hongyongfeng.wanandroid.base.BasePresenter;

import java.util.Map;


public class TabActivity extends BaseActivity {
    TextView tvTitle;
    TextView tvBack;
    @Override
    public Object getContract() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(intent != null){
            //获取intent中的参数
            Map<String,Object> childrenMap=(Map<String,Object>)intent.getSerializableExtra("map");
            System.out.println(childrenMap.get("name0"));
            String name=intent.getStringExtra("name");
            tvTitle.setText(name);
        }
    }

    @Override
    protected void initListener() {
        tvBack.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void destroy() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_tab_layout;
    }

    @Override
    protected BasePresenter getPresenterInstance() {
        return null;
    }

    @Override
    protected void initView() {
        tvBack=findViewById(R.id.tv_back);
        tvTitle=findViewById(R.id.tv_title);
    }

    @Override
    protected void responseError(Object o, Throwable throwable) {

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==R.id.tv_back){
            finish();
        }
    }
}
