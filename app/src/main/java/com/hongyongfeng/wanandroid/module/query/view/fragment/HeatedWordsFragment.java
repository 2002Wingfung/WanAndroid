package com.hongyongfeng.wanandroid.module.query.view.fragment;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.module.query.interfaces.HeatedWords;
import com.hongyongfeng.wanandroid.module.query.presenter.HeatedWordsPresenter;
import com.hongyongfeng.wanandroid.module.query.view.FlowLayout;
import com.hongyongfeng.wanandroid.test.TestFlowLayoutActivity;
import com.hongyongfeng.wanandroid.util.DisplayUtils;

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
                    System.out.println(tv.getText().toString());
                }
            });
            flowLayout.addView(tv);
        }
    }
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
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvHeatedWords.setText(words.toString());
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
    FlowLayout flowLayout;

    FragmentActivity activity;
    TextView tvHeatedWords;
    @Override
    protected void initView(View view) {
        tvHeatedWords=view.findViewById(R.id.tv_words);
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
