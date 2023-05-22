package com.hongyongfeng.wanandroid.module.knowledge.view.activity;

import static com.hongyongfeng.wanandroid.util.Constant.TWO;
import static com.hongyongfeng.wanandroid.util.Constant.ZERO;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseActivity;
import com.hongyongfeng.wanandroid.base.BasePresenter;
import com.hongyongfeng.wanandroid.module.knowledge.view.fragment.KnowledgeArticleFragment;
import com.hongyongfeng.wanandroid.module.project.view.adapter.ProjectCategoryAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Wingfung Hung
 */
public class TabActivity extends BaseActivity {
    private TextView tvTitle;
    private TextView tvBack;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> fragmentList;
    private List<String> categoryList;

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
            for (int i=ZERO;i<childrenMap.size()/TWO;i++){
                String name=(String) childrenMap.get("name"+i);
                categoryList.add(name);
                KnowledgeArticleFragment fragment=new KnowledgeArticleFragment();
                Bundle bundle=new Bundle();
                bundle.putInt("id",(int)childrenMap.get("id"+i));
                fragment.setArguments(bundle);
                fragmentList.add(fragment);
            }
            String name=intent.getStringExtra("name");
            tvTitle.setText(name);
        }
        ProjectCategoryAdapter adapter = new ProjectCategoryAdapter(getSupportFragmentManager(),
                fragmentList, categoryList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(TWO);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initListener() {
        tvBack.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        fragmentList=new ArrayList<>();
        categoryList=new ArrayList<>();
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
        viewPager=findViewById(R.id.vp_knowledge);
        tabLayout=findViewById(R.id.tab_layout);
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
