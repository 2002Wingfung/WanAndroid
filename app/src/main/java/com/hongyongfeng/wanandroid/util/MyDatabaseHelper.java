package com.hongyongfeng.wanandroid.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.hongyongfeng.wanandroid.data.local.database.CreateTable;
/**
 * 数据库帮助类
 * @author Wingfung Hung
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 创建表
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        CreateTable.createTable(db);
    }

    /**
     * 升级数据库
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}