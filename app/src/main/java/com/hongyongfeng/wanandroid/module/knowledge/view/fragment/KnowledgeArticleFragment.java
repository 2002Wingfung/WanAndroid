package com.hongyongfeng.wanandroid.module.knowledge.view.fragment;

import static com.hongyongfeng.wanandroid.util.Constant.ONE;
import static com.hongyongfeng.wanandroid.util.Constant.TWO;
import static com.hongyongfeng.wanandroid.util.Constant.ZERO;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.CollectListener;
import com.hongyongfeng.wanandroid.module.home.view.adapter.ArticleAdapter;
import com.hongyongfeng.wanandroid.module.knowledge.interfaces.ArticleInterface;
import com.hongyongfeng.wanandroid.module.knowledge.presenter.ArticlePresenter;
import com.hongyongfeng.wanandroid.module.signinorup.SignInUpActivity;
import com.hongyongfeng.wanandroid.module.webview.view.WebViewActivity;
import com.hongyongfeng.wanandroid.util.SetRecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wingfung Hung
 */
public class KnowledgeArticleFragment extends BaseFragment<ArticlePresenter, ArticleInterface.ViewPresenter> {
    @Override
    public ArticleInterface.ViewPresenter getContract() {
        return new ArticleInterface.ViewPresenter() {
            @Override
            public void saveHistory(ArticleBean article) {
                mPresenter.getContract().saveHistory(article);
            }

            @Override
            public void collectVp(int id, CollectListener listener) {
                mPresenter.getContract().collectVp(id,listener);
            }

            @Override
            public void unCollectVp(int id, CollectListener listener) {
                mPresenter.getContract().unCollectVp(id,listener);

            }

            @Override
            public void collectResponse(int code) {
                fragmentActivity.runOnUiThread(() -> {
                    if (code==ZERO){
                        Toast.makeText(fragmentActivity, "点赞成功", Toast.LENGTH_SHORT).show();
                    }else if (code==ONE){
                        Toast.makeText(fragmentActivity, "还没登录", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(fragmentActivity, SignInUpActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(fragmentActivity, "点赞失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void unCollectResponse(int code) {
                fragmentActivity.runOnUiThread(() -> {
                    if (code==ZERO){
                        Toast.makeText(fragmentActivity, "取消点赞", Toast.LENGTH_SHORT).show();
                    }else if (code==ONE){
                        Toast.makeText(fragmentActivity, "还没登录", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(fragmentActivity, "点赞失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void requestArticleVp(int id, int page) {
                mPresenter.getContract().requestArticleVp(id,page);
            }

            @Override
            public void responseArticleVp(List<ArticleBean> articleLists) {
                //System.out.println(articleLists);
                requireActivity().runOnUiThread(new Runnable() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void run() {
                        if (articleLists.size() != ZERO){
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
    private final List<ArticleBean> articleList=new ArrayList<>();
    private FragmentActivity fragmentActivity;
    private int id;
    private final ArticleAdapter adapter=new ArticleAdapter(articleList);
    private RecyclerView recyclerView;
    private ProgressDialog dialog;
    private int page=ONE;
    private boolean loadMore = false;
    @Override
    protected void destroy() {

    }
    @Override
    protected void initView(View view) {
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
            id=getArguments().getInt("id");
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
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (articleList.size()==ZERO){
            dialog=ProgressDialog.show(fragmentActivity, "", "正在加载", false, false);
            getContract().requestArticleVp(id,ZERO);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loadMore=false;
        //视图销毁将变量置为false
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
                if (recyclerView.computeVerticalScrollExtent()!=recyclerView.computeVerticalScrollRange()){
                    if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange()){
                        dialog = ProgressDialog.show(requireActivity(), "", "正在加载", false, false);
                        getContract().requestArticleVp(id,page);
                        page++;
                    }
                }else {
                    View view=recyclerView.getChildAt(articleList.size());
                    if (view!=null){
                        ProgressBar bar=view.findViewById(R.id.progressBar);
                        bar.setVisibility(View.GONE);
                        TextView tv=view.findViewById(R.id.tv);
                        tv.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        adapter.setOnItemClickListener(new ArticleAdapter.OnItemClickListener() {
            @Override
            public void onLikesClicked(View view, int position, TextView likes, int[] count) {
                int number0 = ZERO;
                if (articleList.get(position).isCollect()){
                    number0=ONE;
                }
                if (count[ZERO] % TWO == number0) {
                    getContract().collectVp(articleList.get(position).getId(), new CollectListener() {
                        @Override
                        public void onFinish() {
                            likes.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_likes, null));
                        }
                        @Override
                        public void onError() {
                        }
                    });
                } else {
                    getContract().unCollectVp(articleList.get(position).getId(), new CollectListener() {
                        @Override
                        public void onFinish() {
                            likes.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_likes_gray, null));
                        }
                        @Override
                        public void onError() {
                        }
                    });
                }
                count[ZERO]++;
            }

            @Override
            public void onArticleClicked(View view, int position) {
                ArticleBean article=articleList.get(position);
                Intent intent = new Intent(fragmentActivity, WebViewActivity.class);
                intent.putExtra("url", article.getLink());
                startActivity(intent);
                getContract().saveHistory(article);
            }
        });
    }
}
