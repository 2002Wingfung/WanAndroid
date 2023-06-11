package com.hongyongfeng.wanandroid.module.main.activity;

import static com.hongyongfeng.wanandroid.util.Constant.ONE;
import static com.hongyongfeng.wanandroid.util.Constant.TWO;
import static com.hongyongfeng.wanandroid.util.Constant.ZERO;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseActivity;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.CollectListener;
import com.hongyongfeng.wanandroid.module.home.view.adapter.ArticleAdapter;
import com.hongyongfeng.wanandroid.module.main.interfaces.MoreInterface;
import com.hongyongfeng.wanandroid.module.main.presenter.MorePresenter;
import com.hongyongfeng.wanandroid.module.signinorup.SignInUpActivity;
import com.hongyongfeng.wanandroid.module.webview.view.WebViewActivity;
import com.hongyongfeng.wanandroid.util.SetRecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wingfung Hung
 */
public class MoreActivity extends BaseActivity<MorePresenter, MoreInterface.Vp>{
    private TextView tvBack;
    private TextView tvTitle;
    private RecyclerView recyclerView;
    /**
     * 可以是网络获取的bean,也可以是本地获取的bean
     */
    private final ArrayList<ArticleBean> articleBeanLists=new ArrayList<>();
    private final ArticleAdapter adapter=new ArticleAdapter(articleBeanLists);
    private ProgressDialog dialog;
    private int page=ZERO;
    @Override
    public MoreInterface.Vp getContract() {
        return new MoreInterface.Vp() {
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
            public void requestHistoryVp() {
                mPresenter.getContract().requestHistoryVp();
            }

            @Override
            public void responseHistoryVp(List<ArticleBean> articleBeanList) {
                articleBeanLists.addAll(articleBeanList);
                MoreActivity.this.runOnUiThread(new Runnable() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void requestCollectVp(int page) {
                mPresenter.getContract().requestCollectVp(page);
            }

            @Override
            public void responseCollectVp(List<ArticleBean> articleList) {
                if (articleList!=null){
                    if (articleList.size()!=0){
                        for (ArticleBean article:articleList) {
                            article.setCollect(true);
                        }
                        articleBeanLists.addAll(articleList);
                        MoreActivity.this.runOnUiThread(new Runnable() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });
                    }else {
                        MoreActivity.this.runOnUiThread(new Runnable() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void run() {
                                dialog.dismiss();
                                Toast.makeText(MoreActivity.this, "加载完成", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }else {
                    MoreActivity.this.runOnUiThread(new Runnable() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void run() {
                            dialog.dismiss();
                            Toast.makeText(MoreActivity.this, "加载完成", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void collectResponse(int code) {
                runOnUiThread(() -> {
                    if (code==ZERO){
                        Toast.makeText(MoreActivity.this, "点赞成功", Toast.LENGTH_SHORT).show();
                    }else if (code==ONE){
                        Toast.makeText(MoreActivity.this, "还没登录", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MoreActivity.this, SignInUpActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                        finish();
                    }else {
                        Toast.makeText(MoreActivity.this, "点赞失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void unCollectResponse(int code) {
                runOnUiThread(() -> {
                    if (code==ZERO){
                        Toast.makeText(MoreActivity.this, "取消点赞", Toast.LENGTH_SHORT).show();
                    }else if (code==ONE){
                        Toast.makeText(MoreActivity.this, "还没登录", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MoreActivity.this, "点赞失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
    }

    @Override
    public void initListener() {
        tvBack.setOnClickListener(this);
        //如果是展示收藏的文章的话就要设置recyclerview的滑动监听
        //如果是浏览历史记录的话就不用设置recyclerview的滑动监听
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
                        if (index == 0) {
                            page++;
                            dialog = ProgressDialog.show(MoreActivity.this, "", "正在加载", false, false);
                            getContract().requestCollectVp(page);
                        }
                    }
                }else {
                    View view=recyclerView.getChildAt(articleBeanLists.size());
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
                int number2 = TWO;
                int number0 = ZERO;
                if (MoreActivity.this.count ==ZERO){
                    number0=ONE;
                    if (count[0] % number2 == number0) {
                        getContract().collectVp(articleBeanLists.get(position).getId(), new CollectListener() {
                            @Override
                            public void onFinish() {
                                likes.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_likes, null));
                            }
                            @Override
                            public void onError() {
                            }
                        });
                    } else {
                        getContract().unCollectVp(articleBeanLists.get(position).getId(), new CollectListener() {
                            @Override
                            public void onFinish() {
                                likes.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_likes_gray, null));
                            }
                            @Override
                            public void onError() {
                            }
                        });
                    }
                }else {
                    if (count[ZERO] % number2 == number0) {
                        likes.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_likes, null));
                        Toast.makeText(MoreActivity.this, "点赞成功", Toast.LENGTH_SHORT).show();
                    } else {
                        likes.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_likes_gray, null));
                        Toast.makeText(MoreActivity.this, "取消点赞", Toast.LENGTH_SHORT).show();
                    }
                }
                count[ZERO]++;
            }
            @Override
            public void onArticleClicked(View view, int position) {
                Intent intent = new Intent(MoreActivity.this, WebViewActivity.class);
                ArticleBean article=articleBeanLists.get(position);
                intent.putExtra("url", article.getLink());
                startActivity(intent);
                getContract().saveHistory(article);
            }
        });
    }

    @Override
    public void initData() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_more;
    }


    @Override
    public MorePresenter getPresenterInstance() {
        return new MorePresenter();
    }

    @Override
    public <ERROR> void responseError(ERROR error, Throwable throwable) {
    }
    @Override
    public void initView() {
        tvBack=findViewById(R.id.tv_back);
        tvTitle=findViewById(R.id.tv_title);
        recyclerView= findViewById(R.id.rv_article1);
        SetRecyclerView.setRecyclerView(this,recyclerView,adapter);
    }


    private int index=-ONE;
    private int count =ZERO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        if (intent!=null){
            String title=intent.getStringExtra("title");
            tvTitle.setText(title);
            index=intent.getIntExtra("index",-ONE);
            switch (index){
                case ZERO:
                    dialog = ProgressDialog.show(MoreActivity.this, "", "正在加载", false, false);
                    getContract().requestCollectVp(ZERO);
                    count=ZERO;
                    break;
                case ONE:
                    dialog = ProgressDialog.show(MoreActivity.this, "", "正在加载", false, false);
                    getContract().requestHistoryVp();
                    count=ONE;
                    break;
                default:
                    break;
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_back) {
            index = -ONE;
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        index=-ONE;
    }
}
