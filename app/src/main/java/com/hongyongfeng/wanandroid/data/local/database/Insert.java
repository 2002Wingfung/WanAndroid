package com.hongyongfeng.wanandroid.data.local.database;

import static com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel.SQL_INSERT_ARTICLE;
import static com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel.helper;

import android.database.sqlite.SQLiteDatabase;

import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;

public class Insert {
    public static void insert(ArticleBean article){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(SQL_INSERT_ARTICLE,new String[]
                {String.valueOf(article.getId()),article.getAuthor(),
                        article.getChapterName(),article.getLink(),
                        article.getTitle(),article.getNiceDate(),
                        article.getSuperChapterName(),String.valueOf(article.getTop())});
    }
}
