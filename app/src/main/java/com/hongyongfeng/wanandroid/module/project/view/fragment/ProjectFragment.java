package com.hongyongfeng.wanandroid.module.project.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.module.home.interfaces.HomeFragmentInterface;
import com.hongyongfeng.wanandroid.module.project.interfaces.ProjectFragmentInterface;
import com.hongyongfeng.wanandroid.module.project.presenter.ProjectFragmentPresenter;
import com.hongyongfeng.wanandroid.test.VPFragment;

import java.util.ArrayList;
import java.util.List;

public class ProjectFragment extends BaseFragment<ProjectFragmentPresenter, ProjectFragmentInterface.VP>{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ViewPager viewPager;
    private TableLayout tableLayout;
    private List<Fragment> fragmentList;
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
    protected void destroy() {

    }

    @Override
    protected void initView(View view) {
        viewPager=view.findViewById(R.id.vp_project);
        tableLayout=view.findViewById(R.id.tab_layout);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        fragmentList=new ArrayList<>();
        VPFragment fragment1=VPFragment.newInstance("推荐","");
        VPFragment fragment2=VPFragment.newInstance("关注","");
        VPFragment fragment3=VPFragment.newInstance("娱乐","");
        VPFragment fragment4=VPFragment.newInstance("时政","");
        VPFragment fragment5=VPFragment.newInstance("汽车","");
        VPFragment fragment6=VPFragment.newInstance("历史","");
        VPFragment fragment7=VPFragment.newInstance("地理","");
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        fragmentList.add(fragment4);
        fragmentList.add(fragment5);
        fragmentList.add(fragment6);
        fragmentList.add(fragment7);
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
