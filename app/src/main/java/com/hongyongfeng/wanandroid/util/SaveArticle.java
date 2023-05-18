package com.hongyongfeng.wanandroid.util;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.fragment.app.FragmentActivity;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * @author Wingfung Hung
 */
public class SaveArticle {
    public static final String CACHE_HOME="home";
    public static final String CACHE_BITMAP="bitmap";
    public static final String CACHE_BANNER="BannerBean";

    /**
     * 已废弃，本来是打算将历史记录保存到SharedPreferences中的
     * @param activity 调用该方法的活动
     * @param article 文章实体类
     */
    public static void save(FragmentActivity activity, ArticleBean article){
        SharedPreferences.Editor editor=activity.getSharedPreferences("data", Context.MODE_PRIVATE).edit();
        editor.putString("author",article.getAuthor());
        editor.putString("chapterName",article.getChapterName());
        editor.putString("link",article.getLink());
        editor.putString("title",article.getTitle());
        editor.putString("niceDate",article.getNiceDate());
        editor.putString("superChapterName",article.getSuperChapterName());
        editor.apply();
    }

    /**
     * 保存数据
     * @param context 调用该方法的活动
     * @param list 对应实体类的集合
     * @param type 用于标识是储存bitmap还是首页文章还是banner的实体类
     * @param <T> 泛型
     */
    public static <T> void setData(Context context, List<T> list,int type)
    {
        File file = context.getCacheDir();
        File cache ;
        if (type==0){
            cache =  new File(file, CACHE_HOME);
        }else if (type==1){
            cache =  new File(file, CACHE_BITMAP);
        }else {
            cache =  new File(file, CACHE_BANNER);
        }
        if(cache.exists()){
            cache.delete();
        }
        try {
            ObjectOutputStream outputStream =
                    new ObjectOutputStream(new FileOutputStream(cache));
            outputStream.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提取数据
     * @param context 调用该方法的活动
     * @param type 用于标识是提取bitmap还是首页文章还是banner的实体类
     * @return 返回一个该泛型的集合
     * @param <T> 泛型
     */
    public static <T> List<T> getData(Context context,int type) throws IllegalAccessException, InstantiationException {
        File file = context.getCacheDir();
        File cache;
        List<T> list;
        if (type==0){
            cache = new File(file, CACHE_HOME);
        }else if (type==1){
            cache = new File(file, CACHE_BITMAP);
        }else {
            cache = new File(file, CACHE_BANNER);

        }
        if(!cache.exists()){
            return null;
        }
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(cache));
            list = (List<T>) inputStream.readObject();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
