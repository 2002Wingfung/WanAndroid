package com.hongyongfeng.wanandroid.test;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.drawable.DrawableUtils;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.module.query.view.FlowLayout;
import com.hongyongfeng.wanandroid.util.DisplayUtils;

public class TestFlowLayoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        FlowLayout flowLayout = (FlowLayout)findViewById(R.id.flowlayout);
        flowLayout.setSpace(DisplayUtils.dp2px(15), DisplayUtils.dp2px( 15));
        flowLayout.setPadding(DisplayUtils.dp2px(5), DisplayUtils.dp2px( 5),
                DisplayUtils.dp2px( 5), DisplayUtils.dp2px( 5));
        displayUI(flowLayout);
    }
    private void displayUI(FlowLayout flowLayout ) {
        final String data = "132";
        StringBuilder builder=new StringBuilder(data);

        for (int i = 0; i < 7; i++) {
            builder.append("ni");
            TextView tv = new TextView(this);
            tv.setText(builder);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            tv.setGravity(Gravity.CENTER);
            int paddingy = DisplayUtils.dp2px( 7);
            int paddingx = DisplayUtils.dp2px( 6);
            tv.setPadding(paddingx, paddingy, paddingx, paddingy);
            tv.setClickable(false);

            int shape = GradientDrawable.RECTANGLE;
            int radius = DisplayUtils.dp2px( 4);
            int strokeWeight = DisplayUtils.dp2px( 1);
            int stokeColor = getResources().getColor(R.color.green);
            int stokeColor2 = getResources().getColor(R.color.gray);

            GradientDrawable drawableDefault = new GradientDrawable();
            drawableDefault.setShape(shape);
            drawableDefault.setCornerRadius(radius);
            drawableDefault.setStroke(strokeWeight, stokeColor);
            drawableDefault.setColor(ContextCompat.getColor(TestFlowLayoutActivity.this, android.R.color.transparent));

            GradientDrawable drawableChecked = new GradientDrawable();
            drawableChecked.setShape(shape);
            drawableChecked.setCornerRadius(radius);
//            drawableChecked.setColor(ContextCompat.getColor(TestFlowLayoutActivity.this, android.R.color.darker_gray));
            drawableChecked.setColor(ContextCompat.getColor(TestFlowLayoutActivity.this,R.color.gray));

            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_checked}, drawableChecked);
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, drawableChecked);

            stateListDrawable.addState(new int[]{}, drawableDefault);

            tv.setBackground(stateListDrawable);
            //ColorStateList colorStateList = DrawableUtils.getColorSelector(getResources().getColor(R.color.black), getResources().getColor(R.color.white));
            tv.setTextColor(getResources().getColor(R.color.black));
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(tv.getText().toString());
                }
            });
            flowLayout.addView(tv);
        }
    }
}
