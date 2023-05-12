package com.hongyongfeng.wanandroid.module.knowledge.view.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.home.view.adapter.ArticleAdapter;
import com.hongyongfeng.wanandroid.module.knowledge.interfaces.ArticleInterface;
import com.hongyongfeng.wanandroid.module.knowledge.view.presenter.ArticlePresenter;
import com.hongyongfeng.wanandroid.module.query.interfaces.LoadMoreInterface;
import com.hongyongfeng.wanandroid.module.query.presenter.LoadMorePresenter;
import com.hongyongfeng.wanandroid.module.query.view.fragment.LoadingFragment;
import com.hongyongfeng.wanandroid.module.webview.view.WebViewActivity;
import com.hongyongfeng.wanandroid.util.SetRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeArticleFragment extends BaseFragment<ArticlePresenter, ArticleInterface.VP> {
    @Override
    public ArticleInterface.VP getContract() {
        return new ArticleInterface.VP() {
            @Override
            public void requestArticleVP(int id, int page) {
                mPresenter.getContract().requestArticleVP(id,page);
            }

            @Override
            public void responseArticleVP(List<ArticleBean> articleLists) {
                System.out.println(articleLists);
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (articleLists.size() != 0){
                            articleList.addAll(articleLists);
                            adapter.notifyDataSetChanged();
                            //adapter.notifyItemInserted(articleList.size());
                        }else {
                            Toast.makeText(fragmentActivity, "已加载全部内容", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                        dialog.dismiss();
                    }
                });
            }
        };
    }
    private final List<ArticleBean> articleList=new ArrayList<>();
    private FragmentActivity fragmentActivity;
    private int id;
    private final ArticleAdapter adapter=new ArticleAdapter(articleList);
    private RecyclerView recyclerView;
    private ProgressDialog dialog;
    private int page=1;
    private int count=0;
    private FragmentTransaction transaction;
    @Override
    protected void destroy() {

    }
    @Override
    protected void initView(View view) {
        Log.d("init","success");
        recyclerView= fragmentActivity.findViewById(R.id.rv_article);
        if (count==0){
            SetRecyclerView.setRecyclerView(fragmentActivity,recyclerView,adapter);
            count=1;
        }
    }
    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return false;
        }
        return recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
    }

    @Override
    protected void initData() {

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
        return R.layout.fragment_query_article;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        fragmentActivity = requireActivity();
        if (getArguments() != null) {
            //articleBeanList = getArguments().getParcelableArrayList("list");
            id=getArguments().getInt("id");
            System.out.println("id"+id);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("fra","oncreate");
        View view = inflater.inflate(R.layout.fragment_query_article,container, false);
        recyclerView= view.findViewById(R.id.rv_article);
        SetRecyclerView.setRecyclerView(fragmentActivity,recyclerView,adapter);
        mPresenter=getPresenterInstance();
        mPresenter.bindView(this);
        dialog=ProgressDialog.show(requireActivity(), "", "正在加载", false, true);
        articleList.clear();
        getContract().requestArticleVP(id,0);
//        loadFragment();
//        if (!loadingFragment.isAdded()){
//            transaction.hide(this).add(R.id.fragment_query,loadingFragment).show(loadingFragment).commit();
//        }else {
//            transaction.hide(this).show(loadingFragment).commit();
//        }
        //transaction.replace(R.id.loading,loadingFragment).commit();
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {   // 不在最前端显示 相当于调用了onPause();
            //System.out.println("hide");;
        }else{  // 在最前端显示 相当于调用了onResume();
            //System.out.println("show");//网络数据刷新
//            if (getArguments() != null) {
//                articleBeanList = getArguments().getParcelableArrayList("list");
//            }
//            loadData();
        }
    }
    private void loadFragment(){
        FragmentManager fragmentManager = getChildFragmentManager();
        transaction= fragmentManager.beginTransaction();
    }
    @SuppressLint("NotifyDataSetChanged")
    private void loadData() {

//        if (articleList.size()==0){
//            articleList.addAll(articleBeanList);
//        }else {
//            articleList.clear();
//            articleList.addAll(articleBeanList);
//        }
//        adapter.notifyDataSetChanged();
//        recyclerView.scrollToPosition(0);

        //adapter.notifyItemRangeChanged(0,articleList.size());
    }

    @Override
    public void onClick(View v) {
    }
    @Override
    protected void initListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isSlideToBottom(recyclerView)) {
                    dialog = ProgressDialog.show(requireActivity(), "", "正在加载", false, true);
                    getContract().requestArticleVP(id,page);
                    //Toast.makeText(fragmentActivity, "正在加载", Toast.LENGTH_SHORT).show();
                    page++;
                }
            }
        });

        //System.out.println(recyclerView.canScrollVertically(-1));
        //判断是否滑动到底部
        //返回false表示不能往上滑动，即代表到底部了
        adapter.setOnItemClickListener(new ArticleAdapter.OnItemClickListener() {
            @Override
            public void onLikesClicked(View view, int position, TextView likes, int[] count) {
                int number2 = 2;
                int number0 = 0;
                if (count[0] % number2 == number0) {
                    likes.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_likes, null));
                    Toast.makeText(fragmentActivity, "点赞成功", Toast.LENGTH_SHORT).show();
                } else {
                    likes.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_likes_gray, null));
                    Toast.makeText(fragmentActivity, "取消点赞", Toast.LENGTH_SHORT).show();
                }
                count[0]++;
            }

            @Override
            public void onArticleClicked(View view, int position) {
                Intent intent = new Intent(fragmentActivity, WebViewActivity.class);
                intent.putExtra("url", articleList.get(position).getLink());
                startActivity(intent);
            }
        });
    }
}
