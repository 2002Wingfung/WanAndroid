package com.hongyongfeng.wanandroid.module.project.view.adapter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.ArrayMap;
import android.util.Log;
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

    private ImageLoader imageLoader=ImageLoader.build(MyApplication.getContext());
    private final Resources resource= MyApplication.getContext().getResources();
    /**
     * 存储DishesInformation对象的List集合
     */
    private final List<ProjectBean> articleList;
    public interface OnItemClickListener {
        /**
         * 点赞按钮的监听
         * @param view 点赞按钮的视图
         * @param position item的索引
         * @param likes 点赞控件
         * @param count 用于判断是否点赞
         */
        void onLikesClicked(View view, int position, TextView likes, int[] count);

        /**
         * 点击文章监听
         * @param view item的视图
         * @param position item的索引
         */
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
//        if (bitmapLists.size()>position){
//            Bitmap bitmap=bitmapLists.get(position);
//            if (bitmap==null){
//                holder.imageView.setImageResource(R.drawable.project_item_default_bg);
//            }else {
//                //Log.d("adapter-bitmap",bitmap.toString());
//                holder.imageView.setImageBitmap(bitmap);
//            }
//        }
        //System.out.println(articleList.get(position).getEnvelopePic());
        holder.imageView.setImageResource(R.drawable.project_item_default_bg);
        imageLoader.bindBitmap(articleList.get(position).getEnvelopePic(),holder.imageView,900,1600);
    }
    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public ProjectAdapter(List<ProjectBean> articleList) {
        this.articleList = articleList;
    }
}
