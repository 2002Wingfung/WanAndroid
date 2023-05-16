package com.hongyongfeng.wanandroid.module.home.view.adapter;

import android.text.Html;
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
    public static final String HIGH_LIGHT_START="<em class='highlight'>";
    public static final String HIGH_LIGHT_END="</em>";

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
        //holder.tvTitle.setText(article.getTitle());
        String title=article.getTitle();
        StringBuilder tvTitle=new StringBuilder(title);
        int first=title.indexOf(HIGH_LIGHT_START);
        while (first!=-1){
            tvTitle.insert(first,"<font color='red'>");
            first=tvTitle.indexOf(HIGH_LIGHT_START,first+1+HIGH_LIGHT_START.length());
        }
        int last=tvTitle.indexOf(HIGH_LIGHT_END);
        while (last!=-1){
            last+=5;
            tvTitle.insert(last,"</font>");
            last=tvTitle.indexOf(HIGH_LIGHT_END,last+1+HIGH_LIGHT_END.length());
        }
        holder.tvTitle.setText(Html.fromHtml(tvTitle.toString()));
        try{
            String superChapterName=article.getSuperChapterName();
            String chapterName=article.getChapterName();
            if (superChapterName==null&&chapterName==null){
                holder.tvCategory.setText("项目");
            }else {
                //assert superChapterName != null;
                if (superChapterName!=null){
                    StringBuilder category=new StringBuilder(superChapterName);
                    category.append("-").append(chapterName);
                    holder.tvCategory.setText(category);
                }else {
                    holder.tvCategory.setText(chapterName);
                }

            }

        }catch (Exception e){
            Log.e("StringBuilder",e.toString());
        }

        holder.tvTime.setText(article.getNiceDate());
        holder.tvAuthor.setText(article.getAuthor());
        //tvTop
        if (article.getTop()==-1){
            holder.tvTop.setVisibility(View.VISIBLE);
            holder.tvTop.setText("置顶");
        }else {
            holder.tvTop.setVisibility(View.GONE);
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
