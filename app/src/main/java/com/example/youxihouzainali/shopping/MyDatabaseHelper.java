package com.example.youxihouzainali.shopping;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by youxihouzainali on 2017/11/23.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_USER = "create table User("
            + "id integer primary key autoincrement, "
            + "username text, "
            + "password text, "
            + "ownerflag integer)";

    public static final String CREATE_MARGIN = "create table Margin("
            + "id integer primary key autoincrement, "
            + "username text, "
            + "name text, "
            + "price real, "
            + "describe text, "
            + "picture blob)";

    public static final String CREATE_HISTORY = "create table History("
            + "id integer primary key autoincrement, "
            + "customerid integer, "
            + "marginid integer, "
            + "number integer, "
            + "buy integer)";
    public static final String CREATE_CART = "create table Cart("
            + "id integer primary key autoincrement, "
            + "customerid integer, "
            + "marginid integer, "
            + "number integer)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_CART);
        db.execSQL(CREATE_HISTORY);
        db.execSQL(CREATE_MARGIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists User");
        db.execSQL("drop table if exists Margin");
        db.execSQL("drop table if exists History");
        db.execSQL("drop table if exists Cart");
        onCreate(db);
    }

}
