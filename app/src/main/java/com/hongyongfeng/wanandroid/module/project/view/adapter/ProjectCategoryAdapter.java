package com.hongyongfeng.wanandroid.module.project.view.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class ProjectCategoryAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> categoryList;

    public ProjectCategoryAdapter(@NonNull FragmentManager fm,
                                  List<Fragment> fragmentList,
                                  List<String> categoryList) {
        //super(fm);
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        //懒加载
        this.fragmentList=fragmentList;
        this.categoryList=categoryList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList==null?null:fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList==null?0:fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categoryList==null?"":categoryList.get(position);
    }
}
