package com.hongyongfeng.wanandroid.module.project.view.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.data.net.bean.ProjectBean;
import com.hongyongfeng.wanandroid.module.project.interfaces.ArticleInterface;
import com.hongyongfeng.wanandroid.module.project.interfaces.ProjectFragmentInterface;
import com.hongyongfeng.wanandroid.module.project.presenter.ArticlePresenter;
import com.hongyongfeng.wanandroid.module.project.presenter.ProjectFragmentPresenter;
import com.hongyongfeng.wanandroid.module.project.view.adapter.ProjectAdapter;
import com.hongyongfeng.wanandroid.module.webview.view.WebViewActivity;
import com.hongyongfeng.wanandroid.test.VPFragment;
import com.hongyongfeng.wanandroid.util.SetRecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProjectArticleFragment extends BaseFragment<ArticlePresenter, ArticleInterface.VP> {
    @SuppressLint("StaticFieldLeak")
    private FragmentActivity fragmentActivity;

    private final List<ProjectBean> projectList =new ArrayList<>();
    private final List<Bitmap> bitmapLists =new ArrayList<>();

    private final ProjectAdapter adapter=new ProjectAdapter(projectList,bitmapLists);
    private RecyclerView recyclerView;
    private ProgressDialog dialog;
    private int page=1;
    private int id;
    private int position=0;

    private boolean loadMore = false;
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
    public ArticleInterface.VP getContract() {
        return new ArticleInterface.VP() {
            @Override
            public void requestTitleVP(int id,int page) {
                mPresenter.getContract().requestTitleVP(id,page);
            }

            @Override
            public void responseTitleResult(List<ProjectBean> projectLists) {
                fragmentActivity.runOnUiThread(new Runnable() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void run() {
                        if (projectLists.size()!=0){
                            projectList.addAll(projectLists);
                            //adapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(fragmentActivity, "已加载全部内容", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void responseImageResult(List<Bitmap> bitmapList) {
                fragmentActivity.runOnUiThread(new Runnable() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void run() {
                        //adapter.notifyItemChanged(position);
                        //position++;
                        bitmapLists.addAll(bitmapList);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
            }
        };
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentActivity=requireActivity();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            id=getArguments().getInt("id");
            System.out.println("id"+id);
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_article,container, false);
        recyclerView= view.findViewById(R.id.rv_project);
        SetRecyclerView.setRecyclerView(fragmentActivity,recyclerView,adapter);
        mPresenter=getPresenterInstance();
        mPresenter.bindView(this);
        dialog= ProgressDialog.show(fragmentActivity, "", "正在加载", false, false);
        projectList.clear();
        getContract().requestTitleVP(id,page);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loadMore=false;//视图销毁将变量置为false
    }
    @Override
    protected void destroy() {

    }

    @Override
    protected void initView(View view) {
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
                intent.putExtra("url", projectList.get(position).getLink());
                startActivity(intent);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isSlideToBottom(recyclerView)){
                    if (loadMore){
                        page++;
                        //System.out.println("load");
                        dialog = ProgressDialog.show(fragmentActivity, "", "正在加载", false, false);
                        getContract().requestTitleVP(id,page);
                    }
                }
                if (isSlideToBottom(recyclerView)) {
                    loadMore=true;
                }
            }
        });
    }
    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return false;
        }
        if (recyclerView.computeVerticalScrollExtent()<recyclerView.computeVerticalScrollRange()){
            loadMore=true;
        }
        return recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange();
    }
    @Override
    protected void initData() {
//        if (projectList.size()==0){
//            StringBuilder str=new StringBuilder("abc ");
//            for (int i =1;i<11;i++){
//                projectList.add(new ProjectBean("项目"+i,str.toString()));
//                str.append(i).append("nihao  ");
//            }
//        }
    }

    @Override
    protected ArticlePresenter getPresenterInstance() {
        return new ArticlePresenter();
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
