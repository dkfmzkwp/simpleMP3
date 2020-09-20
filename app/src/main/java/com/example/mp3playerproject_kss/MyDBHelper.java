package com.example.mp3playerproject_kss;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private String sql;
    private Cursor cursor;

    public MyDBHelper(Context context, String dbName){
        super(context,dbName,null,1);
        this.context = context;
    }

    //테이블 생성
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table userTBL(id string(20) primary key, password string(10));");
    }

    //테이블 제거
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists userTBL;");
        onCreate(sqLiteDatabase);
    }


}
