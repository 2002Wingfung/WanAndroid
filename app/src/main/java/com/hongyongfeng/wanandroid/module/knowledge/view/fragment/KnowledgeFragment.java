package com.hongyongfeng.wanandroid.module.knowledge.view.fragment;

import static com.hongyongfeng.wanandroid.util.Constant.ONE;
import static com.hongyongfeng.wanandroid.util.Constant.ZERO;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.module.knowledge.interfaces.KnowledgeFragmentInterface;
import com.hongyongfeng.wanandroid.module.knowledge.presenter.KnowledgeFragmentPresenter;
import com.hongyongfeng.wanandroid.module.knowledge.view.activity.TabActivity;
import com.hongyongfeng.wanandroid.module.knowledge.view.adapter.KnowledgeAdapter;
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
 * @author Wingfung Hung
 */
public class KnowledgeFragment extends BaseFragment<KnowledgeFragmentPresenter, KnowledgeFragmentInterface.Vp> {
    private ProgressDialog dialog;
    private RecyclerView recyclerView;
    private FragmentActivity fragmentActivity;
    private final List<Map<String,Object>> categoryList=new ArrayList<>();
    private final List<Map<String,Object>> stringListMap=new ArrayList<>();
    public static List<String> nameList=new ArrayList<>();
    private final KnowledgeAdapter adapter=new KnowledgeAdapter(categoryList,nameList);
    private FragmentActivity activity;
    private int count=ZERO;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fragmentActivity=requireActivity();
        super.onViewCreated(view, savedInstanceState);
        setRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (count==ZERO){
            getContract().requestTitleVp();
            dialog = ProgressDialog.show(requireActivity(), "", "正在加载", false, false);
            count=ONE;
        }
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
        recyclerView.scrollToPosition(ZERO);
    }

    public KnowledgeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity=requireActivity();
    }

    @Override
    public KnowledgeFragmentInterface.Vp getContract() {
        return new KnowledgeFragmentInterface.Vp() {
            @Override
            public void requestTitleVp() {
                mPresenter.getContract().requestTitleVp();
            }
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void responseTitleResult(List<Map<String,Object>> treeList) {
                activity.runOnUiThread(() -> {
                    if (categoryList.size()==0){
                        for (Map<String,Object> treeMap:treeList) {
                            try {
                                JSONArray jsonArray=(JSONArray)treeMap.get("children");
                                StringBuilder builder=new StringBuilder();
                                Map<String,Object> stringMap = new HashMap<>(ONE);
                                for (int i = 0; i< Objects.requireNonNull(jsonArray).length(); i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    stringMap.put("id"+i,jsonObject.get("id"));
                                    String name=jsonObject.getString("name");
                                    builder.append(name).append("  ");
                                    stringMap.put("name"+i,name);
                                }
                                stringListMap.add(stringMap);
                                nameList.add(builder.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        categoryList.addAll(treeList);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
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
        adapter.setOnItemClickListener((view, position) -> {
            String name=(String) categoryList.get(position).get("name");
            Intent intent=new Intent(activity, TabActivity.class);
            intent.putExtra("name",name);
            Map<String,Object> childrenMap=stringListMap.get(position);
            intent.putExtra("name",name);
            intent.putExtra("map",(Serializable)childrenMap);
            startActivity(intent);
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