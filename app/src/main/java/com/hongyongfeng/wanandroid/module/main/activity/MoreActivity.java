package com.hongyongfeng.wanandroid.module.main.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import com.hongyongfeng.wanandroid.module.home.view.adapter.ArticleAdapter;
import com.hongyongfeng.wanandroid.module.main.interfaces.MoreInterface;
import com.hongyongfeng.wanandroid.module.main.presenter.MorePresenter;
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

    @Override
    public MoreInterface.VP getContract() {
        return new MoreInterface.VP() {
            @Override
            public void requestQueryVP(String key,int page) {
                //System.out.println(key);

                mPresenter.getContract().requestQueryVP(key,page);
            }

            @Override
            public void responseQueryResult(List<ArticleBean> articleBeanList) {
                handler.sendEmptyMessageDelayed(0,500);
                articleBeanLists=(ArrayList<ArticleBean>) articleBeanList;
            }

            @Override
            public void requestHistoryVP() {
                mPresenter.getContract().requestHistoryVP();
            }

            @Override
            public void responseHistoryVP(List<ArticleBean> articleBeanList) {
//                for (ArticleBean article:articleBeanList) {
//                    System.out.println(article.getTitle());
//                }
                articleBeanLists.addAll(articleBeanList);
                MoreActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        };
    }
    Handler handler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

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
                int number1 = 1;
                int number0 = 0;
                if (count[0] % number2 == number1) {
                    //如果是收藏界面，则这里是number1，看看怎么把所有的item都变为红心
                    //如果是历史浏览界面，则这里是number0
                    likes.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_likes, null));
                    Toast.makeText(MoreActivity.this, "点赞成功", Toast.LENGTH_SHORT).show();
                } else {
                    likes.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_likes_gray, null));
                    Toast.makeText(MoreActivity.this, "取消点赞", Toast.LENGTH_SHORT).show();
                }
                count[0]++;
            }

            @Override
            public void onArticleClicked(View view, int position) {
                Intent intent = new Intent(MoreActivity.this, WebViewActivity.class);
                intent.putExtra("url", articleBeanLists.get(position).getLink());
                startActivity(intent);
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
                    break;
                case 1:
                    getContract().requestHistoryVP();
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
