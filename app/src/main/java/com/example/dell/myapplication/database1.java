package com.example.dell.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



/**
 * Created by Dell on 2019/4/17.
 */

public class database1 extends SQLiteOpenHelper {

    public static final String CREATE_EVENT = "create table event("
            +"name text primary key,"
            +"content text,"
            +"year int,"
            +"month int,"
            +"day int,"
            +"time text,"
            +"type text)";

    private Context mContext;



    public database1(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EVENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
