package com.hongyongfeng.wanandroid.module.knowledge.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.data.net.bean.KnowledgeCategoryBean;
import com.hongyongfeng.wanandroid.module.knowledge.view.viewholder.KnowledgeViewHolder;

import java.util.List;

/**
 * @author Wingfung Hung
 */
public class KnowledgeAdapter extends RecyclerView.Adapter<KnowledgeViewHolder>  {

    /**
     * 存储DishesInformation对象的List集合
     */
    public List<KnowledgeCategoryBean> categoryList;

    public interface OnItemClickListener {
        void onCategoryClicked(View view, int position);

    }
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.mOnItemClickListener = clickListener;
    }
        /**
     * 绑定顾客主页的item
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return CustomerHomeViewHolder对象
     */
    @NonNull
    @Override
    public KnowledgeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_knowledge_category,parent,false);
        return new KnowledgeViewHolder(view,mOnItemClickListener);
    }


    /**
     * 绑定item中的控件
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull KnowledgeViewHolder holder, int position) {
        KnowledgeCategoryBean category=categoryList.get(position);

        holder.tvCategory.setText(category.getCategory());
        holder.tvDetails.setText(category.getDetailedCategory());

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    /**
     *
     */
    public KnowledgeAdapter(List<KnowledgeCategoryBean> categoryList) {
        this.categoryList = categoryList;
    }
}
