package com.hongyongfeng.wanandroid.module.main.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class MoreActivity extends BaseActivity<MorePresenter, MoreInterface.VP>{
    TextView tvBack;
    TextView tvTitle;
    RecyclerView recyclerView;
    //可以是网络获取的bean,也可以是本地获取的bean
    ArrayList<ArticleBean> articleBeanLists=new ArrayList<>();
    ArticleAdapter adapter=new ArticleAdapter(articleBeanLists);
    static ProgressDialog dialog;

    @Override
    public MoreInterface.VP getContract() {
        return new MoreInterface.VP() {
            @Override
            public void saveHistory(ArticleBean article) {
                mPresenter.getContract().saveHistory(article);
            }

            @Override
            public void collectVP(int id, CollectListener listener) {
                mPresenter.getContract().collectVP(id,listener);
            }

            @Override
            public void unCollectVP(int id, CollectListener listener) {
                mPresenter.getContract().unCollectVP(id,listener);

            }

            @Override
            public void requestHistoryVP() {
                mPresenter.getContract().requestHistoryVP();
            }

            @Override
            public void responseHistoryVP(List<ArticleBean> articleBeanList) {
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
            public void requestCollectVP() {
                mPresenter.getContract().requestCollectVP();
            }

            @Override
            public void responseCollectVP(List<ArticleBean> article) {
                articleBeanLists.addAll(article);
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
            public void collectResponse(int code) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (code==0){
                            Toast.makeText(MoreActivity.this, "点赞成功", Toast.LENGTH_SHORT).show();
                        }else if (code==1){
                            Toast.makeText(MoreActivity.this, "还没登录", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(MoreActivity.this, SignInUpActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                            finish();
                        }else {
                            Toast.makeText(MoreActivity.this, "点赞失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            @Override
            public void unCollectResponse(int code) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (code==0){
                            Toast.makeText(MoreActivity.this, "取消点赞", Toast.LENGTH_SHORT).show();
                        }else if (code==1){
                            Toast.makeText(MoreActivity.this, "还没登录", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MoreActivity.this, "点赞失败", Toast.LENGTH_SHORT).show();
                        }
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
//                        dialog = ProgressDialog.show(fragmentActivity, "", "正在加载", false, false);
//                        page++;
//                        getContract().requestTitleVP(id,page);
                    }
                }
            }
        });
        adapter.setOnItemClickListener(new ArticleAdapter.OnItemClickListener() {
            @Override
            public void onLikesClicked(View view, int position, TextView likes, int[] count) {
                int number2 = 2;
                int number0 = 0;
                if (MoreActivity.this.count ==0){

                    number0=1;
                    if (count[0] % number2 == number0) {
                        getContract().collectVP(articleBeanLists.get(position).getId(), new CollectListener() {
                            @Override
                            public void onFinish() {
                                likes.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_likes, null));
                            }
                            @Override
                            public void onError() {
                            }
                        });
                    } else {
                        getContract().unCollectVP(articleBeanLists.get(position).getId(), new CollectListener() {
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
                    if (count[0] % number2 == number0) {
                        likes.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_likes, null));
                        Toast.makeText(MoreActivity.this, "点赞成功", Toast.LENGTH_SHORT).show();
                    } else {
                        likes.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_likes_gray, null));
                        Toast.makeText(MoreActivity.this, "取消点赞", Toast.LENGTH_SHORT).show();
                    }
                }
                int number1 = 1;
                //如果是收藏界面，则这里是number1，看看怎么把所有的item都变为红心
                //如果是历史浏览界面，则这里是number0

                count[0]++;
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


    private int count =0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        if (intent!=null){
            String title=intent.getStringExtra("title");
            tvTitle.setText(title);
            int index=intent.getIntExtra("index",-1);
            switch (index){
                case 0:
                    dialog = ProgressDialog.show(MoreActivity.this, "", "正在加载", false, false);
                    getContract().requestCollectVP();
                    count=0;
                    break;
                case 1:
                    dialog = ProgressDialog.show(MoreActivity.this, "", "正在加载", false, false);
                    getContract().requestHistoryVP();
                    count=1;
                    break;
                default:
                    break;
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
            default:
                break;
        }
    }
}
