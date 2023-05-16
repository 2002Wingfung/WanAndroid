package com.hongyongfeng.wanandroid.data.local.database;

import static com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel.SQL_INSERT_ARTICLE;
import static com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel.helper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;

public class Insert {
    static SQLiteDatabase db = helper.getWritableDatabase();
    public static void insert(ArticleBean article){
        Cursor cursor=db.rawQuery("select id from article_bean where id=?",new String[]{String.valueOf(article.getId())});
        if (cursor.moveToFirst()){
            int id=cursor.getInt(0);
            db.execSQL("delete from article_bean where id=?",new String[]{String.valueOf(id)});
        }
        db.execSQL(SQL_INSERT_ARTICLE,new String[]
                {String.valueOf(article.getId()),article.getAuthor(),
                        article.getChapterName(),article.getLink(),
                        article.getTitle(),article.getNiceDate(),
                        article.getSuperChapterName(),String.valueOf(article.getTop())});
        cursor.close();
        //db.close();
    }
}
