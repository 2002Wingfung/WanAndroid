package com.hongyongfeng.wanandroid.module.home.view.adapter;

import static com.hongyongfeng.wanandroid.util.Constant.ONE;

import android.content.res.Resources;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.home.view.viewholder.ArticleViewHolder;
import com.hongyongfeng.wanandroid.util.MyApplication;
import java.util.List;

/**
 * 文章适配器
 * @author Wingfung Hung
 */
public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    /**
     * 用于搜索页面的html代码解析
     */
    public static final String HIGH_LIGHT_START="<em class='highlight'>";
    /**
     * 用于搜索页面的html代码解析
     */
    public static final String HIGH_LIGHT_END="</em>";
    /**
     * Resources实例
     */
    private final Resources resource=MyApplication.getContext().getResources();
    /**
     * 存储DishesInformation对象的List集合
     */
    private final List<ArticleBean> articleList;

    /**
     * 点击事件监听接口
     */
    public interface OnItemClickListener {
        /**
         * 点赞监听
         * @param view item的视图
         * @param position item的索引
         * @param likes TextView实例
         * @param count 用于判断是否已点赞
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

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.mOnItemClickListener = clickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (articleList.size()==0){
            return super.getItemViewType(position);
        }
        if (position<articleList.size()){
            return super.getItemViewType(position);
        } else {
            return -1;
        }
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==-1){
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_loading,parent,false);
            return new ArticleViewHolder.LoadingHolder(view);
        }else {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_article,parent,false);
            return new ArticleViewHolder(view,mOnItemClickListener);
        }
    }

    /**
     * 绑定item中的控件
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (position<articleList.size()){
            ArticleViewHolder holder1=(ArticleViewHolder)holder;
            ArticleBean article=articleList.get(position);
            String title=article.getTitle();
            StringBuilder tvTitle=new StringBuilder(title);
            int first=title.indexOf(HIGH_LIGHT_START);
            //如果标题中没有html代码,则first默认为-1
            while (first!=-ONE){
                //在关键字的html代码前加上设为红色的代码
                tvTitle.insert(first,"<font color='red'>");
                first=tvTitle.indexOf(HIGH_LIGHT_START,first+1+HIGH_LIGHT_START.length());
            }
            int last=tvTitle.indexOf(HIGH_LIGHT_END);
            while (last!=-ONE){
                last+=5;
                tvTitle.insert(last,"</font>");
                last=tvTitle.indexOf(HIGH_LIGHT_END,last+1+HIGH_LIGHT_END.length());
            }
            //解析html代码，并将字符串显示在TextView中
            holder1.tvTitle.setText(Html.fromHtml(tvTitle.toString()));
            try{
                String superChapterName=article.getSuperChapterName();
                String chapterName=article.getChapterName();
                //如果类别为空，那么设置该类别为项目
                if (superChapterName==null&&chapterName==null){
                    holder1.tvCategory.setText("项目");
                }else {
                    if (superChapterName!=null){
                        //如果父类别不为空，则用-将父类别和子类别拼接到一起，并显示在TextView中
                        StringBuilder category=new StringBuilder(superChapterName);
                        category.append("-").append(chapterName);
                        holder1.tvCategory.setText(category);
                    }else {
                        holder1.tvCategory.setText(chapterName);
                    }
                }
            }catch (Exception e){
                Log.e("StringBuilder",e.toString());
            }
            holder1.tvTime.setText(article.getNiceDate());
            holder1.tvAuthor.setText(article.getAuthor());
            //判断是否显示置顶标识
            if (article.getTop()==-1){
                holder1.tvTop.setVisibility(View.VISIBLE);
                holder1.tvTop.setText("置顶");
            }else {
                holder1.tvTop.setVisibility(View.GONE);
            }
            if (article.isCollect()){
                holder1.tvLikes.setBackground(ResourcesCompat.getDrawable(resource, R.drawable.ic_likes, null));
            }
        }

    }

    @Override
    public int getItemCount() {
        if (articleList.size()==0){
            return 0;
        }else {
            return articleList.size()+1;
        }
    }

    public ArticleAdapter(List<ArticleBean> articleList) {
        this.articleList = articleList;
    }
}
