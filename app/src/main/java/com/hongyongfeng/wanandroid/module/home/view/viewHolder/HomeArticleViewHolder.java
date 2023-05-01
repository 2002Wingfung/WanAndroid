package com.hongyongfeng.wanandroid.module.home.view.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hongyongfeng.wanandroid.R;

/**
 * @author Wingfung Hung
 */
public class HomeArticleViewHolder extends RecyclerView.ViewHolder{
    /**
     * 菜品item视图
     */
    public View articleView;


    public TextView tvTitle;

    public HomeArticleViewHolder(@NonNull View itemView) {
        super(itemView);
        articleView=itemView;
        tvTitle=itemView.findViewById(R.id.tv_title);
    }
}
