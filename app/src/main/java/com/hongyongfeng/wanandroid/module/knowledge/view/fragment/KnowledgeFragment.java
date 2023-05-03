package com.hongyongfeng.wanandroid.module.knowledge.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.data.net.bean.KnowledgeCategoryBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.HomeFragmentInterface;
import com.hongyongfeng.wanandroid.module.home.presenter.HomeFragmentPresenter;
import com.hongyongfeng.wanandroid.module.home.view.adapter.ArticleAdapter;
import com.hongyongfeng.wanandroid.module.knowledge.interfaces.KnowledgeFragmentInterface;
import com.hongyongfeng.wanandroid.module.knowledge.presenter.KnowledgeFragmentPresenter;
import com.hongyongfeng.wanandroid.module.knowledge.view.adapter.KnowledgeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KnowledgeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KnowledgeFragment extends BaseFragment<KnowledgeFragmentPresenter, KnowledgeFragmentInterface.VP> {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    private FragmentActivity fragmentActivity;
    public static List<KnowledgeCategoryBean> categoryList=new ArrayList<>();

    static KnowledgeAdapter adapter=new KnowledgeAdapter(categoryList);


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fragmentActivity=requireActivity();

        super.onViewCreated(view, savedInstanceState);
        System.out.println(fragmentActivity);
        setRecyclerView();
    }

    private void setRecyclerView() {
        //获取LinearLayoutManager实例，设置布局方式
        LinearLayoutManager layoutManager=new LinearLayoutManager(fragmentActivity,LinearLayoutManager.VERTICAL,false);
        //将LinearLayoutManager实例传入RecycleView的实例中，设置RecycleView的item布局
        recyclerView.setLayoutManager(layoutManager);
        //通过设置ItemDecoration 来装饰Item的效果,设置间隔线
        DividerItemDecoration mDivider = new
                DividerItemDecoration(fragmentActivity,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(mDivider);
        //将adapter传入recyclerView对象中
        recyclerView.setAdapter(adapter);
        //添加默认动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //使recyclerView滚动到0索引的位置
        recyclerView.scrollToPosition(0);
    }

    public KnowledgeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KnowledgeFragment.
     */
    public static KnowledgeFragment newInstance(String param1, String param2) {
        KnowledgeFragment fragment = new KnowledgeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public KnowledgeFragmentInterface.VP getContract() {
        return new KnowledgeFragmentInterface.VP() {
            @Override
            public void requestLoginVP(String name, String pwd) {

            }

            @Override
            public void responseLoginResult(boolean loginStatusResult) {

            }
        };
    }



    @Override
    protected void destroy() {

    }

    @Override
    protected void initView(View view) {

        //根据id获取RecycleView的实例
        recyclerView= fragmentActivity.findViewById(R.id.rv_knowledge);
    }

    @Override
    protected void initListener() {

        adapter.setOnItemClickListener(new KnowledgeAdapter.OnItemClickListener() {

            @Override
            public void onCategoryClicked(View view, int position) {
                Toast.makeText(fragmentActivity, "点击了view"+(position+1), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void initData() {
        if (categoryList.size()==0){
            StringBuilder str=new StringBuilder("abc ");
            for (int i =1;i<21;i++){
                categoryList.add(new KnowledgeCategoryBean("开发环境"+i,str.toString()));
                str.append(i).append("nihao  ");
            }
        }
    }

    @Override
    protected KnowledgeFragmentPresenter getPresenterInstance() {
        return new KnowledgeFragmentPresenter();
    }

    @Override
    protected <ERROR> void responseError(ERROR error, Throwable throwable) {

    }

    @Override
    protected int getFragmentView() {
        return R.layout.fragment_knowledge;
    }

    @Override
    public void onClick(View v) {

    }
}