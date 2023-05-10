package com.hongyongfeng.wanandroid.module.query.view.fragment;

import static android.content.Intent.getIntent;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.home.view.adapter.ArticleAdapter;
import com.hongyongfeng.wanandroid.module.query.interfaces.LoadMoreInterface;
import com.hongyongfeng.wanandroid.module.query.presenter.LoadMorePresenter;
import com.hongyongfeng.wanandroid.module.webview.view.WebViewActivity;
import com.hongyongfeng.wanandroid.util.SetRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ArticleFragment extends BaseFragment<LoadMorePresenter, LoadMoreInterface.VP> {
    @Override
    public LoadMoreInterface.VP getContract() {
        return new LoadMoreInterface.VP() {
            @Override
            public void requestLoadMoreVP(String key,int page) {
                mPresenter.getContract().requestLoadMoreVP(key,page);
            }

            @Override
            public void responseLoadMoreVP(List<ArticleBean> articleLists) {
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        articleList.addAll(articleLists);
                        adapter.notifyItemInserted(articleList.size());
                        dialog.dismiss();
                    }
                });
            }
        };
    }
    public List<ArticleBean> articleList=new ArrayList<>();
    private FragmentActivity fragmentActivity;
    List<ArticleBean> articleBeanList = null;

    ArticleAdapter adapter=new ArticleAdapter(articleList);
    RecyclerView recyclerView;
    static ProgressDialog dialog;
    private int page=1;
    EditText edtQuery;
    View view;
    @Override
    protected void destroy() {

    }

    @Override
    protected void initView(View view) {
        recyclerView= fragmentActivity.findViewById(R.id.rv_article);
        SetRecyclerView.setRecyclerView(fragmentActivity,recyclerView,adapter);
        edtQuery=fragmentActivity.findViewById(R.id.edt_keyword);
    }
    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return false;
        }
        return recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange();
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
                //LogUtils.d("------->isSlideToBottom:" + isSlideToBottom(recyclerView));
                if (isSlideToBottom(recyclerView)) {
                    //srlLayout.setEnabled(true);
                    //Toast.makeText(fragmentActivity, edtQuery.getText().toString(), Toast.LENGTH_SHORT).show();
                    dialog = ProgressDialog.show(requireActivity(), "", "正在加载", false, false);
                    getContract().requestLoadMoreVP(edtQuery.getText().toString(),page);
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

    @Override
    protected void initData() {

    }

    @Override
    protected LoadMorePresenter getPresenterInstance() {
        return new LoadMorePresenter();
    }

    @Override
    protected <ERROR> void responseError(ERROR error, Throwable throwable) {

    }

    @Override
    protected int getFragmentView() {
        return R.layout.fragment_query_article;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        fragmentActivity = requireActivity();
        if (getArguments() != null) {
            articleBeanList = getArguments().getParcelableArrayList("list");
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {   // 不在最前端显示 相当于调用了onPause();
            //System.out.println("hide");;
        }else{  // 在最前端显示 相当于调用了onResume();
            //System.out.println("show");//网络数据刷新

            if (getArguments() != null) {
                articleBeanList = getArguments().getParcelableArrayList("list");
//                articleBeanList.remove(0);
//                articleBeanList.remove(0);
            }
            loadData();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadData() {
        if (articleList.size()==0){
            articleList.addAll(articleBeanList);
        }else {
            articleList.clear();
            articleList.addAll(articleBeanList);
        }
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(0);
        //adapter.notifyItemRangeChanged(0,articleList.size());
    }

    @Override
    public void onClick(View v) {

    }
}
