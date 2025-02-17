package com.hongyongfeng.wanandroid.module.knowledge.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.module.knowledge.view.viewholder.KnowledgeViewHolder;
import java.util.List;
import java.util.Map;

/**
 * @author Wingfung Hung
 */
public class KnowledgeAdapter extends RecyclerView.Adapter<KnowledgeViewHolder>  {

    private final List<String> nameList;
    /**
     * 存储DishesInformation对象的List集合
     */
    public List<Map<String,Object>> categoryList;

    public interface OnItemClickListener {
        /**
         * 知识文章点击事件
         * @param view item视图
         * @param position item索引
         */
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
        Map<String,Object> category=categoryList.get(position);
        holder.tvDetails.setText(nameList.get(position));
        holder.tvCategory.setText((String) category.get("name"));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public KnowledgeAdapter(List<Map<String,Object>> categoryList,List<String> nameList) {
        this.categoryList = categoryList;
        this.nameList=nameList;
    }
}
