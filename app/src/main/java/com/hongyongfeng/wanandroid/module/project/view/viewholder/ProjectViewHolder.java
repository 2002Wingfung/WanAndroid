package com.hongyongfeng.wanandroid.module.project.view.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.module.home.view.adapter.ArticleAdapter;
import com.hongyongfeng.wanandroid.module.project.view.adapter.ProjectAdapter;

/**
 * @author Wingfung Hung
 */
public class ProjectViewHolder extends RecyclerView.ViewHolder{
    /**
     * 菜品item视图
     */
    public View articleView;


    public TextView tvTitle;
    public TextView tvLikes;


    public ProjectViewHolder(@NonNull View itemView, final ProjectAdapter.OnItemClickListener onClickListener) {
        super(itemView);
        articleView=itemView;
        tvTitle=itemView.findViewById(R.id.tv_title);
        tvLikes=itemView.findViewById(R.id.likes);

        final int[] count={0};
        tvLikes.setOnClickListener(view -> {
            if (onClickListener != null) {
                int position = getAdapterPosition();
                //确保position值有效
                if (position != RecyclerView.NO_POSITION) {
                    System.out.println(tvLikes);

                    onClickListener.onLikesClicked(view, position,tvLikes,count);
                }
            }
        });
        articleView.setOnClickListener(view->{
            if (onClickListener != null) {
                int position = getAdapterPosition();
                //确保position值有效
                if (position != RecyclerView.NO_POSITION) {
                    onClickListener.onArticleClicked(view, position);
                }
            }
        });
    }
}
