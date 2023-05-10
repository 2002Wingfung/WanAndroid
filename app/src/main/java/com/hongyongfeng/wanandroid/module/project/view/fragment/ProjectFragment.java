package com.hongyongfeng.wanandroid.module.project.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.module.project.interfaces.ProjectFragmentInterface;
import com.hongyongfeng.wanandroid.module.project.presenter.ProjectFragmentPresenter;
import com.hongyongfeng.wanandroid.module.project.view.adapter.ProjectCategoryAdapter;
import com.hongyongfeng.wanandroid.test.VPFragment;

import java.util.ArrayList;
import java.util.List;

public class ProjectFragment extends BaseFragment<ProjectFragmentPresenter, ProjectFragmentInterface.VP>{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> fragmentList;
    private ProjectCategoryAdapter adapter;
    private List<String> categoryList;
    public static ProjectFragment newInstance(String param1, String param2) {
        ProjectFragment fragment = new ProjectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public ProjectFragmentInterface.VP getContract() {
        return new ProjectFragmentInterface.VP() {
            @Override
            public void requestLoginVP(String name, String pwd) {

            }

            @Override
            public void responseLoginResult(boolean loginStatusResult) {

            }
        };
    }

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_project, container, false);
//        //待修改
//    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //Log.d("ProjectFragment","onCreate"+SystemClock.elapsedRealtime());
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Log.d("ProjectFragment","onCreateView"+SystemClock.elapsedRealtime());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Log.d("ProjectFragment","onViewCreated"+SystemClock.elapsedRealtime());
        super.onViewCreated(view, savedInstanceState);
        adapter=new ProjectCategoryAdapter(getChildFragmentManager(),
                fragmentList,categoryList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        //Log.d("ProjectFragment","onViewStateRestored"+SystemClock.elapsedRealtime());
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onStart() {
        //Log.d("ProjectFragment","onStart"+SystemClock.elapsedRealtime());
        super.onStart();
    }

    @Override
    public void onResume() {
        //Log.d("ProjectFragment","onResume"+SystemClock.elapsedRealtime());
        super.onResume();
    }

    @Override
    public void onPause() {
        //Log.d("ProjectFragment","onPause"+SystemClock.elapsedRealtime());
        super.onPause();
    }

    @Override
    public void onStop() {
        //Log.d("ProjectFragment","onStop"+ SystemClock.elapsedRealtime());
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //Log.d("ProjectFragment","onSaveInstanceState"+SystemClock.elapsedRealtime());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        //Log.d("ProjectFragment","onDestroyView"+SystemClock.elapsedRealtime());
        super.onDestroyView();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onDestroy() {
        //Log.d("ProjectFragment","onDestroy"+SystemClock.elapsedRealtime());
        super.onDestroy();
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
        VPFragment fragment1=VPFragment.newInstance("推荐","");
        VPFragment fragment2=VPFragment.newInstance("关注","");
        VPFragment fragment3=VPFragment.newInstance("娱乐","");
        VPFragment fragment4=VPFragment.newInstance("时政","");
        VPFragment fragment5=VPFragment.newInstance("汽车","");
        VPFragment fragment6=VPFragment.newInstance("历史","");
        VPFragment fragment7=VPFragment.newInstance("地理","");

        fragmentList.add(new ProjectArticleFragment());
        //在new对象时在构造函数中传入url，然后再在articleFragment中根据url初始化recyclerview
        fragmentList.add(ProjectArticleFragment.newInstance("nihao",""));
//        fragmentList.add(new ProjectArticleFragment());
//        fragmentList.add(new ProjectArticleFragment());
//        fragmentList.add(new ProjectArticleFragment());
//        fragmentList.add(new ProjectArticleFragment());
//        fragmentList.add(new ProjectArticleFragment());
        //fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        fragmentList.add(fragment4);
        fragmentList.add(fragment5);
        fragmentList.add(fragment6);
        fragmentList.add(fragment7);
        categoryList.add("推荐");
        categoryList.add("关注");
        categoryList.add("娱乐");
        categoryList.add("时政");
        categoryList.add("汽车");
        categoryList.add("历史");
        categoryList.add("地理");
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
