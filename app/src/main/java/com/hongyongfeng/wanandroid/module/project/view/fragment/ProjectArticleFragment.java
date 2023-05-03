package com.hongyongfeng.wanandroid.module.project.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.data.net.bean.ProjectBean;
import com.hongyongfeng.wanandroid.module.project.interfaces.ProjectFragmentInterface;
import com.hongyongfeng.wanandroid.module.project.presenter.ProjectFragmentPresenter;
import com.hongyongfeng.wanandroid.module.project.view.adapter.ProjectAdapter;
import com.hongyongfeng.wanandroid.util.SetRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProjectArticleFragment extends BaseFragment<ProjectFragmentPresenter, ProjectFragmentInterface.VP> {
    @SuppressLint("StaticFieldLeak")
    private FragmentActivity fragmentActivity;

    List<ProjectBean> projectList =new ArrayList<>();
    ProjectAdapter adapter=new ProjectAdapter(projectList);
    RecyclerView recyclerView;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fragmentActivity=requireActivity();
        super.onViewCreated(view, savedInstanceState);
        SetRecyclerView.setRecyclerView(fragmentActivity,recyclerView,adapter);
    }

    @Override
    protected void destroy() {

    }

    @Override
    protected void initView(View view) {
        recyclerView= fragmentActivity.findViewById(R.id.rv_project);
    }

    @Override
    protected void initListener() {
        adapter.setOnItemClickListener(new ProjectAdapter.OnItemClickListener() {
            @Override
            public void onLikesClicked(View view, int position, TextView likes, int[] count) {

            }

            @Override
            public void onArticleClicked(View view, int position) {

            }
        });
    }

    @Override
    protected void initData() {

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
        return R.layout.fragment_project_article;
    }


    @Override
    public void onClick(View v) {

    }
}
