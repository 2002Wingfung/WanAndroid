package com.hongyongfeng.wanandroid.module.home.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.home.view.viewHolder.HomeArticleViewHolder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wingfung Hung
 */
public class HomeArticleAdapter extends RecyclerView.Adapter<HomeArticleViewHolder>  implements Serializable {

    /**
     * 存储DishesInformation对象的List集合
     */
    public List<ArticleBean> articleList=new ArrayList<>();

    /**
     * 绑定顾客主页的item
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return CustomerHomeViewHolder对象
     */
    @NonNull
    @Override
    public HomeArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_article,parent,false);
        return new HomeArticleViewHolder(view);
    }



    /**
     * 绑定item中的控件
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull HomeArticleViewHolder holder, int position) {
        //在merchantInfoList集合中获取Merchant对象
        ArticleBean article=articleList.get(position);
        //设置商家图片
        holder.tvTitle.setText(String.valueOf(article.getArticleId()));

    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    /**
     *
     * @param articleList
     */
    public HomeArticleAdapter(List<ArticleBean> articleList) {
        this.articleList = articleList;
    }
}
