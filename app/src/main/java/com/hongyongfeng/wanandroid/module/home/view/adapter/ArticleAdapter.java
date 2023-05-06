package com.hongyongfeng.wanandroid.module.home.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.home.view.viewholder.ArticleViewHolder;

import java.util.List;

/**
 * @author Wingfung Hung
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder>  {

    /**
     * 存储DishesInformation对象的List集合
     */
    public List<ArticleBean> articleList;

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
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_article,parent,false);
        return new ArticleViewHolder(view,mOnItemClickListener);
    }



    /**
     * 绑定item中的控件
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        ArticleBean article=articleList.get(position);
        //holder.tvId.setText(String.valueOf(article.getId()));
        holder.tvTitle.setText(article.getTitle());
        try{
            StringBuilder category=new StringBuilder(article.getSuperChapterName());
            category.append("-").append(article.getChapterName());
            holder.tvCategory.setText(category);
        }catch (Exception e){
            Log.e("StringBuilder",e.toString());
        }

        holder.tvTime.setText(article.getNiceDate());
        holder.tvAuthor.setText(article.getAuthor());
        //tvTop
        if (article.getId()==-1){
            holder.tvTop.setText("置顶");
        }
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    /**
     *
     */
    public ArticleAdapter(List<ArticleBean> articleList) {
        this.articleList = articleList;
    }
//    public void addData(int position,String name,String price,int imageId,int dishesId) {
//        DishesInformation dish =new DishesInformation(name,imageId,price,dishesId);
////      在list中添加数据，并通知条目加入一条
//        articleList.add(position, dish);
//        //添加动画
//        this.notifyItemInserted(position);
//    }
}
