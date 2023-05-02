package com.hongyongfeng.wanandroid.module.home.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.home.view.adapter.HomeArticleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        adapter.setOnItemClickListener(new HomeArticleAdapter.OnItemClickListener() {
            @Override
            public void onLikesClicked(View view, int position, TextView likes, int[] count) {
                //String merchantId=orderLists.get(position).getMerchantId();
                int number2=2;
                int number0=0;
                if (count[0]%number2==number0){
                    likes.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_likes,null));
                    Toast.makeText(activity, "点赞成功", Toast.LENGTH_SHORT).show();
                    //UpdateLikes.updateLikesPlus(activity,merchantId);
                }else {
                    likes.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_likes_gray,null));
                    //likes.setBackgroundTintList(new ColorStateList());
                    Toast.makeText(activity, "取消点赞", Toast.LENGTH_SHORT).show();
                    //UpdateLikes.updateLikesDecrease(merchantId,activity);
                }
                count[0]++;
            }

            @Override
            public void onArticleClicked(View view, int position) {
                Toast.makeText(activity, "点击了view", Toast.LENGTH_SHORT).show();

            }
        });

        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    public static List<ArticleBean> articleList=new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    public static Activity activity;
    @SuppressLint("StaticFieldLeak")
    static HomeArticleAdapter adapter=new HomeArticleAdapter(articleList);
    private NestedScrollView scrollView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity=getActivity();
        ConstraintLayout layout= requireActivity().findViewById(R.id.fragment_home);
        scrollView=layout.findViewById(R.id.scroll_view_home);
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {   //scrollY是滑动的距离
                if(scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())){
                    //滑动到底部
                    Toast.makeText(activity, "滑动到了底部", Toast.LENGTH_SHORT).show();
                }
            }
        });

        initData();
        //根据id获取RecycleView的实例
        RecyclerView recyclerView= activity.findViewById(R.id.rv_article);
        //获取LinearLayoutManager实例，设置布局方式
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        //将LinearLayoutManager实例传入RecycleView的实例中，设置RecycleView的item布局
        recyclerView.setLayoutManager(layoutManager);
        //通过设置ItemDecoration 来装饰Item的效果,设置间隔线
        DividerItemDecoration mDivider = new
                DividerItemDecoration(activity,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(mDivider);
        //将adapter传入recyclerView对象中
        recyclerView.setAdapter(adapter);
        //添加默认动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //使recyclerView滚动到0索引的位置
        recyclerView.scrollToPosition(0);
    }


    private void initData() {
        for (int i =0;i<100;i++){
            articleList.add(new ArticleBean(i));
        }
    }
}
