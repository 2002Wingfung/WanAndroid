package com.hongyongfeng.wanandroid.module.home.view.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.module.home.view.adapter.HomeArticleAdapter;

/**
 * @author Wingfung Hung
 */
public class HomeArticleViewHolder extends RecyclerView.ViewHolder{
    /**
     * 菜品item视图
     */
    public View articleView;


    public TextView tvTitle;
    public TextView tvLikes;

    public HomeArticleViewHolder(@NonNull View itemView, final HomeArticleAdapter.OnItemClickListener onClickListener) {
        super(itemView);
        articleView=itemView;
        tvTitle=itemView.findViewById(R.id.tv_title);
        final int[] count={0};
        int position = getAdapterPosition();
        tvLikes.setOnClickListener(view -> {
            if (onClickListener != null) {
                //确保position值有效
                if (position != RecyclerView.NO_POSITION) {
                    onClickListener.onLikesClicked(view, position,tvLikes,count);
                }
            }
        });
        articleView.setOnClickListener(view->{
            if (onClickListener != null) {
                //确保position值有效
                if (position != RecyclerView.NO_POSITION) {
                    onClickListener.onArticleClicked(view, position);
                }
            }
        });
    }
}
