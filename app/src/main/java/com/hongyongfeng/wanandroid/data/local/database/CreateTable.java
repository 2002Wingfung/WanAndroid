package com.hongyongfeng.wanandroid.data.local.database;

import static com.hongyongfeng.wanandroid.util.Constant.ARTICLE_BEAN;
import android.database.sqlite.SQLiteDatabase;

/**
 * 建立数据库表
 * @author Wingfung Hung
 */
public class CreateTable {
    /**
     * 创建项目中所需要的全部表
     * @param db SQLiteDatabase对象
     */
    public static void createTable(SQLiteDatabase db){
        db.execSQL(ARTICLE_BEAN);
    }
}
