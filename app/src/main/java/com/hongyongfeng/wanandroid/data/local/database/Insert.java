package com.hongyongfeng.wanandroid.data.local.database;

import static com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel.SQL_INSERT_ARTICLE;
import static com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel.helper;
import static com.hongyongfeng.wanandroid.util.Constant.DELETE_SQL;
import static com.hongyongfeng.wanandroid.util.Constant.ZERO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;

/**
 * @author Wingfung Hung
 * 插入数据到数据库表中
 */
public class Insert {
    /**
     * 获取SQLiteDatabase实例
     */
    static SQLiteDatabase db = helper.getWritableDatabase();

    /**
     * 读取ArticleBean实体类,并将数据插入数据库中
     * @param article ArticleBean实体类
     */
    public static void insert(ArticleBean article){
        //获取游标指针
        Cursor cursor=db.rawQuery(DELETE_SQL,new String[]{String.valueOf(article.getId())});
        if (cursor.moveToFirst()){
            //检查数据库表中是否存在该项纪录，如果存在则先删除
            int id=cursor.getInt(ZERO);
            db.execSQL("delete from article_bean where id=?",new String[]{String.valueOf(id)});
        }
        //插入文章数据到数据库表
        db.execSQL(SQL_INSERT_ARTICLE,new String[]
                {String.valueOf(article.getId()),article.getAuthor(),
                        article.getChapterName(),article.getLink(),
                        article.getTitle(),
                        article.getSuperChapterName(),String.valueOf(article.getTop())});
        cursor.close();
    }
}
