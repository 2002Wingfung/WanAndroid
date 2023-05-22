package com.hongyongfeng.wanandroid.module.query.view.fragment;

import static com.hongyongfeng.wanandroid.util.Constant.ONE;
import static com.hongyongfeng.wanandroid.util.Constant.TWO;
import static com.hongyongfeng.wanandroid.util.Constant.ZERO;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import com.hongyongfeng.wanandroid.module.query.interfaces.LoadMoreInterface;
import com.hongyongfeng.wanandroid.module.query.presenter.LoadMorePresenter;
import com.hongyongfeng.wanandroid.module.signinorup.SignInUpActivity;
import com.hongyongfeng.wanandroid.module.webview.view.WebViewActivity;
import com.hongyongfeng.wanandroid.util.SetRecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wingfung Hung
 */
public class ArticleFragment extends BaseFragment<LoadMorePresenter, LoadMoreInterface.Vp> {
    @Override
    public LoadMoreInterface.Vp getContract() {
        return new LoadMoreInterface.Vp() {
            @Override
            public void requestLoadMoreVp(String key, int page) {
                mPresenter.getContract().requestLoadMoreVp(key,page);
            }

            @Override
            public void responseLoadMoreVp(List<ArticleBean> articleLists) {
                fragmentActivity.runOnUiThread(() -> {
                    if (articleLists.size() != ZERO){
                        articleList.addAll(articleLists);
                        adapter.notifyItemInserted(articleList.size());
                    }else {
                        Toast.makeText(fragmentActivity, "已加载全部内容", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                });
            }

            @Override
            public void error(Exception e) {
                e.printStackTrace();
                fragmentActivity.runOnUiThread(() -> {
                    Toast.makeText(fragmentActivity, "网络请求错误", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                });
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
            public void saveHistory(ArticleBean article) {
                mPresenter.getContract().saveHistory(article);
            }
        };
    }
    private final List<ArticleBean> articleList=new ArrayList<>();
    private FragmentActivity fragmentActivity;
    private List<ArticleBean> articleBeanList = null;
    private final ArticleAdapter adapter=new ArticleAdapter(articleList);
    private RecyclerView recyclerView;
    static ProgressDialog dialog;
    private int page=ONE;
    private EditText edtQuery;
    private int count=ZERO;
    @Override
    protected void destroy() {

    }
    @Override
    protected void initView(View view) {
        recyclerView= fragmentActivity.findViewById(R.id.rv_article);
        if (count==ZERO){
            SetRecyclerView.setRecyclerView(fragmentActivity,recyclerView,adapter);
            count=ONE;
        }
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
                if (isSlideToBottom(recyclerView)) {
                    dialog = ProgressDialog.show(fragmentActivity, "", "正在加载", false, false);
                    getContract().requestLoadMoreVp(edtQuery.getText().toString(),page);
                    page++;
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
                Intent intent = new Intent(fragmentActivity, WebViewActivity.class);
                ArticleBean article=articleList.get(position);
                intent.putExtra("url", article.getLink());
                startActivity(intent);
                getContract().saveHistory(article);
            }
        });
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
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
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
        //hidden为true，则不在最前端显示 相当于调用了onPause();

        if (!hidden){
            // hidden为false,则在最前端显示，相当于调用了onResume();
            if (getArguments() != null) {
                articleBeanList = getArguments().getParcelableArrayList("list");
            }
            loadData();
        }
        super.onHiddenChanged(hidden);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadData() {
        if (articleList.size()==ZERO){
            articleList.addAll(articleBeanList);
        }else {
            articleList.clear();
            articleList.addAll(articleBeanList);
        }
        articleBeanList.clear();
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(ZERO);
    }

    @Override
    public void onClick(View v) {
    }
}
