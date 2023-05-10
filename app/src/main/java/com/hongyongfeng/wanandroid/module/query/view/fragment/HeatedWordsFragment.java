package com.hongyongfeng.wanandroid.module.query.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.module.query.interfaces.HeatedWords;
import com.hongyongfeng.wanandroid.module.query.presenter.HeatedWordsPresenter;

import java.util.List;
import java.util.Map;

public class HeatedWordsFragment extends BaseFragment<HeatedWordsPresenter, HeatedWords.VP> {
    @Override
    public HeatedWords.VP getContract() {
        return new HeatedWords.VP() {
            @Override
            public void requestHeatedWordsVP() {
                mPresenter.getContract().requestHeatedWordsVP();
            }

            @Override
            public void responseHeatedWordsResult(List<Map<String,Object>> heatedWordsListMap) {
                StringBuilder words=new StringBuilder();
                for (Map<String,Object> heatedWordsMap:heatedWordsListMap) {
                    words.append(heatedWordsMap.get("name")).append(" ");
                    //System.out.println(heatedWordsMap);
//                    for (String key : heatedWordsMap.keySet()){
//                        System.out.println(heatedWordsMap.get(key));
//                    }

                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvHeatedWords.setText(words.toString());
                    }
                });
            }
        };
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity=requireActivity();
    }

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_query_heated_words,container,false);
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getContract().requestHeatedWordsVP();
        //Log.d("heated","print");
    }

    @Override
    protected void destroy() {

    }

    FragmentActivity activity;
    TextView tvHeatedWords;
    @Override
    protected void initView(View view) {
        tvHeatedWords=view.findViewById(R.id.tv_words);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected HeatedWordsPresenter getPresenterInstance() {
        return new HeatedWordsPresenter();
    }

    @Override
    protected <ERROR> void responseError(ERROR error, Throwable throwable) {

    }

    @Override
    protected int getFragmentView() {
        return R.layout.fragment_query_heated_words;
    }

    @Override
    public void onClick(View v) {

    }
}
