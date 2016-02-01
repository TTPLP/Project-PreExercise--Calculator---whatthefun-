package com.example.user.calculator;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.ResultSet;

/**
 * Created by YUAN on 2016/01/24.
 */
public class DB {
    public static final String TABLE_NAME = "Record";
    public static final String KEY_ID = "_id";
    public static final String DATA = "_data";
    private SQLiteDatabase db;

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT Exists " + TABLE_NAME  +"(" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DATA + " TEXT NOT NULL)";

    public DB(Context context){
        db = DBHelper.getDatabase(context);
    }

    public void close(){
        db.close();
    }

    public void insert(String data){
        ContentValues cv = new ContentValues();

        cv.put(DATA, data.toString());
        db.insert(TABLE_NAME, null, cv);
    }

    public String[] getLast(){
        String[] last = new String[10];

        Cursor result = db.rawQuery("SELECT "+ DATA +" FROM Record ORDER BY "+ KEY_ID +" DESC LIMIT 10", null);
        result.moveToFirst();
        for (int i = 0; i < result.getCount(); i++){
            if (result != null){
                last[i] = result.getString(0);
            }
            result.moveToNext();
        }
        return last;
    }
}
