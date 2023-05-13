package com.hongyongfeng.wanandroid.module.project.view.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.data.net.bean.ProjectBean;
import com.hongyongfeng.wanandroid.module.project.view.viewholder.ProjectViewHolder;

import java.util.List;

/**
 * @author Wingfung Hung
 */
public class ProjectAdapter extends RecyclerView.Adapter<ProjectViewHolder>  {

    /**
     * 存储DishesInformation对象的List集合
     */
    public List<ProjectBean> articleList;
    List<Bitmap> bitmapLists;
    public interface OnItemClickListener {
        void onLikesClicked(View view, int position, TextView likes, int[] count);
        void onArticleClicked(View view, int position);

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
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project_article,parent,false);
        return new ProjectViewHolder(view,mOnItemClickListener);
    }



    /**
     * 绑定item中的控件
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        //在merchantInfoList集合中获取Merchant对象
        ProjectBean project=articleList.get(position);
        //设置商家图片
        holder.tvTitle.setText(project.getTitle());
        holder.tvDetails.setText(project.getDesc());
        holder.tvTime.setText(project.getNiceDate());
        holder.tvAuthor.setText(project.getAuthor());
        if (bitmapLists.size()!=0){
            Bitmap bitmap=bitmapLists.get(position);
            if (bitmap==null){
                holder.imageView.setImageResource(R.drawable.project_item_default_bg);
            }else {
                holder.imageView.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public int getItemCount() {
        return bitmapLists.size();
    }

    /**
     *
     */
    public ProjectAdapter(List<ProjectBean> articleList,List<Bitmap> bitmapLists) {
        this.articleList = articleList;
        this.bitmapLists = bitmapLists;
    }
}
