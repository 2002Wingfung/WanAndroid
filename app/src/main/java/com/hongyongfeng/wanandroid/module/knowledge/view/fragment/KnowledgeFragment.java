package com.hongyongfeng.wanandroid.module.knowledge.view.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ProgressBar;
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
import com.hongyongfeng.wanandroid.module.knowledge.view.activity.TabActivity;
import com.hongyongfeng.wanandroid.module.knowledge.view.adapter.KnowledgeAdapter;
import com.hongyongfeng.wanandroid.module.webview.view.WebViewActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KnowledgeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KnowledgeFragment extends BaseFragment<KnowledgeFragmentPresenter, KnowledgeFragmentInterface.VP> {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static ProgressDialog dialog;

    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    private FragmentActivity fragmentActivity;
//    public static List<KnowledgeCategoryBean> categoryList=new ArrayList<>();
    public static List<Map<String,Object>> categoryList=new ArrayList<>();
    static List<Map<String,Object>> stringListMap=new ArrayList<>();
    public static List<String> nameList=new ArrayList<>();

    static KnowledgeAdapter adapter=new KnowledgeAdapter(categoryList,nameList);
    FragmentActivity activity;
    private int count=0;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fragmentActivity=requireActivity();

        super.onViewCreated(view, savedInstanceState);
        setRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (count==0){
            getContract().requestTitleVP();
            dialog = ProgressDialog.show(requireActivity(), "", "正在加载", false, false);
            count=1;
        }
        //请求网络代码

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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity=requireActivity();
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
            public void requestTitleVP() {
                mPresenter.getContract().requestTitleVP();
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void responseTitleResult(List<Map<String,Object>> treeList) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (categoryList.size()==0){
                            for (Map<String,Object> treeMap:treeList) {
                                //System.out.println(treeMap.get("children"));
                                try {
//                                    JSONArray jsonArray=new JSONArray(Objects.requireNonNull(treeMap.get("children")).toString());
                                    JSONArray jsonArray=(JSONArray)treeMap.get("children");
                                    StringBuilder builder=new StringBuilder();
                                    Map<String,Object> stringMap = new HashMap<String,Object>();

                                    for (int i = 0; i< Objects.requireNonNull(jsonArray).length(); i++){
                                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                                        stringMap.put("id"+i,jsonObject.get("id"));
                                        String name=jsonObject.getString("name");
                                        builder.append(name).append("  ");
                                        stringMap.put("name"+i,name);
                                    }
                                    stringListMap.add(stringMap);
                                    nameList.add(builder.toString());
                                    //System.out.println(builder.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            categoryList.addAll(treeList);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }
                });

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

                //ProgressDialog.show(fragmentActivity,"","正在加载",false,true);
                String name=(String) categoryList.get(position).get("name");
                Toast.makeText(fragmentActivity, name, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(activity, TabActivity.class);
                intent.putExtra("name",name);
                Map<String,Object> childrenMap=stringListMap.get(position);
                intent.putExtra("name",name);
                intent.putExtra("map",(Serializable)childrenMap);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void initData() {

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