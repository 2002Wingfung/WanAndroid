package com.hongyongfeng.wanandroid.module.knowledge.view.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.home.view.adapter.ArticleAdapter;
import com.hongyongfeng.wanandroid.module.knowledge.interfaces.ArticleInterface;
import com.hongyongfeng.wanandroid.module.knowledge.presenter.ArticlePresenter;
import com.hongyongfeng.wanandroid.module.webview.view.WebViewActivity;
import com.hongyongfeng.wanandroid.util.SetRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeArticleFragment extends BaseFragment<ArticlePresenter, ArticleInterface.VP> {
    private Handler mHandler=new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

        }
    };
    @Override
    public ArticleInterface.VP getContract() {
        return new ArticleInterface.VP() {
            @Override
            public void requestArticleVP(int id, int page) {
                mPresenter.getContract().requestArticleVP(id,page);
            }

            @Override
            public void responseArticleVP(List<ArticleBean> articleLists) {
                //System.out.println(articleLists);
                requireActivity().runOnUiThread(new Runnable() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void run() {
                        if (articleLists.size() != 0){
                            articleList.addAll(articleLists);
                            adapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(fragmentActivity, "已加载全部内容", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
            }
        };
    }
    private int count=0;
    private final List<ArticleBean> articleList=new ArrayList<>();
    private FragmentActivity fragmentActivity;
    private int id;
    private final ArticleAdapter adapter=new ArticleAdapter(articleList);
    private RecyclerView recyclerView;
    private ProgressDialog dialog;
    private int page=1;
    private boolean loadMore = false;

    private FragmentTransaction transaction;
    @Override
    protected void destroy() {

    }
    @Override
    protected void initView(View view) {
//        recyclerView= fragmentActivity.findViewById(R.id.rv_article);
//        if (count==0){
//            SetRecyclerView.setRecyclerView(fragmentActivity,recyclerView,adapter);
//            count=1;
//        }
    }
    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return false;
        }
        if (recyclerView.computeVerticalScrollExtent()<recyclerView.computeVerticalScrollRange()){
            loadMore=true;
        }
//        System.out.println("屏幕显示的区域高度"+recyclerView.computeVerticalScrollExtent());
//        System.out.println("滑过的高度"+recyclerView.computeVerticalScrollOffset());
//        System.out.println("控件的高度"+recyclerView.computeVerticalScrollRange());
        return recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange();
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
            //System.out.println("id"+id);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_query_article,container, false);
        recyclerView= view.findViewById(R.id.rv_article);
        SetRecyclerView.setRecyclerView(fragmentActivity,recyclerView,adapter);
        mPresenter=getPresenterInstance();
        mPresenter.bindView(this);
        dialog=ProgressDialog.show(fragmentActivity, "", "正在加载", false, false);
        articleList.clear();
        getContract().requestArticleVP(id,0);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loadMore=false;//视图销毁将变量置为false
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
                if (isSlideToBottom(recyclerView)){
                    if (loadMore){
                        //有bug
                        dialog = ProgressDialog.show(requireActivity(), "", "正在加载", false, false);
                        getContract().requestArticleVP(id,page);
                        //Toast.makeText(fragmentActivity, "正在加载", Toast.LENGTH_SHORT).show();
                        page++;
                    }
                }

                if (isSlideToBottom(recyclerView)) {
                    loadMore=true;
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
