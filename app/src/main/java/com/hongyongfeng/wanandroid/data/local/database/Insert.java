package com.hongyongfeng.wanandroid.data.local.database;

import static com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel.SQL_INSERT_ARTICLE;
import static com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel.helper;
import static com.hongyongfeng.wanandroid.util.Constant.DELETE_SQL;
import static com.hongyongfeng.wanandroid.util.Constant.SELECT_SQL;
import static com.hongyongfeng.wanandroid.util.Constant.ZERO;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.util.MyApplication;
import com.hongyongfeng.wanandroid.util.MyDatabaseHelper;

/**
 * @author Wingfung Hung
 * 插入数据到数据库表中
 */
public class Insert {
    public static MyDatabaseHelper helper=new MyDatabaseHelper(MyApplication.getContext(),"HistoryArticle.db",null,1);
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
        Cursor cursor=db.rawQuery(SELECT_SQL,new String[]{String.valueOf(article.getId())});
        if (cursor.moveToFirst()){
            //检查数据库表中是否存在该项纪录，如果存在则先删除
            int id=cursor.getInt(ZERO);
            db.execSQL(DELETE_SQL,new String[]{String.valueOf(id)});
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
