package com.hongyongfeng.wanandroid.module.query.view.fragment;

import static android.content.Intent.getIntent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.home.view.adapter.ArticleAdapter;
import com.hongyongfeng.wanandroid.util.SetRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ArticleFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_query_article,container,false);
    }
    public List<ArticleBean> articleList=new ArrayList<>();
    private FragmentActivity fragmentActivity;
    List<ArticleBean> articleBeanList = null;

    ArticleAdapter adapter=new ArticleAdapter(articleList);
    RecyclerView recyclerView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView= fragmentActivity.findViewById(R.id.rv_article);
        Log.d("article","onviewcreated");
        SetRecyclerView.setRecyclerView(fragmentActivity,recyclerView,adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("article","onResume");
    }

    @Override
    public void onStart() {
        Log.d("article","onStart");
        super.onStart();
        loadData();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("article", "onAttach");
        fragmentActivity = requireActivity();

        if (getArguments() != null) {
            articleBeanList = getArguments().getParcelableArrayList("list");
//            articleBeanList.remove(0);
//            articleBeanList.remove(0);
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
        //adapter.notifyItemRangeChanged(0,articleList.size());
    }
}
