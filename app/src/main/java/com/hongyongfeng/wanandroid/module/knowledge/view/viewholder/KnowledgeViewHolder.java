package com.hongyongfeng.wanandroid.module.knowledge.view.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.module.knowledge.view.adapter.KnowledgeAdapter;

/**
 * @author Wingfung Hung
 */
public class KnowledgeViewHolder extends RecyclerView.ViewHolder{
    /**
     * 菜品item视图
     */
    public View view;


    public TextView tvCategory;
    public TextView tvDetails;


    public KnowledgeViewHolder(@NonNull View itemView, final KnowledgeAdapter.OnItemClickListener onClickListener) {
        super(itemView);
        view=itemView;
        tvCategory =itemView.findViewById(R.id.tv_category);
        tvDetails=itemView.findViewById(R.id.details);


        view.setOnClickListener(view->{
            if (onClickListener != null) {
                int position = getAdapterPosition();
                //确保position值有效
                if (position != RecyclerView.NO_POSITION) {
                    onClickListener.onCategoryClicked(view, position);
                }
            }
        });
    }
}
