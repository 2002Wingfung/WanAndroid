package com.hongyongfeng.wanandroid.data.local.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * 建表语句
 * @author Wingfung Hung
 */
public class CreateTable {
    public static final String ARTICLE_BEAN=
            "create table if not exists article_bean(\n" +
                "id int primary key ,\n" +
                "author varchar(10),\n" +
                "chapterName varchar(10),\n" +
                "link varchar(50) unique,\n" +
                "title varchar(30),\n"+
                "niceDate varchar(20),\n"+
                "superChapterName varchar(10),\n"+
                "top int"+
            ");";
    /**
     * 创建项目中所需要的全部表
     * @param db SQLiteDatabase对象
     */
    public static void createTable(SQLiteDatabase db){
        db.execSQL(ARTICLE_BEAN);
    }
}
