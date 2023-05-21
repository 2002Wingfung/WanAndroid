package com.hongyongfeng.wanandroid.module.project.view.adapter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.data.net.bean.ProjectBean;
import com.hongyongfeng.wanandroid.module.project.view.viewholder.ProjectViewHolder;
import com.hongyongfeng.wanandroid.util.ImageLoader;
import com.hongyongfeng.wanandroid.util.MyApplication;
import java.util.List;

/**
 * @author Wingfung Hung
 */
public class ProjectAdapter extends RecyclerView.Adapter<ProjectViewHolder>  {

    //private ImageLoader imageLoader=ImageLoader.build(MyApplication.getContext());
    private final Resources resource= MyApplication.getContext().getResources();
    /**
     * 存储DishesInformation对象的List集合
     */
    private final List<ProjectBean> articleList;
    List<Bitmap> bitmapLists;
    public interface OnItemClickListener {
        void onLikesClicked(View view, int position, TextView likes, int[] count);
        void onArticleClicked(View view, int position);

    }
    private OnItemClickListener mOnItemClickListener;
    public ArrayMap<Integer,View> viewHolderMap=new ArrayMap<>();

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
        holder.tvTitle.setText(project.getTitle());
        holder.tvDetails.setText(project.getDesc());
        holder.tvTime.setText(project.getNiceDate());
        holder.tvAuthor.setText(project.getAuthor());
        viewHolderMap.put(position,holder.itemView);
        if (project.isCollect()){
            holder.tvLikes.setBackground(ResourcesCompat.getDrawable(resource, R.drawable.ic_likes, null));
        }else {
            holder.tvLikes.setBackground(ResourcesCompat.getDrawable(resource, R.drawable.ic_likes_gray, null));
        }
        if (bitmapLists.size()>position){
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
        return articleList.size();
    }

    /**
     *
     */
    public ProjectAdapter(List<ProjectBean> articleList,List<Bitmap> bitmapLists) {
        this.articleList = articleList;
        this.bitmapLists = bitmapLists;
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ProjectViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        //System.out.println("1"+holder.getAdapterPosition());
//        viewHolderMap.put(holder.getAdapterPosition(),holder.itemView);
    }
}
