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
    @Override
    public void onCreate(SQLiteDatabase db) {
        CreateTable.createTable(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}