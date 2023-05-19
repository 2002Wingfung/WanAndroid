package com.hongyongfeng.wanandroid.test;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import java.util.List;

/**
 * Fragment的适配器
 * @author Wingfung Hung
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm, List<Fragment> fragmentList) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        //super(fm);
        this.fragmentList=fragmentList;
    }

    private final List<Fragment> fragmentList;

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
        //使得不能销毁碎片,避免了切换Fragment时重新加载的问题
    }
}
