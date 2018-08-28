package com.example.administrator.musicplayer;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by hxr on 2018/1/7.
 */

public class myDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "Comment.db";
    private final int DB_VERSION = 1;

    public myDB(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE_1 = "CREATE TABLE if not exists "
                + "GAOBAIQIQIU"
                +"(comment TEXT,"
                +"date TEXT)";
        String CREATE_TABLE_2 = "CREATE TABLE if not exists "
                + "LOVESTORY"
                +"(comment TEXT,"
                +"date TEXT)";
        String CREATE_TABLE_3 = "CREATE TABLE if not exists "
                + "TASHUO"
                +"(comment TEXT,"
                +"date TEXT)";
        String CREATE_TABLE_4 = "CREATE TABLE if not exists "
                + "HELLO"
                +"(comment TEXT,"
                +"date TEXT)";
        String CREATE_TABLE_5 = "CREATE TABLE if not exists "
                + "SHUOHUANG"
                +"(comment TEXT,"
                +"date TEXT)";
        try{
            db.execSQL(CREATE_TABLE_1);
            db.execSQL(CREATE_TABLE_2);
            db.execSQL(CREATE_TABLE_3);
            db.execSQL(CREATE_TABLE_4);
            db.execSQL(CREATE_TABLE_5);
        }catch(SQLException E){
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db){
        String CREATE_TABLE_1 = "CREATE TABLE if not exists "
                + "GAOBAIQIQIU"
                +"(comment TEXT,"
                +"date TEXT)";
        String CREATE_TABLE_2 = "CREATE TABLE if not exists "
                + "LOVESTORY"
                +"(comment TEXT,"
                +"date TEXT)";
        String CREATE_TABLE_3 = "CREATE TABLE if not exists "
                + "TASHUO"
                +"(comment TEXT,"
                +"date TEXT)";
        String CREATE_TABLE_4 = "CREATE TABLE if not exists "
                + "HELLO"
                +"(comment TEXT,"
                +"date TEXT)";
        String CREATE_TABLE_5 = "CREATE TABLE if not exists "
                + "SHUOHUANG"
                +"(comment TEXT,"
                +"date TEXT)";
        try{
            db.execSQL(CREATE_TABLE_1);
            db.execSQL(CREATE_TABLE_2);
            db.execSQL(CREATE_TABLE_3);
            db.execSQL(CREATE_TABLE_4);
            db.execSQL(CREATE_TABLE_5);
        }catch(SQLException E){
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newViewsion){

    }
}
