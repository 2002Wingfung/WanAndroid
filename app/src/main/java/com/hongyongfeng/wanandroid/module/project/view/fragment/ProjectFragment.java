package com.hongyongfeng.wanandroid.module.project.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.module.project.interfaces.ProjectFragmentInterface;
import com.hongyongfeng.wanandroid.module.project.presenter.ProjectFragmentPresenter;
import com.hongyongfeng.wanandroid.module.project.view.adapter.ProjectCategoryAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Wingfung Hung
 */
public class ProjectFragment extends BaseFragment<ProjectFragmentPresenter, ProjectFragmentInterface.Vp>{
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> fragmentList;
    private ProjectCategoryAdapter adapter;
    private List<String> categoryList;
    private FragmentActivity activity;
    private ProgressDialog dialog;
    private int count=0;
    @Override
    public ProjectFragmentInterface.Vp getContract() {
        return new ProjectFragmentInterface.Vp() {
            @Override
            public void requestTitleVp() {
                mPresenter.getContract().requestTitleVp();
            }

            @Override
            public void responseTitleResult(List<Map<String,Object>> titleMapList) {
                activity.runOnUiThread(() -> {
                    for (Map<String,Object> titleMap:titleMapList) {
                        categoryList.add((String) titleMap.get("name"));
                        ProjectArticleFragment fragment=new ProjectArticleFragment();
                        Bundle bundle=new Bundle();
                        bundle.putInt("id",(int)titleMap.get("id"));
                        fragment.setArguments(bundle);
                        fragmentList.add(fragment);
                    }
                    adapter=new ProjectCategoryAdapter(getChildFragmentManager(),
                            fragmentList,categoryList);
                    viewPager.setAdapter(adapter);
                    viewPager.setOffscreenPageLimit(2);
                    tabLayout.setupWithViewPager(viewPager);
                    dialog.dismiss();
                });
            }
        };
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity=requireActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (count==0){
            getContract().requestTitleVp();
            dialog = ProgressDialog.show(requireActivity(), "", "正在加载", false, false);
            count=1;
        }
    }

    @Override
    protected void destroy() {
    }

    @Override
    protected void initView(View view) {
        viewPager=view.findViewById(R.id.vp_project);
        tabLayout=view.findViewById(R.id.tab_layout);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        fragmentList=new ArrayList<>();
        categoryList=new ArrayList<>();
    }

    @Override
    protected ProjectFragmentPresenter getPresenterInstance() {
        return new ProjectFragmentPresenter();
    }

    @Override
    protected <ERROR> void responseError(ERROR error, Throwable throwable) {
    }

    @Override
    protected int getFragmentView() {
        return R.layout.fragment_project;
    }

    @Override
    public void onClick(View v) {
    }
}
