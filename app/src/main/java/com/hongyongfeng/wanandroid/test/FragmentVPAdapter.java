package com.hongyongfeng.wanandroid.test;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class FragmentVPAdapter extends FragmentStatePagerAdapter {
    public FragmentVPAdapter(@NonNull FragmentManager fm, List<Fragment> fragmentList) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        //super(fm);
        this.fragmentList=fragmentList;
    }


//    public FragmentVPAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Fragment> fragmentList) {
//        super(fragmentManager, lifecycle);
//        this.fragmentList = fragmentList;
//    }
//
//    public FragmentVPAdapter(@NonNull Fragment fragment, List<Fragment> fragmentList) {
//        super(fragment);
//        this.fragmentList = fragmentList;
//    }

    private List<Fragment> fragmentList;

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList==null?null:fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
    }
    //    @NonNull
//    @Override
//    public Fragment createFragment(int position) {
//        return fragmentList==null?null:fragmentList.get(position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return fragmentList.size();
//    }
}
