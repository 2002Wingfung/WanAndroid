package com.hongyongfeng.wanandroid.module.query.view.fragment;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.module.query.interfaces.HeatedWords;
import com.hongyongfeng.wanandroid.module.query.presenter.HeatedWordsPresenter;
import com.hongyongfeng.wanandroid.module.query.view.FlowLayout;
import com.hongyongfeng.wanandroid.util.DisplayUtils;
import java.util.List;
import java.util.Map;

/**
 * @author Wingfung Hung
 */
public class HeatedWordsFragment extends BaseFragment<HeatedWordsPresenter, HeatedWords.Vp> {
    private void display(FlowLayout flowLayout, List<Map<String,Object>> heatedWordsListMap) {
        for (Map<String,Object> heatedWordsMap:heatedWordsListMap) {

            TextView tv = new TextView(activity);
            tv.setText((String)heatedWordsMap.get("name"));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            tv.setGravity(Gravity.CENTER);
            int paddingY = DisplayUtils.dp2px( 7);
            int paddingX = DisplayUtils.dp2px( 6);
            tv.setPadding(paddingX, paddingY, paddingX, paddingY);
            tv.setClickable(false);

            int shape = GradientDrawable.RECTANGLE;
            int radius = DisplayUtils.dp2px( 14);
            int strokeWeight = DisplayUtils.dp2px( 2);
            int stokeColor = getResources().getColor(R.color.green);

            GradientDrawable drawableDefault = new GradientDrawable();
            drawableDefault.setShape(shape);
            drawableDefault.setCornerRadius(radius);
            drawableDefault.setStroke(strokeWeight, stokeColor);
            drawableDefault.setColor(ContextCompat.getColor(activity,R.color.transparent1));

            GradientDrawable drawableChecked = new GradientDrawable();
            drawableChecked.setShape(shape);
            drawableChecked.setCornerRadius(radius);
            drawableChecked.setColor(ContextCompat.getColor(activity,R.color.shallow_gray));

            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_checked}, drawableChecked);
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, drawableChecked);

            stateListDrawable.addState(new int[]{}, drawableDefault);

            tv.setBackground(stateListDrawable);
            tv.setOnClickListener(v -> {
                String key=tv.getText().toString();
                edtKeyword.setText(key);
                listener.sendValue(key);
            });
            flowLayout.addView(tv);
        }
    }
    public interface CallBackListener{
        /**
         * 回调TextView中字符串的值给宿主Activity
         * @param value TextView中字符串的值
         */
        void sendValue(String value);
    }

    private CallBackListener listener;

    @Override
    public HeatedWords.Vp getContract() {
        return new HeatedWords.Vp() {

            @Override
            public void requestHeatedWordsVp() {
                mPresenter.getContract().requestHeatedWordsVp();
            }

            @Override
            public void responseHeatedWordsResult(List<Map<String,Object>> heatedWordsListMap) {
                activity.runOnUiThread(() -> {
                    //tvHeatedWords.setText(words.toString());
                    display(flowLayout,heatedWordsListMap);
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getContract().requestHeatedWordsVp();
    }

    @Override
    protected void destroy() {
    }
    private FlowLayout flowLayout;
    private EditText edtKeyword;
    private FragmentActivity activity;
    @Override
    protected void initView(View view) {
        edtKeyword = activity.findViewById(R.id.edt_keyword);
        flowLayout = view.findViewById(R.id.flowlayout);
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
