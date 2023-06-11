package com.hongyongfeng.wanandroid.module.home.view.viewholder;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.module.home.view.adapter.ArticleAdapter;

/**
 * @author Wingfung Hung
 */
public class ArticleViewHolder extends RecyclerView.ViewHolder{
    /**
     * 文章item视图
     */
    public View articleView;
    /**
     * 文章标题
     */
    public TextView tvTitle;
    /**
     * 文章的点赞按钮
     */
    public TextView tvLikes;
    /**
     * 文章置顶标识
     */
    public TextView tvTop;
    /**
     * 文章类别
     */
    public TextView tvCategory;
    /**
     * 文章发布时间
     */
    public TextView tvTime;
    /**
     * 文章作者
     */
    public TextView tvAuthor;
    public static class LoadingHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        public LoadingHolder(@NonNull View itemView) {
            super(itemView);
            progressBar=itemView.findViewById(R.id.progressBar);
        }
    }

    public ArticleViewHolder(@NonNull View itemView, final ArticleAdapter.OnItemClickListener onClickListener) {
        super(itemView);
        articleView=itemView;
        tvTitle=itemView.findViewById(R.id.title);
        tvLikes=itemView.findViewById(R.id.likes);
        tvTop=itemView.findViewById(R.id.top);
        tvCategory=itemView.findViewById(R.id.category);
        tvTime=itemView.findViewById(R.id.time);
        tvAuthor=itemView.findViewById(R.id.author);
        final int[] count={0};
        tvLikes.setOnClickListener(view -> {
            if (onClickListener != null) {
                int position = getAdapterPosition();
                //确保position值有效
                if (position != RecyclerView.NO_POSITION) {
                    //点赞事件的监听回调
                    onClickListener.onLikesClicked(view, position,tvLikes,count);
                }
            }
        });
        articleView.setOnClickListener(view->{
            if (onClickListener != null) {
                int position = getAdapterPosition();
                //确保position值有效
                if (position != RecyclerView.NO_POSITION) {
                    //点击item事件监听回调
                    onClickListener.onArticleClicked(view, position);
                }
            }
        });
    }
}
