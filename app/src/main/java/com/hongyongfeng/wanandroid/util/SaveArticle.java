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

public class SaveArticle {
    public static final String CACHE_HOME="home";
    public static final String CACHE_BITMAP="bitmap";
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
    public static <T> void setData(Context context, List<T> list,int type)
    {
        File file = context.getCacheDir();
        File cache ;
        if (type==0){
            cache =  new File(file, CACHE_HOME);
        }else {
            cache =  new File(file, CACHE_BITMAP);
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
    public static <T> List<T> getData(Context context,int type) throws IllegalAccessException, InstantiationException {
        File file = context.getCacheDir();
        File cache;
        List<T> list;
        if (type==0){
            cache = new File(file, CACHE_HOME);
        }else {
            cache = new File(file, CACHE_BITMAP);
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
