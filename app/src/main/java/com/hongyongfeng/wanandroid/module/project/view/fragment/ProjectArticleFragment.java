package com.hongyongfeng.wanandroid.module.project.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.data.net.bean.ProjectBean;
import com.hongyongfeng.wanandroid.module.project.interfaces.ProjectFragmentInterface;
import com.hongyongfeng.wanandroid.module.project.presenter.ProjectFragmentPresenter;
import com.hongyongfeng.wanandroid.module.project.view.adapter.ProjectAdapter;
import com.hongyongfeng.wanandroid.module.webview.view.WebViewActivity;
import com.hongyongfeng.wanandroid.test.VPFragment;
import com.hongyongfeng.wanandroid.util.SetRecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProjectArticleFragment extends BaseFragment<ProjectFragmentPresenter, ProjectFragmentInterface.VP> {
    @SuppressLint("StaticFieldLeak")
    private FragmentActivity fragmentActivity;

    List<ProjectBean> projectList =new ArrayList<>();
    ProjectAdapter adapter=new ProjectAdapter(projectList);
    RecyclerView recyclerView;
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static ProjectArticleFragment newInstance(String param1, String param2) {
        ProjectArticleFragment fragment = new ProjectArticleFragment();
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
            public void requestTitleVP() {
                mPresenter.getContract().requestTitleVP();
            }

            @Override
            public void responseTitleResult(List<Map<String,Object>> titleMapList) {
                System.out.println(titleMapList);
            }
        };
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fragmentActivity=requireActivity();
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_article,container, false);
        recyclerView= view.findViewById(R.id.rv_project);
        fragmentActivity=requireActivity();
        SetRecyclerView.setRecyclerView(fragmentActivity,recyclerView,adapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
                int number2=2;
                int number0=0;
                if (count[0]%number2==number0){
                    likes.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_likes,null));
                    Toast.makeText(fragmentActivity, "点赞成功", Toast.LENGTH_SHORT).show();
                }else {
                    likes.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_likes_gray,null));
                    Toast.makeText(fragmentActivity, "取消点赞", Toast.LENGTH_SHORT).show();
                }
                count[0]++;
            }

            @Override
            public void onArticleClicked(View view, int position) {
                Intent intent=new Intent(fragmentActivity, WebViewActivity.class);
                intent.putExtra("url","https://www.baidu.com");
                startActivity(intent);
                Toast.makeText(fragmentActivity, "点击了view"+(position+1), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initData() {
        if (projectList.size()==0){
            StringBuilder str=new StringBuilder("abc ");
            for (int i =1;i<11;i++){
                projectList.add(new ProjectBean("项目"+i,str.toString()));
                str.append(i).append("nihao  ");
            }
        }
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
