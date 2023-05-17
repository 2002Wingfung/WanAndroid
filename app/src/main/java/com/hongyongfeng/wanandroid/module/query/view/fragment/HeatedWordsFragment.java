package com.hongyongfeng.wanandroid.module.query.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.query.interfaces.HeatedWords;
import com.hongyongfeng.wanandroid.module.query.presenter.HeatedWordsPresenter;
import com.hongyongfeng.wanandroid.module.query.view.FlowLayout;
import com.hongyongfeng.wanandroid.test.TestFlowLayoutActivity;
import com.hongyongfeng.wanandroid.util.DisplayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HeatedWordsFragment extends BaseFragment<HeatedWordsPresenter, HeatedWords.VP> {
    private void displayUI(FlowLayout flowLayout,List<Map<String,Object>> heatedWordsListMap) {
//        final String data = "132";
//        StringBuilder builder=new StringBuilder(data);

        for (Map<String,Object> heatedWordsMap:heatedWordsListMap) {

            TextView tv = new TextView(activity);
            tv.setText((String)heatedWordsMap.get("name"));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            tv.setGravity(Gravity.CENTER);
            int paddingy = DisplayUtils.dp2px( 7);
            int paddingx = DisplayUtils.dp2px( 6);
            tv.setPadding(paddingx, paddingy, paddingx, paddingy);
            tv.setClickable(false);

            int shape = GradientDrawable.RECTANGLE;
            int radius = DisplayUtils.dp2px( 14);
            int strokeWeight = DisplayUtils.dp2px( 2);
            int stokeColor = getResources().getColor(R.color.green);
            int stokeColor2 = getResources().getColor(R.color.gray);

            GradientDrawable drawableDefault = new GradientDrawable();
            drawableDefault.setShape(shape);
            drawableDefault.setCornerRadius(radius);
            drawableDefault.setStroke(strokeWeight, stokeColor);
            drawableDefault.setColor(ContextCompat.getColor(activity, android.R.color.transparent));

            GradientDrawable drawableChecked = new GradientDrawable();
            drawableChecked.setShape(shape);
            drawableChecked.setCornerRadius(radius);
//            drawableChecked.setColor(ContextCompat.getColor(TestFlowLayoutActivity.this, android.R.color.darker_gray));
            drawableChecked.setColor(ContextCompat.getColor(activity,R.color.shallow_gray));

            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_checked}, drawableChecked);
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, drawableChecked);

            stateListDrawable.addState(new int[]{}, drawableDefault);

            tv.setBackground(stateListDrawable);
            //ColorStateList colorStateList = DrawableUtils.getColorSelector(getResources().getColor(R.color.black), getResources().getColor(R.color.white));
            //tv.setTextColor(getResources().getColor(R.color.gray));
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String key=tv.getText().toString();
                    //System.out.println(key);
                    edtKeyword.setText(key);
                    listener.sendValue(key);
                    //dialog = ProgressDialog.show(activity, "", "正在加载", false, false);
                    //getContract().requestQueryVP(key,0);
                }
            });
            flowLayout.addView(tv);
        }
    }
    public interface CallBackListener{
        public void sendValue(String value);
    }

    private CallBackListener listener;
    private final Handler mHandler=new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            dialog.dismiss();
//            Bundle bundle=new Bundle();
//            bundle.putParcelableArrayList("list",  articleBeanLists);
            //loadFragment();
//            if (!articleFragment.isAdded()){
//                articleFragment.setArguments(bundle);
//                transaction.add(R.id.fragment_query,articleFragment).show(articleFragment).commit();
//            }else {
//                articleFragment.setArguments(bundle);
//                transaction.show(articleFragment).commit();
//            }
        }
    };
    @Override
    public HeatedWords.VP getContract() {
        return new HeatedWords.VP() {
            @Override
            public void requestQueryVP(String key, int page) {
                mPresenter.getContract().requestQueryVP(key,page);
            }

            @Override
            public void responseQueryResult(List<ArticleBean> queryResult) {

                //System.out.println(queryResult);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        mHandler.sendEmptyMessageDelayed(0,500);
//                        articleBeanLists=(ArrayList<ArticleBean>) queryResult;
                        //listener.sendValue();
                    }
                });
            }

            @Override
            public void requestHeatedWordsVP() {
                mPresenter.getContract().requestHeatedWordsVP();
            }

            @Override
            public void responseHeatedWordsResult(List<Map<String,Object>> heatedWordsListMap) {
                StringBuilder words=new StringBuilder();
//                for (Map<String,Object> heatedWordsMap:heatedWordsListMap) {
//                    words.append(heatedWordsMap.get("name")).append(" ");
//                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //tvHeatedWords.setText(words.toString());
                        displayUI(flowLayout,heatedWordsListMap);
                    }
                });
            }
        };
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity=requireActivity();
        listener=(CallBackListener) activity;
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

    ProgressDialog dialog;
    FlowLayout flowLayout;
    EditText edtKeyword;
    FragmentActivity activity;
    @Override
    protected void initView(View view) {
//        tvHeatedWords=view.findViewById(R.id.tv_words);
        edtKeyword=activity.findViewById(R.id.edt_keyword);
        flowLayout = (FlowLayout) view.findViewById(R.id.flowlayout);
        flowLayout.setSpace(DisplayUtils.dp2px(15), DisplayUtils.dp2px( 15));
        flowLayout.setPadding(DisplayUtils.dp2px(5), DisplayUtils.dp2px( 5),
                DisplayUtils.dp2px( 5), DisplayUtils.dp2px( 5));

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
