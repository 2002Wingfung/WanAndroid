package com.hongyongfeng.wanandroid.module.knowledge.view.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.hongyongfeng.wanandroid.R;

import java.util.Map;


public class TabActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        Intent intent = getIntent();
        if(intent != null){
            //获取intent中的参数
            Map<String,Object> childrenMap=(Map<String,Object>)intent.getSerializableExtra("map");
            System.out.println(childrenMap.get("name0"));
            String name=intent.getStringExtra("name");
            System.out.println(name);
        }
    }
}
